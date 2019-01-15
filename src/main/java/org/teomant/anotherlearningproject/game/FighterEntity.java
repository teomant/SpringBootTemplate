package org.teomant.anotherlearningproject.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.teomant.anotherlearningproject.entities.UserEntity;

import javax.persistence.*;
import java.util.Random;

@Getter
@Setter
@EqualsAndHashCode(of = "id, name, user")
@Entity
@Table(name = "fighters")
public class FighterEntity {

    @Id
    @SequenceGenerator( name = "fighters_sequence", sequenceName = "fighters_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "fighters_sequence" )
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "strength", nullable = false)
    private int strength;

    @Column(name = "agility", nullable = false)
    private int agility;

    @Column(name = "mind", nullable = false)
    private int mind;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private int hp;

    public FighterEntity() {

    }

    public FighterEntity(String name, int strength, int agility, int mind) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.mind = mind;
        this.hp = getMaxHp();
    }

    public int getMaxHp() {
        return 50 + 20 * strength;
    }

    public int getDamage() {
        Random random = new Random();
        if (random.nextInt(100) > 100 * (1 - 1 / 2 / mind)) {
            return 0;
        }
        return (int)(1.5 * (3 + (1 + strength) * (3.5 - 3 / (agility + 1))));
    }

    @Override
    public String toString() {
        return "FighterEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", strength=" + strength +
                ", agility=" + agility +
                ", mind=" + mind +
                ", user=" + user.getId() +
                ", hp=" + hp +
                '}';
    }
}
