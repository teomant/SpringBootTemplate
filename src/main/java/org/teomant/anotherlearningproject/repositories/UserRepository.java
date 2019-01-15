package org.teomant.anotherlearningproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teomant.anotherlearningproject.entities.UserEntity;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    public Set<UserEntity> findByBefriendedContaining(UserEntity userEntity);
}
