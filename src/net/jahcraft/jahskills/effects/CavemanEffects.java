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
import org.bukkit.inventory.ItemStack;

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
	
}
