package org.teomant.anotherlearningproject.services;

import org.teomant.anotherlearningproject.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<UserEntity> getUserById(Long id);
    UserEntity findUserByUsername(String username);
    UserEntity save(UserEntity userEntity);
    List<UserEntity> findAll();
    Set<UserEntity> findFriends(UserEntity userEntity);
    UserEntity findById(Long id);
}
