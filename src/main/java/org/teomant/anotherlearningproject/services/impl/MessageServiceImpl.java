package org.teomant.anotherlearningproject.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teomant.anotherlearningproject.entities.MessageEntity;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.repositories.MessageRepository;
import org.teomant.anotherlearningproject.services.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public void save(MessageEntity messageEntity) {
        messageRepository.save(messageEntity);
    }

    @Override
    public List<MessageEntity> findByUser(UserEntity userEntity) {
        return messageRepository.findByFrom(userEntity);
    }
}
