package org.teomant.anotherlearningproject.game.actions.impl;

import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.FighterEntity;
import org.teomant.anotherlearningproject.game.actions.Action;

public class HealAction extends Action {

    FighterEntity fighterEntity;

    public HealAction(UserEntity userEntity, FighterEntity fighterEntity) {
        super(userEntity);
        this.fighterEntity = fighterEntity;
    }

    @Override
    public String execute() {
        fighterEntity.setHp(fighterEntity.getHp() + (fighterEntity.getMind()/10 * 30 + 15));
        if (fighterEntity.getHp() > fighterEntity.getMaxHp()) {
            fighterEntity.setHp(fighterEntity.getMaxHp());
        }
        return "Healing " + fighterEntity.getName() + " for " + fighterEntity.getMind()/10 * 30 + 15;
    }
}
