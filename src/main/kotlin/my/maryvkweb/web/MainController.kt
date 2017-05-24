package my.maryvkweb.web

import my.maryvkweb.VkProperties
import my.maryvkweb.domain.RegisteredSeeker
import my.maryvkweb.domain.User
import my.maryvkweb.seeker.MarySeekerScheduler
import my.maryvkweb.service.RegisteredSeekerService
import my.maryvkweb.service.RelationChangeService
import my.maryvkweb.service.UserService
import my.maryvkweb.service.VkService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller class MainController(
        private val marySeekerScheduler: MarySeekerScheduler,
        private val relationChangeService: RelationChangeService,
        private val registeredSeekerService: RegisteredSeekerService,
        private val vkProperties: VkProperties,
        private val vkService: VkService
) {

    private companion object Views {
        private const val VIEWS_REGISTERED_SEEKERS = "registered-seekers"
        private const val VIEWS_CHANGES = "changes"
        private const val REDIRECT_TO_SEEKERS = "redirect:/seekers"
        private const val REDIRECT_TO_AUTH = "redirect:/getAuth"
    }

    @RequestMapping("/")
    fun index() = REDIRECT_TO_SEEKERS

    @RequestMapping("/seekers")
    fun registeredSeekers(model: Model): String {
        if (vkProperties.accessToken.isEmpty())
            return REDIRECT_TO_AUTH

        val connected = vkService.findUsers(
                registeredSeekerService.findAll().map { it.connectedId!! })
        val seekers = connected?.map(this::createStatus)

        model.addAttribute("seekers", seekers)
        model.addAttribute("newSeeker", RegisteredSeeker())
        return VIEWS_REGISTERED_SEEKERS
    }

    private fun createStatus(connected: User)
            = SeekerStatus(connected, marySeekerScheduler.isRunning(connected.id!!))

    @RequestMapping("/seekers/{connectedId}/start")
    fun start(@PathVariable connectedId: Int): String {
        marySeekerScheduler.schedule(connectedId)
        return REDIRECT_TO_SEEKERS
    }

    @RequestMapping("/seekers/startAll")
    fun startAll(): String {
        registeredSeekerService.findAll()
                .forEach { user -> start(user.connectedId!!) }
        return REDIRECT_TO_SEEKERS
    }

    @RequestMapping("/seekers/{connectedId}/stop")
    fun stop(@PathVariable connectedId: Int): String {
        marySeekerScheduler.unschedule(connectedId)
        return REDIRECT_TO_SEEKERS
    }

    @RequestMapping("/seekers/{connectedId}/remove")
    fun remove(@PathVariable connectedId: Int): String {
        marySeekerScheduler.unschedule(connectedId)
        registeredSeekerService.unregister(connectedId)
        return REDIRECT_TO_SEEKERS
    }

    @RequestMapping("/seekers/register")
    fun register(@ModelAttribute newSeeker: RegisteredSeeker): String {
        registeredSeekerService.register(newSeeker.connectedId!!)
        return REDIRECT_TO_SEEKERS
    }

    @RequestMapping("/seekers/{connectedId}/changes")
    fun changes(model: Model, @PathVariable connectedId: Int): String {
        val relationChanges = relationChangeService.findAllByConnectedIdOrderByTimeDesc(connectedId)
        vkService.findUsers(relationChanges.map { it.targetId!! })  //fetch users
        model.addAttribute("changes", relationChanges)
        model.addAttribute("owner", vkService.findUser(vkProperties.ownerId))
        return VIEWS_CHANGES
    }

    @RequestMapping("/seekers/allChanges")
    fun allChanges(model: Model): String {
        val relationChanges = relationChangeService.findAllOrderByTimeDesc()
        vkService.findUsers(relationChanges.map { it.targetId!! })  //fetch users
        model.addAttribute("changes", relationChanges)
        model.addAttribute("owner", vkService.findUser(vkProperties.ownerId))
        return VIEWS_CHANGES
    }

    @GetMapping("/getAuth")
    fun getAuth(): String {
        return "redirect:" +
                "https://oauth.vk.com/authorize" +
                "?client_id=${vkProperties.clientId}" +
                "&display=${vkProperties.display}" +
                "&redirect_uri=${vkProperties.redirectUri}" +
                "&scope=${vkProperties.scope}" +
                "&response_type=${vkProperties.responseType}" +
                "&v=${vkProperties.apiVersion}"
    }

    @RequestMapping("/doAuth")
    fun doAuth(@RequestParam code: String): String {
        vkService.authorize(code)
        return REDIRECT_TO_SEEKERS
    }
}