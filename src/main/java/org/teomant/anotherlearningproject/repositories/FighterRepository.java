package org.teomant.anotherlearningproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.anotherlearningproject.entities.UserEntity;
import org.teomant.anotherlearningproject.game.FighterEntity;

@Repository
public interface FighterRepository extends JpaRepository<FighterEntity, Long> {

    public FighterEntity findByUser(UserEntity userEntity);

    public FighterEntity findByName(String name);
}
