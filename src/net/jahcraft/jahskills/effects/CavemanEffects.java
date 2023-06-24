package net.jahcraft.jahskills.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class CavemanEffects implements Listener {

	SkillType type = SkillType.CAVEMAN;
	
	private int getRandom(int i) { return (int) (Math.random() * (i+1)); }
	void debugMSG(String s) { Bukkit.broadcastMessage(s); }
	
	@EventHandler
	public void motherlode(BlockBreakEvent e) {
		
		//INITIAL CHECKS (IS THIS EVENT ELIGIBLE FOR CONSIDERATION?)
		
		if (e.getPlayer() == null) return;
		if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.MOTHERLODE)) return;
		if (!e.getBlock().getType().toString().contains("ORE") &&
				!e.getBlock().getType().toString().contains("DEBRIS")) return;
		
		//INITIALIZE TOOLS
		
		Player p = e.getPlayer();
		ItemStack tool = p.getInventory().getItemInMainHand();
		int level = SkillManager.getLevel(p, type);
		Collection<ItemStack> originalDrops = e.getBlock().getDrops(tool);
		Location location = e.getBlock().getLocation();
		World world = location.getWorld();

		//SECONDARY CHECKS (IS THIS EVENT VALID FOR MANIPULATION?)
		
		if (originalDrops.size() == 0) return;
		if (p.getGameMode().equals(GameMode.CREATIVE)) return;

		//ROLLS (ROLL FOR CHANCE FOR PERK/MODIFIERS TO TAKE EFFECT)
		
		double chance = (level*5);
		double multiplier = 2.0;

		if (getRandom(100) > chance) return;
		if (getRandom(100) >= 75 && level >= 20) multiplier += 1.0;
		
		//DO THE THING

		{
			e.setDropItems(false);
			List<ItemStack> newDrops = new ArrayList<>();
			for (ItemStack i : originalDrops) {
				i.setAmount((int)(i.getAmount() * multiplier));
				newDrops.add(i);
			}
			
			for (ItemStack i : newDrops) {
				world.dropItemNaturally(location, i);
			}
		}
		
		//DONE!
		
	}
	@EventHandler
	public void oreWhisperer(BlockBreakEvent e) {
		
		//INITIAL CHECKS (IS THIS EVENT ELIGIBLE FOR CONSIDERATION?)
		
		if (e.getPlayer() == null) return;
		if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;
		if (e.getExpToDrop() <= 0) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.OREWHISPERER)) return;
		if (!e.getBlock().getType().toString().contains("ORE") &&
				!e.getBlock().getType().toString().contains("DEBRIS")) return;
		
		//INITIALIZE TOOLS
		
		Player p = e.getPlayer();
		int level = SkillManager.getLevel(p, type);

		//SECONDARY CHECKS (IS THIS EVENT VALID FOR MANIPULATION?)
		
		if (p.getGameMode().equals(GameMode.CREATIVE)) return;

		//ROLLS (ROLL FOR CHANCE FOR PERK/MODIFIERS TO TAKE EFFECT)
		
		double chance = (level*5);
		double multiplier = 1.5;

		if (getRandom(100) > chance) return;
		if (getRandom(100) >= 50 && level >= 20) multiplier += 1.5;
		
		//DO THE THING

		{
			int originalPoints = e.getExpToDrop();
			int newPoints = (int) (originalPoints * multiplier);
			e.setExpToDrop(newPoints);
		}
		
		//DONE!
		
	}
	@EventHandler
	public void efficientDigger(BlockBreakEvent e) {
		
		//INITIAL CHECKS (IS THIS EVENT ELIGIBLE FOR CONSIDERATION?)
		
		if (e.getPlayer() == null) return;
		if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.EFFICIENTDIGGER)) return;
		if (!e.getBlock().getType().equals(Material.STONE) &&
			!e.getBlock().getType().equals(Material.TUFF) &&
			!e.getBlock().getType().equals(Material.GRANITE) &&
			!e.getBlock().getType().equals(Material.DIORITE) &&
			!e.getBlock().getType().equals(Material.ANDESITE) &&
			!e.getBlock().getType().equals(Material.NETHERRACK) &&
			!e.getBlock().getType().equals(Material.BASALT) &&
			!e.getBlock().getType().equals(Material.DEEPSLATE) &&
			!e.getBlock().getType().equals(Material.BLACKSTONE) &&
			!e.getBlock().getType().toString().contains("TERRACOTTA") &&
			!e.getBlock().getType().toString().contains("NYLIUM") &&
			!e.getBlock().getType().equals(Material.SMOOTH_BASALT) &&
			!e.getBlock().getType().equals(Material.CALCITE) &&
			!e.getBlock().getType().equals(Material.DRIPSTONE_BLOCK) &&
			!e.getBlock().getType().equals(Material.MAGMA_BLOCK) &&
			!e.getBlock().getType().equals(Material.END_STONE) &&
			!e.getBlock().getType().equals(Material.OBSIDIAN) &&
			!e.getBlock().getType().equals(Material.NETHER_BRICKS)) return;
		
		//INITIALIZE TOOLS
		
		Player p = e.getPlayer();
		ItemStack tool = p.getInventory().getItemInMainHand();
		int level = SkillManager.getLevel(p, type);
		Collection<ItemStack> originalDrops = e.getBlock().getDrops(tool);
		Location location = e.getBlock().getLocation();
		World world = location.getWorld();

		//SECONDARY CHECKS (IS THIS EVENT VALID FOR MANIPULATION?)
		
		if (originalDrops.size() == 0) return;
		if (p.getGameMode().equals(GameMode.CREATIVE)) return;

		//ROLLS (ROLL FOR CHANCE FOR PERK/MODIFIERS TO TAKE EFFECT)
		
		double chance = (level*5);
		double multiplier = 2.0;

		if (getRandom(100) > chance) return;
		if (getRandom(100) >= 75 && level >= 20) multiplier += 1.0;
		
		//DO THE THING

		{
			e.setDropItems(false);
			List<ItemStack> newDrops = new ArrayList<>();
			for (ItemStack i : originalDrops) {
				i.setAmount((int)(i.getAmount() * multiplier));
				newDrops.add(i);
			}
			
			for (ItemStack i : newDrops) {
				world.dropItemNaturally(location, i);
			}
		}
		
		//DONE!
		
	}
	@EventHandler
	public void climbingGear(EntityDamageEvent e) {
		
		//INITIAL CHECKS (IS THIS EVENT ELIGIBLE FOR CONSIDERATION?)
		
		if (!(e.getEntity() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getEntity(), Perk.CLIMBINGGEAR)) return;
		if (e.getCause() != DamageCause.FALL) return;
		
		//INITIALIZE TOOLS
		
		Player p = (Player) e.getEntity();
		int level = SkillManager.getLevel(p, type);

		//SECONDARY CHECKS (IS THIS EVENT VALID FOR MANIPULATION?)
		
		if (e.getDamage() <= 0) return;
		
		if (p.getGameMode().equals(GameMode.CREATIVE)) return;

		//ROLLS (ROLL FOR CHANCE FOR PERK/MODIFIERS TO TAKE EFFECT)
		
		double chance = (level*5);

		if (getRandom(100) > chance) return;
		
		//DO THE THING

		{
			double multiplier = 1.0/(level/5.0);
			double initialDamage = e.getDamage();
			e.setDamage(initialDamage * multiplier);
			if (p.isSneaking()) e.setCancelled(true);
		}
		
		//DONE!
		
	}
	@EventHandler
	public void thermalInsulation(EntityDamageEvent e) {
		
		//INITIAL CHECKS (IS THIS EVENT ELIGIBLE FOR CONSIDERATION?)
		
		if (!(e.getEntity() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getEntity(), Perk.THERMALINSULATION)) return;
		if (e.getCause() != DamageCause.LAVA) return;
		
		//INITIALIZE TOOLS
		
		Player p = (Player) e.getEntity();
		int level = SkillManager.getLevel(p, type);

		//SECONDARY CHECKS (IS THIS EVENT VALID FOR MANIPULATION?)
		
		if (e.getDamage() <= 0) return;
		if (p.getGameMode().equals(GameMode.CREATIVE)) return;

		//ROLLS (ROLL FOR CHANCE FOR PERK/MODIFIERS TO TAKE EFFECT)
		
		double chance = (level*5);

		if (getRandom(100) > chance) return;
		
		//DO THE THING

		{
			double multiplier = 1.0/(level/15.0);
			double initialDamage = e.getDamage();
			e.setDamage(initialDamage * multiplier);
		}
		
		//DONE!
		
	}
	public static BukkitRunnable getCaveVisionTask() {
		return new BukkitRunnable () {

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (SkillManager.activePerk(p, Perk.CAVEVISION)) {
						pollCaveVision(p);
					}
				}
			}
			
		};
	}
	public static void pollCaveVision(Player p) {
//		int radius = SkillManager.getLevel(p, SkillType.CAVEMAN);
		Location loc = p.getLocation().add(0, 1, 0);
		
		if (loc.getBlock().getType() == Material.AIR ||
			loc.getBlock().getType() == Material.STRUCTURE_VOID) {
			p.sendBlockChange(loc, Bukkit.createBlockData(Material.LIGHT));
			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, blockDataTimer(p, loc));
		}
//		loc.add(radius, 0, 0);
//		if (loc.getBlock().getType() == Material.AIR) {
//			p.sendBlockChange(loc, Bukkit.createBlockData(Material.LIGHT));
//			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, blockDataTimer(p, loc));
//		}
//		loc.add(radius, 0, radius);
//		if (loc.getBlock().getType() == Material.AIR) {
//			p.sendBlockChange(loc, Bukkit.createBlockData(Material.LIGHT));
//			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, blockDataTimer(p, loc));
//		}
//		loc.add(-radius, 0, 0);
//		if (loc.getBlock().getType() == Material.AIR) {
//			p.sendBlockChange(loc, Bukkit.createBlockData(Material.LIGHT));
//			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, blockDataTimer(p, loc));
//		}
//		loc.add(-radius, 0, -radius);
//		if (loc.getBlock().getType() == Material.AIR) {
//			p.sendBlockChange(loc, Bukkit.createBlockData(Material.LIGHT));
//			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, blockDataTimer(p, loc));
//		}

	}
	public static Runnable blockDataTimer(Player p, Location loc) {
		return new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (loc.getBlockX() != p.getLocation().getBlockX() &&
					loc.getBlockY() != p.getLocation().getBlockY() &&
					loc.getBlockZ() != p.getLocation().getBlockZ()) {
					p.sendBlockChange(loc, Bukkit.createBlockData(Material.AIR));				
				} else {
					Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, blockDataTimer(p, loc));
				}
			}
		
		};
	}
}
