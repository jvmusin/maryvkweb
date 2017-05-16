package my.maryvk.maryvkweb.web;

import lombok.extern.java.Log;
import my.maryvk.maryvkweb.domain.RegisteredSeeker;
import my.maryvk.maryvkweb.seeker.MarySeekerScheduler;
import my.maryvk.maryvkweb.service.RegisteredSeekerService;
import my.maryvk.maryvkweb.service.RelationChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log
public class MainController {

    private static final String VIEWS_REGISTERED_SEEKERS = "registered-seekers";
    private static final String VIEWS_CHANGES = "changes";
    private static final String REDIRECT_TO_SEEKERS = "redirect:/seekers";

    private final MarySeekerScheduler marySeekerScheduler;
    private final RelationChangeService relationChangeService;
    private final RegisteredSeekerService registeredSeekerService;

    @Autowired
    public MainController(MarySeekerScheduler marySeekerScheduler, RelationChangeService relationChangeService, RegisteredSeekerService registeredSeekerService) {
        this.marySeekerScheduler = marySeekerScheduler;
        this.relationChangeService = relationChangeService;
        this.registeredSeekerService = registeredSeekerService;
    }

    @RequestMapping("/")
    public String index() {
        return REDIRECT_TO_SEEKERS;
    }

    @RequestMapping("/seekers")
    public String registeredSeekers(Model model) {
        List<SeekerStatus> seekers = registeredSeekerService.findAll().stream()
                .map(RegisteredSeeker::getTargetId)
                .map(id -> new SeekerStatus(id, marySeekerScheduler.isRunning(id)))
                .collect(Collectors.toList());
        model.addAttribute("seekers", seekers);
        model.addAttribute("newSeeker", new RegisteredSeeker());
        return VIEWS_REGISTERED_SEEKERS;
    }

    @RequestMapping("/seekers/{userId}/start")
    public String start(@PathVariable("userId") int userId) {
        marySeekerScheduler.schedule(userId);
        return REDIRECT_TO_SEEKERS;
    }

    @RequestMapping("/seekers/{userId}/stop")
    public String stop(@PathVariable("userId") int userId) {
        marySeekerScheduler.unschedule(userId);
        return REDIRECT_TO_SEEKERS;
    }

    @RequestMapping("/seekers/{userId}/remove")
    public String remove(@PathVariable("userId") int userId) {
        marySeekerScheduler.unschedule(userId);
        registeredSeekerService.unregister(userId);
        return REDIRECT_TO_SEEKERS;
    }

    @RequestMapping("/seekers/{userId}/changes")
    public String changes(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("changes", relationChangeService.findAllByOwnerIdOrderByTimeDesc(userId));
        return VIEWS_CHANGES;
    }

    @RequestMapping("/seekers/register")
    public String register(@ModelAttribute(value = "newSeeker") RegisteredSeeker newSeeker) {
        registeredSeekerService.register(newSeeker.getTargetId());
        return REDIRECT_TO_SEEKERS;
    }

    @RequestMapping("/seekers/all-changes")
    public String allChanges(Model model) {
        model.addAttribute("changes", relationChangeService.findAllOrderByTimeDesc());
        return VIEWS_CHANGES;
    }
}