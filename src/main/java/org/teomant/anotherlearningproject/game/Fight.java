package org.teomant.anotherlearningproject.game;

import lombok.ToString;

@ToString
public class Fight {

    public Fight(Fighter fighterOne, Fighter fighterTwo) {
        this.fighterOne = fighterOne;
        this.fighterTwo = fighterTwo;
        status = Status.STARTED;
    }

    public enum Status { STARTED, IN_PROGRESS, RESULT, COMPLITED};


    private Fighter fighterOne;
    private Fighter fighterTwo;

    Status status;

    public void tick() {
        if (status == Status.STARTED) {
            System.out.println(fighterOne.getName() + " and " + fighterTwo.getName()
                    + " begin thier fight!");
            fighterOne.setHp(fighterOne.getMaxHp());
            fighterTwo.setHp(fighterTwo.getMaxHp());
            status = Status.IN_PROGRESS;
            return;
        }
        if (status == Status.IN_PROGRESS) {
            System.out.println(fighterOne.getName() + "(" + fighterOne.getHp() + ")-"
                    + fighterTwo.getName() + "(" + fighterTwo.getHp() + "): "
                    + fighterOne.getDamage() + "-" + fighterTwo.getDamage());
            fighterOne.setHp(fighterOne.getHp()-fighterTwo.getDamage());
            fighterTwo.setHp(fighterTwo.getHp()-fighterOne.getDamage());
            if (fighterOne.getHp() < 1 || fighterTwo.getHp() < 1) {
                status = Status.RESULT;
            }
            return;
        }
        if (status == Status.RESULT) {
            if (fighterOne.getHp() < 1 && fighterTwo.getHp() < 1) {
                System.out.println(fighterOne.getName() + "-" 
                        + fighterTwo.getName() + ": TIE");
            } else if (fighterOne.getHp() < 1) {
                System.out.println(fighterOne.getName() + "-"
                        + fighterTwo.getName() + ": " + fighterTwo.getName() + " WIN");
            } else {
                System.out.println(fighterOne.getName() + "-"
                        + fighterTwo.getName() + ": " + fighterOne.getName() + " WIN");
            }
            status = Status.COMPLITED;
        }
    }

    public boolean inThisFight(Fighter fighter) {
        return fighter.equals(fighterOne) || fighter.equals(fighterTwo);
    }

}
