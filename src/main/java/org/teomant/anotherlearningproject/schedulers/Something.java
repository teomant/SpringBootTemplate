package org.teomant.anotherlearningproject.schedulers;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Something {

    @Scheduled(fixedRate = 10000)
    void doSomething() {
        System.out.println("testing schedulers");
    }
}
