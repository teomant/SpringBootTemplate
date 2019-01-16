package org.teomant.anotherlearningproject.game.actions.impl;

import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.FighterEntity;
import org.teomant.anotherlearningproject.game.actions.Action;

public class SpellDamageAction extends Action {

    FighterEntity fighterEntityOne;
    FighterEntity fighterEntityTwo;

    public SpellDamageAction(UserEntity userEntity, FighterEntity fighterEntityOne, FighterEntity fighterEntityTwo) {
        super(userEntity);
        this.fighterEntityOne = fighterEntityOne;
        this.fighterEntityTwo = fighterEntityTwo;
    }

    @Override
    public String execute() {

        fighterEntityTwo.setHp(fighterEntityTwo.getHp() - fighterEntityOne.getMind() * 15 / fighterEntityTwo.getMind());
        return "Damaging " + fighterEntityTwo.getName() + " for "
                + fighterEntityOne.getMind() * 15 / fighterEntityTwo.getMind();
    }
}
