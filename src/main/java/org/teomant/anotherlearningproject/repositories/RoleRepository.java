package org.teomant.anotherlearningproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teomant.anotherlearningproject.entities.RoleEntity;
import org.teomant.anotherlearningproject.entities.UserEntity;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findByUsersContaining(UserEntity userEntity);

    RoleEntity findByRoleName(String name);
}
