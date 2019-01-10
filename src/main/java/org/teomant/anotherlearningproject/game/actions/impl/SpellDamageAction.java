package org.teomant.anotherlearningproject.game.actions.impl;

import org.teomant.anotherlearningproject.game.FighterEntity;
import org.teomant.anotherlearningproject.game.actions.Action;

public class SpellDamageAction implements Action {

    FighterEntity fighterEntityOne;
    FighterEntity fighterEntityTwo;

    public SpellDamageAction(FighterEntity fighterEntityOne, FighterEntity fighterEntityTwo) {
        this.fighterEntityOne = fighterEntityOne;
        this.fighterEntityTwo = fighterEntityTwo;
    }

    @Override
    public void execute() {

        System.out.println("Damaging " + fighterEntityTwo.getName() + " for " + fighterEntityOne.getMind() * 15 / fighterEntityTwo.getMind());

        fighterEntityTwo.setHp(fighterEntityTwo.getHp() - fighterEntityOne.getMind() * 15 / fighterEntityTwo.getMind());
    }
}
