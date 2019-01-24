package org.teomant.anotherlearningproject.game;

import org.junit.BeforeClass;
import org.junit.Test;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.actions.impl.SpellDamageAction;

import static org.junit.Assert.*;

public class FightTest {

    private static FighterEntity fighter1;
    private static FighterEntity fighter2;

    private static Fight fight;

    private static UserEntity userEntity;


    @BeforeClass
    public static void prepare() {
        fighter1 = new FighterEntity();
        fighter1.setId(1L);
        fighter1.setStrength(5);
        fighter1.setAgility(5);
        fighter1.setMind(5);
        fighter2 = new FighterEntity();
        fighter2.setId(2L);
        fighter2.setStrength(5);
        fighter2.setAgility(5);
        fighter2.setMind(5);
        fight = new Fight(fighter1, fighter2);
        userEntity = new UserEntity();
    }

    @Test
    public void tick() {

        fight = new Fight(fighter1, fighter2);
        assertTrue(fight.status == Fight.Status.STARTED);
        fight.tick();
        assertTrue(fight.status == Fight.Status.IN_PROGRESS);

    }

    @Test
    public void inThisFight() {
        assertTrue(fight.inThisFight(fighter1));
    }

    @Test
    public void getAnotherFighter() {
        assertEquals(fight.getAnotherFighter(fighter1), fighter2);
    }

    @Test
    public void getThisFighter() {
        assertEquals(fight.getThisFighter(fighter1), fighter1);
    }

    @Test
    public void addAction() {
        assertFalse(fight.userPlanSomeAction(userEntity));
        fight.addAction(new SpellDamageAction(userEntity, fighter1, fighter2));
        assertTrue(fight.userPlanSomeAction(userEntity));
        fight.tick();

    }

    @Test
    public void userPlanSomeAction() {
        fight.addAction(new SpellDamageAction(userEntity, fighter1, fighter2));
        assertTrue(fight.userPlanSomeAction(userEntity));
        fight.tick();
        assertFalse(fight.userPlanSomeAction(userEntity));
    }
}