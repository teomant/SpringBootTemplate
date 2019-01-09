package org.teomant.anotherlearningproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teomant.anotherlearningproject.controllers.forms.RegistrationForm;
import org.teomant.anotherlearningproject.controllers.forms.validators.RegistrationValidator;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.Fight;
import org.teomant.anotherlearningproject.game.Fighter;
import org.teomant.anotherlearningproject.game.Server;
import org.teomant.anotherlearningproject.schedulers.TestScheduler;
import org.teomant.anotherlearningproject.services.RoleService;
import org.teomant.anotherlearningproject.services.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private RegistrationValidator registrationValidator;

    @Autowired
    private TestScheduler testScheduler;

    @Autowired
    private Server server;

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

        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            Fighter fighterOne = new Fighter("First" + i, random.nextInt(5), random.nextInt(5), random.nextInt(5));
            Fighter fighterTwo = new Fighter("Second" + i, random.nextInt(5), random.nextInt(5), random.nextInt(5));
            Fight fight = new Fight(fighterOne, fighterTwo);
            server.addFight(fight);
            if (server.inFight(fighterOne)) {
                System.out.println(server.fightWithFighter(fighterOne).get());
            }
        }

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

        System.out.println("User Name: " + userName);

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
}
