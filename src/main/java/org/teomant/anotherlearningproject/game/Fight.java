package org.teomant.anotherlearningproject.game;

import lombok.ToString;
import org.teomant.anotherlearningproject.game.actions.Action;

import java.util.concurrent.CopyOnWriteArrayList;

@ToString
public class Fight {

    public Fight(FighterEntity fighterEntityOne, FighterEntity fighterEntityTwo) {
        this.fighterEntityOne = fighterEntityOne;
        this.fighterEntityTwo = fighterEntityTwo;
        status = Status.STARTED;
    }

    public enum Status { STARTED, IN_PROGRESS, RESULT, COMPLITED };

    private FighterEntity fighterEntityOne;
    private FighterEntity fighterEntityTwo;

    private CopyOnWriteArrayList<Action> actionsList = new CopyOnWriteArrayList();

    Status status;

    public void tick() {
        if (status == Status.STARTED) {
            System.out.println(fighterEntityOne.getName() + " and " + fighterEntityTwo.getName()
                    + " begin thier fight!");
            fighterEntityOne.setHp(fighterEntityOne.getMaxHp());
            fighterEntityTwo.setHp(fighterEntityTwo.getMaxHp());
            status = Status.IN_PROGRESS;
            return;
        }
        if (status == Status.IN_PROGRESS) {

            for (Action action : actionsList) {
                action.execute();
                actionsList.remove(action);
            }

            System.out.println(fighterEntityOne.getName() + "(" + fighterEntityOne.getHp() + ")-"
                    + fighterEntityTwo.getName() + "(" + fighterEntityTwo.getHp() + "): "
                    + fighterEntityOne.getDamage() + "-" + fighterEntityTwo.getDamage());

            fighterEntityOne.setHp(fighterEntityOne.getHp()- fighterEntityTwo.getDamage());
            fighterEntityTwo.setHp(fighterEntityTwo.getHp()- fighterEntityOne.getDamage());
            if (fighterEntityOne.getHp() < 1 || fighterEntityTwo.getHp() < 1) {
                status = Status.RESULT;
            }
            return;
        }
        if (status == Status.RESULT) {
            if (fighterEntityOne.getHp() < 1 && fighterEntityTwo.getHp() < 1) {
                System.out.println(fighterEntityOne.getName() + "-"
                        + fighterEntityTwo.getName() + ": TIE");
            } else if (fighterEntityOne.getHp() < 1) {
                System.out.println(fighterEntityOne.getName() + "-"
                        + fighterEntityTwo.getName() + ": " + fighterEntityTwo.getName() + " WIN");
            } else {
                System.out.println(fighterEntityOne.getName() + "-"
                        + fighterEntityTwo.getName() + ": " + fighterEntityOne.getName() + " WIN");
            }
            status = Status.COMPLITED;
        }
    }

    public boolean inThisFight(FighterEntity fighterEntity) {
        return fighterEntity.equals(fighterEntityOne) || fighterEntity.equals(fighterEntityTwo);
    }

    public FighterEntity getAnotherFighter(FighterEntity fighterEntity) {
        if (!inThisFight(fighterEntity)) {
            return null;
        }
        if (fighterEntityOne.equals(fighterEntity)) {
            return fighterEntityTwo;
        } else {
            return fighterEntityOne;
        }
    }

    public void addAction (Action action) {
        actionsList.add(action);
    }

}
