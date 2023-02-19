package net.emmecilab.spring.batch.job;

import org.springframework.batch.item.ItemProcessor;
import net.emmecilab.spring.batch.model.Player;

public class PlayerItemProcessor implements ItemProcessor<Player, Player> {

	@Override
	public Player process(Player player) throws Exception {
		if (player.getPosition().equalsIgnoreCase("forward")) {
			return player;
		} else {
			return null;
		}
	}
}