package org.teomant.anotherlearningproject.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Fighter {

    private String name;

    private double strength;
    private double agility;
    private double mind;
    private int hp;
    private int damage;
    private int maxHp;


    public Fighter(String name, double strength, double agility, double mind) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.mind = mind;
        this.maxHp = 50 + 10 * (int)strength;
        this.hp = maxHp;
        this.damage = (int)(1.5 * (3 + (1 + strength) * (3.5 - 3 / (agility + 1))));
    }
}
