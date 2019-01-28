package org.teomant.anotherlearningproject.services;

import org.teomant.anotherlearningproject.entities.RoleEntity;
import org.teomant.anotherlearningproject.entities.UserEntity;

import java.util.List;

public interface RoleService {

    RoleEntity save(RoleEntity roleEntity);
    List<RoleEntity> getRolesByUser(UserEntity userEntity);
    RoleEntity getUserRole();
    RoleEntity getAdminRole();

}
