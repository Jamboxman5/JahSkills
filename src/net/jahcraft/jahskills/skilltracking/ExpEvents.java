package net.jahcraft.jahskills.skilltracking;

import java.math.BigDecimal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class ExpEvents implements Listener {

	private final double skillFactor = 1000.0;
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
				
		if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;
		
		if (e.getPlayer() == null) return;
		Player p = e.getPlayer();
		
		BigDecimal baseProgress = BigDecimal.valueOf(1.0/(SkillManager.getLevel(p)*skillFactor));
		BigDecimal multiplier = BigDecimal.valueOf(getMultiplier(e.getBlock().getType()));
		
		SkillManager.addProgress(p, baseProgress.multiply(multiplier));
		ProgressBar.updateBar(p);
		
	}

	private double getMultiplier(Material type) {
		return 100;
	}
	
}
