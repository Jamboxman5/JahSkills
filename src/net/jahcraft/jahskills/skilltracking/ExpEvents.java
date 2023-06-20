package net.jahcraft.jahskills.skilltracking;

import java.math.BigDecimal;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class ExpEvents implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
				
		if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;
		
		if (e.getPlayer() == null) return;
		Player p = e.getPlayer();
		
		BigDecimal baseProgress = BigDecimal.valueOf(.01);
		BigDecimal factor = BigDecimal.valueOf(.75).pow(SkillManager.getLevel(p));
		BigDecimal multiplier = BigDecimal.valueOf(getMultiplier(e.getBlock().getType()));
		
		SkillManager.addProgress(p, baseProgress.multiply(factor).multiply(multiplier));
		ProgressBar.updateBar(p);
		
	}
	
	@EventHandler
	public void onKill(EntityDamageByEntityEvent e) {
						
		if (!(e.getDamager() instanceof Player)) return;
		Player p = (Player) e.getDamager();
		
		BigDecimal baseProgress = BigDecimal.valueOf(.05);
		BigDecimal factor = BigDecimal.valueOf(.75).pow(SkillManager.getLevel(p));
		BigDecimal multiplier = BigDecimal.valueOf(getMultiplier(e.getEntityType()));
		
		SkillManager.addProgress(p, baseProgress.multiply(factor).multiply(multiplier));
		ProgressBar.updateBar(p);
		
	}

	private int getMultiplier(EntityType type) {
		if (type.equals(EntityType.PLAYER)) return 15;
		if (type.equals(EntityType.ZOMBIE)) return 3;
		if (type.equals(EntityType.SKELETON)) return 3;
		if (type.equals(EntityType.SPIDER)) return 3;
		if (type.equals(EntityType.CAVE_SPIDER)) return 5;
		if (type.equals(EntityType.CREEPER)) return 3;
		if (type.equals(EntityType.GUARDIAN)) return 5;
		if (type.equals(EntityType.STRAY)) return 5;
		if (type.equals(EntityType.HUSK)) return 5;
		if (type.equals(EntityType.GHAST)) return 25;
		if (type.equals(EntityType.BLAZE)) return 25;
		if (type.equals(EntityType.IRON_GOLEM)) return 50;
		if (type.equals(EntityType.EVOKER)) return 15;
		if (type.equals(EntityType.VEX)) return 15;
		if (type.equals(EntityType.PILLAGER)) return 10;
		if (type.equals(EntityType.ENDERMAN)) return 40;
		if (type.equals(EntityType.ELDER_GUARDIAN)) return 200;
		if (type.equals(EntityType.WARDEN)) return 500;
		if (type.equals(EntityType.ENDER_DRAGON)) return 250;
		return 1;
		
	}
	
	private int getMultiplier(Material type) {
		if (type.toString().contains("EMERALD")) return 10;
		if (type.toString().contains("DIAMOND")) return 8;
		if (type.toString().contains("GOLD")) return 7;
		if (type.toString().contains("IRON")) return 6;
		if (type.toString().contains("LAPIS")) return 6;
		if (type.toString().contains("REDSTONE")) return 6;
		if (type.toString().contains("COAL")) return 5;
		if (type.toString().contains("WHEAT")) return 4;
		if (type.toString().contains("SUGAR")) return 4;
		if (type.toString().contains("CARROT")) return 4;
		if (type.toString().contains("POTATO")) return 4;
		if (type.toString().contains("MELON")) return 4;
		if (type.toString().contains("PUMPKIN")) return 4;
		if (type.toString().contains("BEET")) return 4;
		if (type.toString().contains("BEANS")) return 4;
		if (type.toString().contains("LOG")) return 3;
		return 1;
		
	}
	
}
