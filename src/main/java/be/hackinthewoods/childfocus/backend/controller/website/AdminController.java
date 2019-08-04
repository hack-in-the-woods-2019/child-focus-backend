package be.hackinthewoods.childfocus.backend.controller.website;

import be.hackinthewoods.childfocus.backend.controller.website.model.RegisteringUser;
import be.hackinthewoods.childfocus.backend.entity.MissingPerson;
import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.entity.Role;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import be.hackinthewoods.childfocus.backend.repository.UserRepository;
import be.hackinthewoods.childfocus.backend.service.MissingPersonService;
import be.hackinthewoods.childfocus.backend.service.MissionService;
import be.hackinthewoods.childfocus.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private MissingPersonService missingPersonService;
    private MissionService missionService;
    private RoleService roleService;
    private UserRepository userRepository;

    @Autowired
    public AdminController(MissingPersonService missingPersonService, MissionService missionService, RoleService roleService, UserRepository userRepository) {
        this.missingPersonService = missingPersonService;
        this.missionService = missionService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/admin/register-admin", method = RequestMethod.GET)
    public String registerAdminPage(Model model) {
        model.addAttribute("registeringUser", new RegisteringUser());
        return "admin/register-admin";
    }

    @RequestMapping(value = "/admin/register-admin", method = RequestMethod.POST)
    public String registerAdminPostPage(@ModelAttribute RegisteringUser registeringUser, BindingResult bindingResult) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Set<Role> roles = roleService.findAll();
        WebUser webUser = new WebUser(registeringUser.getEmail(),
                null,
                null,
                null,
                passwordEncoder.encode(registeringUser.getPassword()),
                roles);

        userRepository.save(webUser);

        return "redirect:/";
    }

    @RequestMapping(value = "/admin/register-volunteer", method = RequestMethod.GET)
    public String registerVolunteerPage(Model model) {
        model.addAttribute("registeringUser", new RegisteringUser());
        return "admin/register-volunteer";
    }

    @RequestMapping(value = "/admin/register-volunteer", method = RequestMethod.POST)
    public String registerVolunteerPostPage(@ModelAttribute RegisteringUser registeringUser) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        WebUser webUser = new WebUser(registeringUser.getEmail(),
                registeringUser.getFirstname(),
                registeringUser.getLastname(),
                registeringUser.getPhoneNumber(),
                passwordEncoder.encode(registeringUser.getPassword()),
                Collections.singleton(roleService.findUserRole())
        );

        userRepository.save(webUser);

        return "redirect:/";
    }

    @RequestMapping(value = "/admin/add-missing-person", method = RequestMethod.GET)
    public String missingPersonMissingPersonPage(Model model) {
        model.addAttribute("missingPerson", new MissingPerson());
        return "admin/add-missing-person";
    }

    @RequestMapping(value = "/admin/add-missing-person", method = RequestMethod.POST)
    public String missingPersonMissingPersonPostPage(@ModelAttribute MissingPerson missingPerson,
                                            MultipartHttpServletRequest multipartHttpServletRequest,
                                            BindingResult bindingResult) {

        addPictureToMissingPerson(missingPerson, multipartHttpServletRequest);
        missingPersonService.save(missingPerson);

        List<WebUser> volonteers = userRepository.findByRole(roleService.findUserRole());
        for (WebUser volonteer : volonteers) {
            Mission mission = new Mission(null,
                    volonteer,
                    missingPerson,
                    Mission.Status.PENDING);

            missionService.save(mission);
        }
        return "redirect:/admin/missing-people";
    }

    @GetMapping("/admin/missing-person-missions/{id}")
    public String missingPersonMissionsPage(Model model, @PathVariable long id) {
        MissingPerson missingPerson = missingPersonService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no missing person with ID = " + id));
        List<Mission> missions = missionService.findByMissingPerson(missingPerson);
        model.addAttribute("missions", missions);
        return "admin/missing-person-missions";
    }

    @GetMapping(value = "/admin/missing-people")
    public String missingPeoplePage(Model model) {
        List<MissingPerson> missingPeople = missingPersonService.findAll();
        model.addAttribute("missingPeople", missingPeople);
        return "admin/missing-people";
    }

    @GetMapping(value = "/admin/missing-person-picture/{id}")
    public ResponseEntity<byte[]> getMissingPersonPicture(@PathVariable("id") long id) {
        MissingPerson missingPerson =
                missingPersonService.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("The evidence of id " + id + " doesn't exist.")
                );
        String[] fileName = missingPerson.getPictureFileName().split("\\.");
        String fileExtension = fileName[fileName.length - 1];

        HttpHeaders responseHeader = new HttpHeaders();
        switch (fileExtension.toLowerCase()) {
            case "png":
                responseHeader.setContentType(MediaType.IMAGE_PNG);
                break;
            case "jpg":
            case "jpeg":
                responseHeader.setContentType(MediaType.IMAGE_JPEG);
                break;
            default: throw new IllegalStateException("File extention ." + fileExtension + " not supported.");
        }
        return new ResponseEntity<>(missingPerson.getPicture(), responseHeader, HttpStatus.CREATED);
    }

    private void addPictureToMissingPerson(MissingPerson missingPerson, MultipartHttpServletRequest multipartHttpServletRequest) {
        List<MultipartFile> files = multipartHttpServletRequest.getFiles("file-with-multi-file");

        for (MultipartFile file : files) {
            // Fix a bug where a file is saved even if no file has been submitted
            if (MediaType.APPLICATION_OCTET_STREAM_VALUE.equals(file.getContentType())) {
                continue;
            }

            missingPerson.setPictureFileName(file.getOriginalFilename());

            try {
                missingPerson.setPicture(file.getBytes());
            } catch (IOException ignored) {
            }
        }
    }

}
