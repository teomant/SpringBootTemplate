package org.teomant.anotherlearningproject.game;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Server {

    private CopyOnWriteArrayList<Fight> fights = new CopyOnWriteArrayList();

    @Scheduled(fixedRate = 3000)
    void tick() {
        System.out.println("tick");
        fights.parallelStream().forEach(f -> {
            if (f.status == Fight.Status.COMPLITED) {
                removeFight(f);
            } else {
                f.tick();
            }
        });
        if (fights.size() > 0) {
            System.out.println("Fights in progress: " + fights.size());
        }
    }

    public void addFight(Fight fight) {
        fights.add(fight);
    }

    public void removeFight(Fight fight) {
        fights.remove(fight);
    }

    public boolean inFight(FighterEntity fighterEntity) {
        return fights.stream().anyMatch(f -> f.inThisFight(fighterEntity));
    }

    public Optional<Fight> fightWithFighter(FighterEntity fighterEntity) {
        return fights.stream().filter(f -> f.inThisFight(fighterEntity)).findFirst();
    }
}
