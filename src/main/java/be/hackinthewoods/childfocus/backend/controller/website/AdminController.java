package be.hackinthewoods.childfocus.backend.controller.website;

import be.hackinthewoods.childfocus.backend.controller.website.model.RegisteringUser;
import be.hackinthewoods.childfocus.backend.entity.MissingPerson;
import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.service.MissingPersonService;
import be.hackinthewoods.childfocus.backend.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminController {

    private MissingPersonService missingPersonService;

    private MissionService missionService;

    @Autowired
    public AdminController(MissingPersonService missingPersonService, MissionService missionService) {
        this.missingPersonService = missingPersonService;
        this.missionService = missionService;
    }

    @RequestMapping(value = "/admin/register-admin", method = RequestMethod.GET)
    public String registerAdminPage(Model model) {
        model.addAttribute("registeringUser", new RegisteringUser());
        return "admin/register-admin";
    }

    @RequestMapping(value = "/admin/register-volunteer", method = RequestMethod.GET)
    public String registerVolunteerPage(Model model) {
        model.addAttribute("registeringUser", new RegisteringUser());
        return "admin/register-volunteer";
    }

    @RequestMapping(value = "/admin/missing-people", method = RequestMethod.GET)
    public String missingPeoplePage(Model model) {
        List<MissingPerson> missingPeople = missingPersonService.findAll();
        model.addAttribute("missingPeople", missingPeople);
        return "admin/missing-people";
    }

    @RequestMapping(value = "/admin/missing-person-missions/{id}", method = RequestMethod.GET)
    public String missingPersonMissionsPage(Model model, @PathVariable long id) {
        MissingPerson missingPerson = missingPersonService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no missing person with ID = " + id));
        List<Mission> missions = missionService.findByMissingPerson(missingPerson);
        model.addAttribute("missions", missions);
        return "admin/missing-person-missions";
    }
}
