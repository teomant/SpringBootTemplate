package org.teomant.anotherlearningproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teomant.anotherlearningproject.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

}
