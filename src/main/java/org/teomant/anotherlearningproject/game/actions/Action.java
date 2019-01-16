package org.teomant.anotherlearningproject.game.actions;

import lombok.Getter;
import org.teomant.anotherlearningproject.entities.UserEntity;

public abstract class Action {

    @Getter
    private final UserEntity user;

    public Action(UserEntity userEntity) {
        user = userEntity;
    }

    public abstract String execute();
}
