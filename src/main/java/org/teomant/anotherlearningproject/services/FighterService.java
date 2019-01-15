package org.teomant.anotherlearningproject.services;

import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.FighterEntity;

import java.util.List;

public interface FighterService {

    public FighterEntity save(FighterEntity fighterEntity);

    public FighterEntity findByUser(UserEntity userEntity);

    public FighterEntity findByName(String name);

    public List<FighterEntity> getAll();

}
