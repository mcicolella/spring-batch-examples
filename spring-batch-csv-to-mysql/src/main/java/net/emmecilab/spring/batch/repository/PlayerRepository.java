package net.emmecilab.spring.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.emmecilab.spring.batch.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

}
