package org.teomant.anotherlearningproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teomant.anotherlearningproject.controllers.forms.RegistrationForm;
import org.teomant.anotherlearningproject.controllers.forms.validators.RegistrationValidator;
import org.teomant.anotherlearningproject.entities.MessageEntity;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.FighterEntity;
import org.teomant.anotherlearningproject.game.Server;
import org.teomant.anotherlearningproject.repositories.FighterRepository;
import org.teomant.anotherlearningproject.schedulers.TestScheduler;
import org.teomant.anotherlearningproject.services.FighterService;
import org.teomant.anotherlearningproject.services.MessageService;
import org.teomant.anotherlearningproject.services.RoleService;
import org.teomant.anotherlearningproject.services.UserService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private RegistrationValidator registrationValidator;

    @Autowired
    private FighterService fighterService;

    @Autowired
    private MessageService messageService;

    @ModelAttribute( "registrationForm" )
    public RegistrationForm registrationForm(){
        return new RegistrationForm();
    }

    @InitBinder( "registrationForm" )
    protected void initRegistrationBinder( WebDataBinder binder ){
        binder.setValidator( registrationValidator );
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", "test");

        return "index";
    }

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String viewRegister(Model model, Principal principal) {

        if (principal != null) {
            return "redirect:/userInfo";
        }

        RegistrationForm form = new RegistrationForm();

        model.addAttribute("registrationForm", form);

        return "registerPage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveRegister(Model model, //
                               @ModelAttribute("registrationForm") @Validated RegistrationForm registrationForm, //
                               BindingResult result,
                               final RedirectAttributes redirectAttributes, Principal principal) {

        if (result.hasErrors()) {
            return "registerPage";
        }
        UserEntity newUser = new UserEntity();

        try {
            newUser.setRoles(Arrays.asList(roleService.getUserRole()));
            newUser.setUsername(registrationForm.getUsername());
            newUser.setPassword(new BCryptPasswordEncoder().encode(registrationForm.getPassword()));
            newUser.setEnabled(1);
            newUser = userService.save(newUser);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerPage";
        }

        redirectAttributes.addFlashAttribute("flashUser", newUser);

        return "redirect:/registerSuccessful";
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(Model model, Principal principal) {

        if (principal != null) {
            return "redirect:/userInfo";
        }

        return "registerSuccessfulPage";
    }

    @RequestMapping(value = { "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        UserEntity user = userService.findUserByUsername(principal.getName());

        model.addAttribute("userInfo", user.toString());

        return "adminPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        String userName = principal.getName();

        UserEntity user = userService.findUserByUsername(principal.getName());

        model.addAttribute("userInfo", user.toString());

        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            UserEntity user = userService.findUserByUsername(principal.getName());

            model.addAttribute("userInfo", user.toString());

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

    @RequestMapping(value = "/startTask", method = RequestMethod.GET)
    public String startTask(Model model, Principal principal) {

        return "schedulePage";
    }

    @RequestMapping(value = "/chatWith", method = RequestMethod.GET)
    public String chatPage(Model model, Principal principal, @RequestParam(name = "id") long toId) {

        UserEntity from = userService.findUserByUsername(principal.getName());
        UserEntity to = userService.findById(toId);
        List<MessageEntity> messages = new ArrayList<>();

        messages.addAll(messageService.findByUser(from));
        messages.addAll(messageService.findByUser(to));

        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("messages", messages.stream()
                .sorted(Comparator.comparing(MessageEntity::getTime)).collect(Collectors.toList()));

        return "chatPage";
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String postMessage(Model model, @RequestParam("fromId") long fromId,
                              @RequestParam("toId") long toId,
                              @RequestParam("messageText") String messageText) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setFrom(userService.findById(fromId));
        messageEntity.setTo(userService.findById(toId));

        messageEntity.setTime(new Date());
        messageEntity.setText(messageText);
        messageService.save(messageEntity);
        return "redirect:/chatWith?id="+toId;
    }
}
