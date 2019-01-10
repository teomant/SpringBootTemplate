package org.teomant.anotherlearningproject.services;

import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.FighterEntity;

public interface FighterService {

    public FighterEntity save(FighterEntity fighterEntity);

    public FighterEntity findByUser(UserEntity userEntity);
}
