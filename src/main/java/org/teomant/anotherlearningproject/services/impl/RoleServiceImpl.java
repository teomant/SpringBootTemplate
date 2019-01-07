package org.teomant.anotherlearningproject.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teomant.anotherlearningproject.entities.RoleEntity;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.repositories.RoleRepository;
import org.teomant.anotherlearningproject.services.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleEntity save(RoleEntity authoritiesEntity) {
        return roleRepository.save(authoritiesEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleEntity> getRolesByUser(UserEntity userEntity) {
        return roleRepository.findByUsersContaining(userEntity);
    }

    public RoleEntity getUserRole() {
        return roleRepository.findByRoleName("ROLE_USER");
    }
}
