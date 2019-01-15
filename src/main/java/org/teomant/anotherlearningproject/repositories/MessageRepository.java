package org.teomant.anotherlearningproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teomant.anotherlearningproject.entities.MessageEntity;
import org.teomant.anotherlearningproject.entities.UserEntity;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    public List<MessageEntity> findByFrom(UserEntity userEntity);
}
