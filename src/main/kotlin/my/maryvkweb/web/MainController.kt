package my.maryvkweb.web

import my.maryvkweb.domain.RegisteredSeeker
import my.maryvkweb.seeker.MarySeekerScheduler
import my.maryvkweb.service.RegisteredSeekerService
import my.maryvkweb.service.RelationChangeService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller class MainController(
        private val marySeekerScheduler: MarySeekerScheduler,
        private val relationChangeService: RelationChangeService,
        private val registeredSeekerService: RegisteredSeekerService
) {

    companion object {
        private val VIEWS_REGISTERED_SEEKERS = "registered-seekers"
        private val VIEWS_CHANGES = "changes"
        private val REDIRECT_TO_SEEKERS = "redirect:/seekers"
    }

    @RequestMapping("/")
    fun index() = REDIRECT_TO_SEEKERS

    @RequestMapping("/seekers")
    fun registeredSeekers(model: Model): String {
        val seekers = registeredSeekerService.findAll()
                .map { it.targetId!! }
                .map { id -> SeekerStatus(id, marySeekerScheduler.isRunning(id)) }
                .toList()
        model.addAttribute("seekers", seekers)
        model.addAttribute("newSeeker", RegisteredSeeker())
        return VIEWS_REGISTERED_SEEKERS
    }

    @RequestMapping("/seekers/{userId}/start")
    fun start(@PathVariable("userId") userId: Int): String {
        marySeekerScheduler.schedule(userId)
        return REDIRECT_TO_SEEKERS
    }

    @RequestMapping("/seekers/{userId}/stop")
    fun stop(@PathVariable("userId") userId: Int): String {
        marySeekerScheduler.unschedule(userId)
        return REDIRECT_TO_SEEKERS
    }

    @RequestMapping("/seekers/{userId}/remove")
    fun remove(@PathVariable("userId") userId: Int): String {
        marySeekerScheduler.unschedule(userId)
        registeredSeekerService.unregister(userId)
        return REDIRECT_TO_SEEKERS
    }

    @RequestMapping("/seekers/register")
    fun register(@ModelAttribute(value = "newSeeker") newSeeker: RegisteredSeeker): String {
        registeredSeekerService.register(newSeeker.targetId!!)
        return REDIRECT_TO_SEEKERS
    }

    @RequestMapping("/seekers/{userId}/changes")
    fun changes(model: Model, @PathVariable("userId") userId: Int): String {
        model.addAttribute("changes", relationChangeService.findAllByOwnerIdOrderByTimeDesc(userId))
        return VIEWS_CHANGES
    }

    @RequestMapping("/seekers/all-changes")
    fun allChanges(model: Model): String {
        model.addAttribute("changes", relationChangeService.findAllOrderByTimeDesc())
        return VIEWS_CHANGES
    }
}