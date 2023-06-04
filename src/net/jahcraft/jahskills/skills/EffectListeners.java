package net.jahcraft.jahskills.skills;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EffectListeners implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		if (Effects.knockedOut(e.getPlayer())) {
			e.setCancelled(true);
		}
		
	}

}
