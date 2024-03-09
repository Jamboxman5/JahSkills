package net.jahcraft.jahskills.skilltracking;

import java.math.BigDecimal;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class ExpEvents implements Listener {
	
	private static HashMap<Entity, Player> targetStorage = new HashMap<>();
	private final double levelScaler = .88;
	private final double randomMultiplierCap = 20;
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBreak(BlockBreakEvent e) {
		
		if (e.isCancelled()) return;
		if (e.getPlayer().hasPermission("jahskills.freezexp")) return;
				
		if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;
		
		if (e.getPlayer() == null) return;
		Player p = e.getPlayer();
		
		BigDecimal baseProgress = BigDecimal.valueOf(.01);
		BigDecimal factor = BigDecimal.valueOf(levelScaler).pow(SkillManager.getLevel(p));
		BigDecimal multiplier = BigDecimal.valueOf(getMultiplier(e.getBlock().getType()));
		
		SkillManager.addProgress(p, baseProgress.multiply(factor).multiply(multiplier));
		ProgressBar.updateBar(p);
		
	}
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
						
//		if (ButcherEffects.theGrindrMobs.contains(e.getEntity())) return;
		if (!targetStorage.containsKey(e.getEntity())) return;
		Player p = targetStorage.get(e.getEntity());
		if (p.hasPermission("jahskills.freezexp")) return;

		BigDecimal baseProgress = BigDecimal.valueOf(.03);
		BigDecimal factor = BigDecimal.valueOf(levelScaler).pow(SkillManager.getLevel(p));
		BigDecimal multiplier = BigDecimal.valueOf(getMultiplier(e.getEntityType()));
		
		SkillManager.addProgress(p, baseProgress.multiply(factor).multiply(multiplier));
		ProgressBar.updateBar(p);
		
	}
	
	@EventHandler
	public void onKill(PlayerFishEvent e) {
		if (e.getState() != State.CAUGHT_FISH) return;
		if (e.getPlayer().hasPermission("jahskills.freezexp")) return;

		BigDecimal baseProgress = BigDecimal.valueOf(.03);
		BigDecimal factor = BigDecimal.valueOf(levelScaler).pow(SkillManager.getLevel(e.getPlayer()));
		BigDecimal multiplier = BigDecimal.valueOf(getMultiplier());
		
		SkillManager.addProgress(e.getPlayer(), baseProgress.multiply(factor).multiply(multiplier));
		ProgressBar.updateBar(e.getPlayer());
		
	}
	
	private double getMultiplier() {
		return Math.random() * randomMultiplierCap;
	}

	@EventHandler
	public void onTarget(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		targetStorage.put(e.getEntity(), (Player)e.getDamager());
	}

	private int getMultiplier(EntityType type) {
		if (type.equals(EntityType.PLAYER)) return 25;
		if (type.equals(EntityType.ZOMBIE)) return 5;
		if (type.equals(EntityType.SKELETON)) return 5;
		if (type.equals(EntityType.SPIDER)) return 5;
		if (type.equals(EntityType.CAVE_SPIDER)) return 8;
		if (type.equals(EntityType.CREEPER)) return 10;
		if (type.equals(EntityType.GUARDIAN)) return 10;
		if (type.equals(EntityType.STRAY)) return 8;
		if (type.equals(EntityType.HUSK)) return 8;
		if (type.equals(EntityType.GHAST)) return 50;
		if (type.equals(EntityType.BLAZE)) return 25;
		if (type.equals(EntityType.IRON_GOLEM)) return 75;
		if (type.equals(EntityType.EVOKER)) return 50;
		if (type.equals(EntityType.VEX)) return 20;
		if (type.equals(EntityType.PILLAGER)) return 15;
		if (type.equals(EntityType.ENDERMAN)) return 40;
		if (type.equals(EntityType.WITCH)) return 100;
		if (type.equals(EntityType.ELDER_GUARDIAN)) return 250;
		if (type.equals(EntityType.WARDEN)) return 500;
		if (type.equals(EntityType.ENDER_DRAGON)) return 250;
		return 1;
		
	}
	
	private double getMultiplier(Material type) {
		if (type.toString().contains("EMERALD")) return 150;
		if (type.toString().contains("DEBRIS")) return 500;
		if (type.toString().contains("DIAMOND")) return 75;
		if (type.toString().contains("GOLD")) return 30;
		if (type.toString().contains("QUARTZ")) return 60;
		if (type.toString().contains("IRON")) return 15;
		if (type.toString().contains("COPPER")) return 10;
		if (type.toString().contains("LAPIS")) return 25;
		if (type.toString().contains("REDSTONE")) return 20;
		if (type.toString().contains("COAL")) return 10;
		if (type.toString().contains("WHEAT")) return 5;
		if (type.toString().contains("SUGAR")) return 5;
		if (type.toString().contains("CARROT")) return 5;
		if (type.toString().contains("POTATO")) return 5;
		if (type.toString().contains("MELON")) return 5;
		if (type.toString().contains("PUMPKIN")) return 5;
		if (type.toString().contains("BEET")) return 5;
		if (type.toString().contains("BEANS")) return 5;
		if (type.toString().contains("LOG")) return 4;
		if (type.toString().contains("BLACKSTONE")) return 2;
		if (type.toString().contains("NETHERRACK")) return 1.25;
		return 1;
		
	}
	
}
