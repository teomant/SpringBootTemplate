package org.teomant.anotherlearningproject.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.teomant.anotherlearningproject.entities.RoleEntity;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.services.RoleService;
import org.teomant.anotherlearningproject.services.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
public class MainRestController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/apitest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserEntity apiTest(Model model, Principal principal) {

        UserEntity userEntity = userService.findUserByUsername(principal.getName());
        List<RoleEntity> roles = roleService.getRolesByUser(userEntity);
        roles.forEach(roleEntity -> roleEntity.setUsers(Collections.emptyList()));
        userEntity.setRoles(roles);
        return userEntity;
    }


}
