package org.teomant.anotherlearningproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teomant.anotherlearningproject.controllers.forms.FighterCreationForm;
import org.teomant.anotherlearningproject.controllers.forms.validators.FighterCreationValidator;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.Fight;
import org.teomant.anotherlearningproject.game.FighterEntity;
import org.teomant.anotherlearningproject.game.Server;
import org.teomant.anotherlearningproject.game.actions.impl.RegenAction;
import org.teomant.anotherlearningproject.game.actions.impl.SpellDamageAction;
import org.teomant.anotherlearningproject.services.FighterService;
import org.teomant.anotherlearningproject.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    private FighterCreationValidator fighterCreationValidator;

    @ModelAttribute( "fighterCreationForm" )
    public FighterCreationForm fighterCreationForm(){
        return new FighterCreationForm();
    }

    @InitBinder( "fighterCreationForm" )
    protected void initRegistrationBinder( WebDataBinder binder ){
        binder.setValidator( fighterCreationValidator );
    }

    @Autowired
    FighterService fighterService;

    @Autowired
    UserService userService;

    @Autowired
    Server server;

    @RequestMapping(value = "/createFigter", method = RequestMethod.GET)
    public String createFighterGet(Model model, Principal principal) {

        FighterCreationForm form = new FighterCreationForm();

        model.addAttribute("fighterCreationForm", form);

        return "fighterCreationPage";
    }

    @RequestMapping(value = "/createFigter", method = RequestMethod.POST)
    public String createFighterPost(Model model, //
                                    @ModelAttribute("fighterCreationForm")
                                    @Validated FighterCreationForm fighterCreationForm, //
                                    BindingResult result,
                                    final RedirectAttributes redirectAttributes, Principal principal) {

        if (result.hasErrors()) {
            return "fighterCreationPage";
        }

        FighterEntity fighterEntity = new FighterEntity();
        fighterEntity.setName(fighterCreationForm.getName());
        fighterEntity.setStrength(Integer.parseInt(fighterCreationForm.getStrength()));
        fighterEntity.setAgility(Integer.parseInt(fighterCreationForm.getAgility()));
        fighterEntity.setMind(Integer.parseInt(fighterCreationForm.getMind()));
        fighterEntity.setUser(getUserByUsername(principal));

        fighterService.save(fighterEntity);

        return "redirect:/userInfo";
    }

    @RequestMapping(value = "/fight", method = RequestMethod.GET)
    public String searchForFight(Model model, Principal principal) {

        List<FighterEntity> enemies = fighterService.getAll();
        enemies.remove(fighterService.findByUser(getUserByUsername(principal)));

        model.addAttribute("friends",userService.findFriends(getUserByUsername(principal)));
        model.addAttribute("enemies", enemies);

        return "selectEnemyPage";
    }

    @RequestMapping(value = "/fight", method = RequestMethod.POST)
    public String startFight(Model model, @RequestParam("enemy_id") long enemyId, Principal principal) {

        server.addFight(new Fight(fighterService.findByUser(getUserByUsername(principal))
                ,fighterService.findByUser(userService.findById(enemyId))));

        return "redirect:/currentFight";
    }

    @RequestMapping(value = "/friend", method = RequestMethod.POST)
    public String addFriend(Model model, @RequestParam("enemy_id") long enemyId, Principal principal) {

        UserEntity user = getUserByUsername(principal);
        user.setFriends(userService.findFriends(user));

        user.getFriends().add(userService.findById(enemyId));

        userService.save(user);

        return "redirect:/";
    }

    @RequestMapping(value = "/currentFight", method = RequestMethod.GET)
    public String currentFight(Model model, Principal principal) {

        FighterEntity userFighter = getUserByUsername(principal).getFighterEntity();
        System.out.println(server.inFight(userFighter));
        if (!server.inFight(userFighter)) {
            return "redirect:/userInfo";
        }

        Fight fight = server.fightWithFighter(userFighter).get();
        FighterEntity enemy = fight.getAnotherFighter(userFighter);
        model.addAttribute("yourFighter", fight.getThisFighter(userFighter));
        model.addAttribute("enemyFighter", enemy);
        model.addAttribute("fightLog", fight.getFightLog());

        return "fightPage";
    }

    @RequestMapping(value = "/currentFight/ThrowSpell", method = RequestMethod.POST)
    public String throwSpell(Model model,Principal principal) {

        FighterEntity userFighter = fighterService.findByUser(getUserByUsername(principal));
        Fight fight = server.fightWithFighter(userFighter).get();

        fight.addAction(new SpellDamageAction(getUserByUsername(principal),userFighter, fight.getAnotherFighter(userFighter)));
        return "redirect:/currentFight";
    }

    @RequestMapping(value = "/currentFight/ThrowRegen", method = RequestMethod.POST)
    public String throwRegen(Model model,Principal principal) {

        FighterEntity userFighter = fighterService.findByUser(getUserByUsername(principal));
        Fight fight = server.fightWithFighter(userFighter).get();

        fight.addAction(new RegenAction(getUserByUsername(principal), userFighter, 5, fight));
        return "redirect:/currentFight";
    }

    private UserEntity getUserByUsername(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }

}
