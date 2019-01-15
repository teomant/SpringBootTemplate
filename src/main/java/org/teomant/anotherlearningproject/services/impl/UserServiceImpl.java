package org.teomant.anotherlearningproject.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.repositories.UserRepository;
import org.teomant.anotherlearningproject.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        Long id = userRepository.saveAndFlush(userEntity).getId();
        UserEntity user = userRepository.getOne(id);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Set<UserEntity> findFriends(UserEntity userEntity) {
        return userRepository.findByBefriendedContaining(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }
}
