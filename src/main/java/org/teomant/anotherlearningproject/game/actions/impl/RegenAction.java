package org.teomant.anotherlearningproject.game.actions.impl;

import org.teomant.anotherlearningproject.game.Fight;
import org.teomant.anotherlearningproject.game.FighterEntity;
import org.teomant.anotherlearningproject.game.actions.Action;

public class RegenAction implements Action {

    FighterEntity fighterEntity;
    int counter;
    Fight fight;

    public RegenAction(FighterEntity fighterEntity, int counter, Fight fight) {
        this.fighterEntity = fighterEntity;
        this.counter = counter;
        this.fight = fight;
    }


    @Override
    public void execute() {
        System.out.println("Healing " + fighterEntity.getName() + " for " + fighterEntity.getMind()/10 * 5 + 3);
        fighterEntity.setHp(fighterEntity.getHp() + (fighterEntity.getMind() * 2 + 3));
        if (fighterEntity.getHp() > fighterEntity.getMaxHp()) {
            fighterEntity.setHp(fighterEntity.getMaxHp());
        }
        counter--;
        fight.addAction(new RegenAction(fighterEntity, counter, fight));
    }
}
