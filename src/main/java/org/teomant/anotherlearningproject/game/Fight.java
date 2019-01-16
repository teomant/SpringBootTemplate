package org.teomant.anotherlearningproject.game;

import lombok.Getter;
import lombok.ToString;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.actions.Action;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@ToString
public class Fight {

    @Getter
    String fightLog ="";

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
            fightLog += fighterEntityOne.getName() + " and " + fighterEntityTwo.getName()
                    + " begin thier fight!\n";
            fighterEntityOne.setHp(fighterEntityOne.getMaxHp());
            fighterEntityTwo.setHp(fighterEntityTwo.getMaxHp());
            status = Status.IN_PROGRESS;
            return;
        }
        if (status == Status.IN_PROGRESS) {

            for (Action action : actionsList) {
                fightLog += fightLog + action.execute() + '\n';
                actionsList.remove(action);
            }

            fightLog += fighterEntityOne.getName() + "(" + fighterEntityOne.getHp() + ")-"
                    + fighterEntityTwo.getName() + "(" + fighterEntityTwo.getHp() + "): "
                    + fighterEntityOne.getDamage() + "-" + fighterEntityTwo.getDamage() + '\n';

            fighterEntityOne.setHp(fighterEntityOne.getHp()- fighterEntityTwo.getDamage());
            fighterEntityTwo.setHp(fighterEntityTwo.getHp()- fighterEntityOne.getDamage());
            if (fighterEntityOne.getHp() < 1 || fighterEntityTwo.getHp() < 1) {
                status = Status.RESULT;
            }
            return;
        }
        if (status == Status.RESULT) {
            if (fighterEntityOne.getHp() < 1 && fighterEntityTwo.getHp() < 1) {
                fightLog += fighterEntityOne.getName() + "-"
                        + fighterEntityTwo.getName() + ": TIE\n";
            } else if (fighterEntityOne.getHp() < 1) {
                fightLog += fighterEntityOne.getName() + "-"
                        + fighterEntityTwo.getName() + ": " + fighterEntityTwo.getName() + " WIN\n";
            } else {
                fightLog += fighterEntityOne.getName() + "-"
                        + fighterEntityTwo.getName() + ": " + fighterEntityOne.getName() + " WIN\n";
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

    public FighterEntity getThisFighter(FighterEntity fighterEntity) {
        if (!inThisFight(fighterEntity)) {
            return null;
        }
        if (fighterEntityOne.equals(fighterEntity)) {
            return fighterEntityOne;
        } else {
            return fighterEntityTwo;
        }
    }


    public void addAction (Action action) {
        if (!userPlanSomeAction(action.getUser())) {
            actionsList.add(action);
        }
    }

    public boolean userPlanSomeAction(UserEntity userEntity) {
        return this.actionsList.stream().map(Action::getUser).collect(Collectors.toSet()).contains(userEntity);
    }

}
