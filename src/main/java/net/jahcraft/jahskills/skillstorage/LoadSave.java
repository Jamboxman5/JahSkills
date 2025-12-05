package net.jahcraft.jahskills.skillstorage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.jahcraft.jahskills.skilltracking.ProgressBar;

public class LoadSave implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		SkillDatabase.load(e.getPlayer());
		SkillManager.updatePrefixes(e.getPlayer());
		ProgressBar.createBar(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		SkillDatabase.save(e.getPlayer());
	}

}
