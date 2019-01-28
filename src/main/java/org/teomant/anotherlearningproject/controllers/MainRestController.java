package org.teomant.anotherlearningproject.controllers;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.teomant.anotherlearningproject.entities.RoleEntity;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.services.RoleService;
import org.teomant.anotherlearningproject.services.UserService;

import java.security.Principal;
import java.util.*;

@RestController
public class MainRestController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/api/test", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserEntity apiTest(Model model, Principal principal) {

        UserEntity userEntity = userService.findUserByUsername(principal.getName());
        List<RoleEntity> roles = roleService.getRolesByUser(userEntity);
        roles.forEach(roleEntity -> roleEntity.setUsers(Collections.emptyList()));
        Set<UserEntity> friends = userService.findFriends(userEntity);
        friends.forEach(user -> {
            user.setFriends(Collections.EMPTY_SET);
            user.setBefriended(Collections.EMPTY_SET);
            user.setReceivedMessages(Collections.EMPTY_LIST);
            user.setSentMessages(Collections.EMPTY_LIST);
            user.setFighterEntity(null);
            user.setRoles(Collections.EMPTY_LIST);
        });
        userEntity.setFriends(friends);
        userEntity.setBefriended(friends);
        userEntity.setRoles(roles);
        userEntity.getFighterEntity().setUser(null);
        userEntity.getSentMessages().forEach(messageEntity -> {
            messageEntity.setFrom(null);
            UserEntity userTo = new UserEntity();
            userTo.setId(messageEntity.getTo().getId());
            messageEntity.setTo(userTo);
        });
        userEntity.getReceivedMessages().forEach(messageEntity -> {
            UserEntity userFrom = new UserEntity();
            userFrom.setId(messageEntity.getFrom().getId());
            messageEntity.setFrom(userFrom);
            messageEntity.setTo(null);
        });
        return userEntity;
    }

    @RequestMapping(value = "/api/create", method = RequestMethod.POST
            , headers="Content-Type=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean apiCreateUser(Model model, Principal principal
            , @RequestBody String jsonString) {

        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            return false;
        }

        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");

        if (isRoleAdmin(principal)) {
            if (userService.findUserByUsername(username) != null) {
                return false;
            }
            UserEntity newUser = new UserEntity();
            newUser.setUsername(username);
            newUser.setPassword(new BCryptPasswordEncoder().encode(password));
            newUser.setRoles(Arrays.asList(roleService.getUserRole()));
            newUser.setEnabled(1);
            userService.save(newUser);
            return true;
        }

        return false;
    }

    @RequestMapping(value = "/api/makeAdmin", method = RequestMethod.POST
            , headers="Content-Type=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean makeAdmin(Model model, Principal principal
            , @RequestBody String jsonString) {

        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            return false;
        }

        String username = (String) jsonObject.get("username");

        if (isRoleAdmin(principal)) {

            UserEntity user = userService.findUserByUsername(username);
            user.setRoles(roleService.getRolesByUser(user));
            if (!isRoleAdmin(user)) {
                user.getRoles().add(roleService.getAdminRole());
                userService.save(user);
            }
            return true;
        }

        return false;
    }

    private boolean isRoleAdmin(Principal principal) {

        return isRoleAdmin(userService.findUserByUsername(principal.getName()));
    }

    private boolean isRoleAdmin(UserEntity userEntity) {

        return roleService.getRolesByUser(userEntity)
                .stream().map(roleEntity -> roleEntity.getRoleName()).anyMatch(s -> s.equals("ROLE_ADMIN"));
    }


}
