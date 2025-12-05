package net.jahcraft.jahskills.skilltracking;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import net.jahcraft.jahskills.skillstorage.SkillDatabase;

public class BlockTracker implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer() == null) return;
		SkillDatabase.placedBlock(e.getBlock().getLocation());
	}
	
	@EventHandler
	public void onGrow(BlockGrowEvent e) {
		SkillDatabase.grownBlock(e.getBlock().getLocation());
	}
	
}
