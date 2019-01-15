package org.teomant.anotherlearningproject.services;

import org.teomant.anotherlearningproject.entities.MessageEntity;
import org.teomant.anotherlearningproject.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MessageService {

    void save(MessageEntity messageEntity);
    List<MessageEntity> findByUser(UserEntity userEntity);
}
