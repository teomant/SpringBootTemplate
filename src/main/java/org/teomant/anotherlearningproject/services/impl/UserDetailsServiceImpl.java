package org.teomant.anotherlearningproject.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.services.RoleService;
import org.teomant.anotherlearningproject.services.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserEntity user = userService.findUserByUsername(username);
        user.setRoles(roleService.getRolesByUser(user));
        User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.disabled(false);
            builder.password(user.getPassword());
            String[] roles = user.getRoles()
                    .stream().map(a -> a.getRoleName()).toArray(String[]::new);
            builder.authorities(roles);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}
