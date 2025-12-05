package net.jahcraft.jahskills.effects;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import net.jahcraft.jahskills.crafting.FamilyRecipes;
import net.jahcraft.jahskills.crafting.RecipeUtil;
import net.jahcraft.jahskills.crafting.SalvageOperations;
import net.jahcraft.jahskills.crafting.TheyFlyNow;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.IndustrialRevolution;
import net.md_5.bungee.api.ChatColor;

public class IntellectualEffects implements Listener {

	static SkillType type = SkillType.INTELLECTUAL;

	//private int getRandom(int i) { return (int) (Math.random() * (i+1)); }
	void debugMSG(String s) { Bukkit.broadcastMessage(s); }
	static boolean mainSkill(Player p) { return SkillManager.getMainSkill(p) == type; }
	private int getRandom(int i) { return (int) (Math.random() * (i+1)); }

	@EventHandler
	public void xpRebates(EnchantItemEvent e) {
		if (e.getEnchanter() == null) return;
		if (!SkillManager.activePerk(e.getEnchanter(), Perk.XPREBATES)) return;
		Player p = e.getEnchanter();
		int level = SkillManager.getLevel(p, type);
		
		int barrier = 5 * level;
		int roll1 = (int) (Math.random() * 101);
		if (roll1 < barrier) p.giveExpLevels(1);
		
		if (!mainSkill(p)) return;
		
		if (e.getExpLevelCost() < 2) return;
		barrier = barrier/2;
		int roll2 = (int) (Math.random() * 101);
		if (roll2 < barrier) p.giveExpLevels(1);
		
		if (e.getExpLevelCost() < 3) return;
		barrier = barrier/2;
		int roll3 = (int) (Math.random() * 101);
		if (roll3 < barrier) p.giveExpLevels(1);
	}
	
	@EventHandler
	public void recipeChecker(CraftItemEvent e) {
		
		if (e.getRecipe() == null) return;
		if (e.getInventory().getResult() == null) return;
		if (e.getWhoClicked() == null) return;
		
		NamespacedKey recipeKey = null;
		Recipe activeRecipe = e.getRecipe();
		
		if (activeRecipe instanceof ShapedRecipe) {
			recipeKey = ((ShapedRecipe)activeRecipe).getKey();
		}
		if (activeRecipe instanceof ShapelessRecipe) {
			recipeKey = ((ShapelessRecipe)activeRecipe).getKey();
		}
		
		if (!RecipeUtil.recipeKeys.contains(recipeKey)) return;
		
		Player crafter = (Player) e.getWhoClicked();
						
		if (TheyFlyNow.keys.contains(recipeKey)) {
			if (SkillManager.activePerk(crafter, Perk.THEYFLYNOW)) return;
			e.setCancelled(true);
			crafter.sendMessage(ChatColor.RED + "You do not have the required perk to craft this item! (/skills)");
			return;
		}
		
		if (FamilyRecipes.keys.contains(recipeKey)) {
			if (SkillManager.activePerk(crafter, Perk.FAMILYRECIPES)) return;
			e.setCancelled(true);
			crafter.sendMessage(ChatColor.RED + "You do not have the required perk to craft this item! (/skills)");
			return;
		}
		
		if (SalvageOperations.keys.contains(recipeKey)) {
			if (SkillManager.activePerk(crafter, Perk.SALVAGEOPERATION)) return;
			e.setCancelled(true);
			crafter.sendMessage(ChatColor.RED + "You do not have the required perk to craft this item! (/skills)");
			return;
		}
		
	}
	
	@EventHandler
	public void industrialRevolution(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR) return;
		if (e.getItem().getType() != Material.REDSTONE) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.INDUSTRIALREVOLUTION)) return;
		
		e.getPlayer().openInventory(IndustrialRevolution.redstoneSelector);
		IndustrialRevolution.menuItems.put(e.getPlayer(), e.getItem());
	}
	
	@EventHandler
	public void navigationalSkillsSelect(InventoryClickEvent e) {
		if (!e.getInventory().equals(IndustrialRevolution.redstoneSelector)) return;
		e.setCancelled(true);
		
		IndustrialRevolution.menuItems.get(e.getWhoClicked()).setType(e.getCurrentItem().getType());
		
		e.getWhoClicked().closeInventory();
	}
	
	@EventHandler
	public void betterMender(PlayerItemMendEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.BETTERMENDER)) return;
		if (SkillManager.getMainSkill(e.getPlayer()) == SkillType.INTELLECTUAL) {
			e.setRepairAmount(e.getRepairAmount() + SkillManager.getLevel(e.getPlayer(), type)/2);
		} else {
			e.setRepairAmount(e.getRepairAmount() + SkillManager.getLevel(e.getPlayer(), type)/4);
		}
	}
	
	@EventHandler
	public void enderInfusionArrow(EntityDamageByEntityEvent e) {
		
		if (!(e.getEntity() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getEntity(), Perk.ENDERINFUSION)) return;
		if (e.getCause() != DamageCause.PROJECTILE) return;
				
		int chance = SkillManager.getLevel((Player) e.getEntity(), type) - 5;
		if (mainSkill((Player) e.getEntity())) chance += 10;
		
		if (chance < getRandom(100)) return;
		
		e.setCancelled(true);
		
		chorusTeleport((Player)e.getEntity());
		e.getEntity().getWorld().playSound(e.getEntity(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
		
	}
	
	public void chorusTeleport(LivingEntity entity) {
		for (int i = 0; i < 16; i++) { //16 attempts: +/- 8 blocks in every direction
		    Location attempt = entity.getLocation();
		    double deltaX = ThreadLocalRandom.current().nextDouble() * 16 - 8;
		    double deltaY = ThreadLocalRandom.current().nextDouble() * 16 - 8;
		    double deltaZ = ThreadLocalRandom.current().nextDouble() * 16 - 8;
		    attempt.setX(attempt.getX() + deltaX);
		    attempt.setY(Math.min(Math.max(attempt.getY() + deltaY, 0),
		    		entity.getWorld().getMaxHeight() - 1));
		    attempt.setZ(attempt.getZ() + deltaZ);
		    attempt = getSafeLocation(attempt);
		    if (attempt != null) {
		    	entity.getWorld().playSound(entity.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT,
		        SoundCategory.PLAYERS, 1, 1);
		    	entity.teleport(attempt);
		      break;
		    }
		}
	}
	
	private Location getSafeLocation(Location loc) {
	    int blockY = loc.getBlockY();
	    World world = loc.getWorld();
	    if (blockY > world.getHighestBlockYAt(loc)) {
	      blockY = world.getHighestBlockYAt(loc) + 1;
	    }
	    boolean found = false;
	    boolean hadSpace = false;
	    while (blockY > 0) {
	      Block current = world.getBlockAt(loc.getBlockX(), blockY, loc.getBlockZ());
	      if (current.isEmpty() && current.getRelative(BlockFace.UP).isEmpty()) {
	        hadSpace = true;
	      } else if (hadSpace) {
	        if (current.getType().isSolid()) {
	          found = true;
	          blockY++;
	          break;
	        } else {
	          hadSpace = false;
	        }
	      }
	      blockY--;
	    }
	    if (Math.abs(blockY - loc.getBlockY()) > 10) return null;
	    if (found) {
	      loc.setY(blockY);
	      loc.setX(loc.getBlockX() + 0.5);
	      //TODO: Do a proper bounding box check instead of just centering the location
	      loc.setZ(loc.getBlockZ() + 0.5);
	      return loc;
	    } else {
	      return null;
	    }
	}
	
	@EventHandler
	public void useTheForce(PlayerInteractAtEntityEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getRightClicked() == null) return;
		if (!(e.getRightClicked() instanceof LivingEntity)) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.USETHEFORCE)) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
		
		LivingEntity target = (LivingEntity) e.getRightClicked();
		if (e.getPlayer().isSneaking()) {
			target.setVelocity(e.getPlayer().getLocation().getDirection().multiply(-.75));
		} else {
			target.setVelocity(e.getPlayer().getLocation().getDirection());
		}
	}
	
}
