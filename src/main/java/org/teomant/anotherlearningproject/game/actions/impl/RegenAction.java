package org.teomant.anotherlearningproject.game.actions.impl;

import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.Fight;
import org.teomant.anotherlearningproject.game.FighterEntity;
import org.teomant.anotherlearningproject.game.actions.Action;

public class RegenAction extends Action {

    FighterEntity fighterEntity;
    int counter;
    Fight fight;

    public RegenAction(UserEntity userEntity, FighterEntity fighterEntity, int counter, Fight fight) {
        super(userEntity);
        this.fighterEntity = fighterEntity;
        this.counter = counter;
        this.fight = fight;
    }


    @Override
    public String execute() {
        fighterEntity.setHp(fighterEntity.getHp() + (fighterEntity.getMind() * 2 + 3));
        if (fighterEntity.getHp() > fighterEntity.getMaxHp()) {
            fighterEntity.setHp(fighterEntity.getMaxHp());
        }
        counter--;
        fight.addAction(new RegenAction(getUser(), fighterEntity, counter, fight));
        return "Healing " + fighterEntity.getName() + " for " + fighterEntity.getMind()/10 * 5 + 3;
    }
}
