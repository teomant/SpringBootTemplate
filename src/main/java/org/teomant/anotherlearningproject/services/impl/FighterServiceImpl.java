package org.teomant.anotherlearningproject.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.FighterEntity;
import org.teomant.anotherlearningproject.repositories.FighterRepository;
import org.teomant.anotherlearningproject.services.FighterService;

import java.util.List;

@Service
public class FighterServiceImpl implements FighterService {

    @Autowired
    FighterRepository fighterRepository;

    @Override
    public FighterEntity save(FighterEntity fighterEntity) {
        return fighterRepository.save(fighterEntity);
    }

    @Override
    public FighterEntity findByUser(UserEntity userEntity) {
        return fighterRepository.findByUser(userEntity);
    }

    @Override
    public FighterEntity findByName(String name) {
        return fighterRepository.findByName(name);
    }

    @Override
    public List<FighterEntity> getAll() {
        return fighterRepository.findAll();
    }

}
