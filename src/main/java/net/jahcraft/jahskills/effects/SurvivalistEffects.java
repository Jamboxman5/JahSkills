package net.jahcraft.jahskills.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.jahcraft.jahskills.crafting.EMTCertified;
import net.jahcraft.jahskills.crafting.RecipeUtil;
import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class SurvivalistEffects implements Listener {

	static SkillType type = SkillType.SURVIVALIST;

	public static HashMap<Zombie, LivingEntity> clones = new HashMap<>();
	public static HashMap<Player, Long> firstAidCooldowns = new HashMap<>();
	
	private int getRandom(int i) { return (int) (Math.random() * (i+1)); }
	void debugMSG(String s) { Bukkit.broadcastMessage(s); }
	static boolean mainSkill(Player p) { return SkillManager.getMainSkill(p) == type; }
	
	@EventHandler
	public void youMissedMe(EntityDamageByEntityEvent e) {
				
		if (!(e.getEntity() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getEntity(), Perk.YOUMISSEDME)) return;
				
		int chance = SkillManager.getLevel((Player) e.getEntity(), type);
		if (mainSkill((Player) e.getEntity())) chance += 13;
		
		if (chance < getRandom(100)) return;
		
		e.setCancelled(true);
		
		TextComponent dodger = new TextComponent("You dodged their attack!");
		TextComponent attacker = new TextComponent("They dodged your attack!");
		attacker.setColor(ChatColor.RED);
		
		((Player)e.getEntity()).spigot().sendMessage(ChatMessageType.ACTION_BAR, dodger);
		
		if (e.getDamager() instanceof Player) {
			((Player)e.getDamager()).spigot().sendMessage(ChatMessageType.ACTION_BAR, attacker);
		}
		
	}
	
	@EventHandler
	public void consumeFirstAid(PlayerInteractEvent e) {
		if (e.getItem() == null) return;
		if (e.getItem().getType() != Material.GHAST_TEAR) return;
		if (!e.getItem().getItemMeta().hasCustomModelData()) return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR) return;
		if (!e.getPlayer().isSneaking()) return;
		
		if (e.getPlayer().getHealth() == e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) return;
		
		if (firstAidCooldowns.containsKey(e.getPlayer())) {
			long curTime = System.currentTimeMillis();
			long then = firstAidCooldowns.get(e.getPlayer());
			if (curTime - then < 30000) {
				int secs = (int) (30-((curTime - then)/1000));
				e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("You must wait " + secs + " to heal again!"));
				return;
			}
		}
		
		firstAidCooldowns.put(e.getPlayer(), System.currentTimeMillis());
		e.getItem().setAmount(e.getItem().getAmount()-1);
		e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
		e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EAT, 1f, 1f);
		Location local = e.getPlayer().getLocation();
		e.getPlayer().getWorld().spawnParticle(Particle.HEART, local.getX(), local.getY()+1, local.getZ(), 10, .3, .3, .3);		
		for (int i = 0; i < 8; i++) {
			if (e.getPlayer().getHealth() +.5 <= e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) e.getPlayer().setHealth(e.getPlayer().getHealth() + .5);
		}
		if (!mainSkill(e.getPlayer())) return;
		for (int i = 0; i < 8; i++) {
			if (e.getPlayer().getHealth() +.5 <= e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) e.getPlayer().setHealth(e.getPlayer().getHealth() + .5);
		}
	
	}
	
	@EventHandler
	public void gordonRamsay(PlayerItemConsumeEvent e) {
		if (e.getItem().getType() == Material.POTION) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.GORDONRAMSAY)) return;
		e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel()+1);
		if (!mainSkill(e.getPlayer())) return;
		e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel()+1);

	}
	
	@EventHandler
	public void superSaiyan(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		if (!SkillManager.activePerk((Player)e.getDamager(), Perk.SUPERSAIYAN)) return;
		Player p = (Player) e.getDamager();
		if (p.getHealth() > 4) return;
		
		if (mainSkill(p)) {
			e.setDamage(e.getDamage()*1.75);
		} else {
			e.setDamage(e.getDamage()*1.5);
		}
	}
	
	@EventHandler
	public void superFoods(PlayerItemConsumeEvent e) {
		if (e.getItem().getType() != Material.RABBIT_STEW &&
			e.getItem().getType() != Material.MUSHROOM_STEW &&
			e.getItem().getType() != Material.GOLDEN_CARROT &&
			e.getItem().getType() != Material.PUMPKIN_PIE) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.SUPERFOODS)) return;
		if (e.getPlayer().getHealth() == e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) return;

		for (int i = 0; i < 3; i++) {
			if (e.getPlayer().getHealth() +.5 <= e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) e.getPlayer().setHealth(e.getPlayer().getHealth() + .5);
		}
		if (!mainSkill(e.getPlayer())) return;
		for (int i = 0; i < 6; i++) {
			if (e.getPlayer().getHealth() +.5 <= e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) e.getPlayer().setHealth(e.getPlayer().getHealth() + .5);
		}

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
						
		if (EMTCertified.keys.contains(recipeKey)) {
			if (SkillManager.activePerk(crafter, Perk.EMTCERTIFIED)) return;
			e.setCancelled(true);
			crafter.sendMessage(ChatColor.RED + "You do not have the required perk to craft this item! (/skills)");
			return;
		}
		
	}
	
	@EventHandler
	public void cloneTargetManager(EntityTargetEvent e) {
		if (!clones.containsKey(e.getEntity())) return;
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void cloneTargetDeath(EntityDeathEvent e) {
		if (!clones.values().contains(e.getEntity())) return;
		for (Zombie z : clones.keySet()) {
			if (clones.get(z).isDead()) {
 				Location local = z.getLocation();
 				z.getWorld().spawnParticle(Particle.PORTAL, local.getX(), local.getY()+1, local.getZ(), 25, .5, .5, .5);
 				z.getWorld().playSound(local, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 1f);
				z.remove();
			}
		}
	}
	
	public static void clearClones() {
		for (Zombie z : clones.keySet()) {
			try {
				z.remove();
			} catch (Exception e) {
				
			}
		}
	}
	
	@EventHandler
	public void attackOfTheClones(EntityDamageByEntityEvent e) {
		
 		if (e.getEntity() instanceof Player) {
 			if (!(e.getDamager() instanceof LivingEntity)) return;
 			if (clones.containsKey(e.getDamager())) return;
 	 		if (!SkillManager.activePerk((Player) e.getEntity(), Perk.ATTACKOFTHECLONES)) return;
 	 		
 	 		int chance = SkillManager.getLevel((Player) e.getEntity(), type) / 5;
 			if (mainSkill((Player) e.getEntity())) chance *= 2;
 			
 			if (chance < getRandom(100)) return;
 	 		
 	 		Player p = (Player) e.getEntity();
 	 		
 			if (p.getHealth() - e.getDamage() < 0) return;
 			
 			List<Zombie> theseClones = new ArrayList<>();
 			
 			for (int i = 0; i < 4; i++) {
 				Zombie clone = (Zombie) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.ZOMBIE);
 			
 				ItemStack head = new ItemStack(Material.PLAYER_HEAD);
 				SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);

 				meta.setOwningPlayer(p);
 				head.setItemMeta(meta);
 				clone.getEquipment().setHelmet(head);

 				clone.getEquipment().setChestplate(p.getEquipment().getChestplate());
 				clone.getEquipment().setLeggings(p.getEquipment().getLeggings());
 				clone.getEquipment().setBoots(p.getEquipment().getBoots());
 				clone.getEquipment().setItemInMainHand(p.getEquipment().getItemInMainHand());
 				clone.setTarget((LivingEntity) e.getDamager());
 				clone.getEquipment().setBootsDropChance(0f);
 				clone.getEquipment().setHelmetDropChance(0f);
 				clone.getEquipment().setChestplateDropChance(0f);
 				clone.getEquipment().setLeggingsDropChance(0f);
 				clone.getEquipment().setItemInMainHandDropChance(0f);
 				
 				theseClones.add(clone);
 				clones.put(clone, (LivingEntity) e.getDamager());
 				chorusTeleport(clone);
 				Location local = clone.getLocation();
 				clone.getWorld().playSound(local, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 1f);
 				clone.getWorld().spawnParticle(Particle.PORTAL, local.getX(), local.getY()+1, local.getZ(), 25, .5, .5, .5);
 			}
 			
 			new BukkitRunnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(7000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (Zombie z : theseClones) {
						new BukkitRunnable() {

							@Override
							public void run() {
								z.remove();
								clones.remove(z);								
							}
							
						}.runTask(Main.plugin);
						
					}
					
				}
 				
 			}.runTaskAsynchronously(Main.plugin);
 		}
 		
 		if (e.getEntity() instanceof Zombie) {
 			if (!clones.containsKey(e.getEntity())) return;
 			Location local = e.getEntity().getLocation();
 			e.getEntity().getWorld().spawnParticle(Particle.PORTAL, local.getX(), local.getY()+1, local.getZ(), 25, .5, .5, .5);
			e.getEntity().getWorld().playSound(local, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 1f);
			e.getEntity().remove();
 		}
 		

	}
	
	@EventHandler
	public void resurrection(EntityDamageEvent e) {
		
		if (!(e.getEntity() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getEntity(), Perk.RESURRECTION)) return;
		
		Player p = (Player) e.getEntity();
				
		if (p.getHealth() - e.getDamage() > 0) return;
				
		int chance = SkillManager.getLevel((Player) e.getEntity(), type) - 10;
		if (mainSkill((Player) e.getEntity())) chance += 10;
		
		if (chance < getRandom(100)) return;
		
		ItemStack offHand = p.getInventory().getItemInOffHand();
		p.getInventory().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
		
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				p.getInventory().setItemInOffHand(offHand);
			}
			
		}.runTaskAsynchronously(Main.plugin);
		
	}
	
	@EventHandler
	public void artfulDodger(EntityDamageByEntityEvent e) {
		
		if (!(e.getEntity() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getEntity(), Perk.ARTFULDODGER)) return;
				
		int chance = SkillManager.getLevel((Player) e.getEntity(), type) - 5;
		if (mainSkill((Player) e.getEntity())) chance += 10;
		
		if (chance < getRandom(100)) return;
		
		e.setCancelled(true);
		
		TextComponent dodger = new TextComponent("You dodged their attack!");
		TextComponent attacker = new TextComponent("They dodged your attack!");
		attacker.setColor(ChatColor.RED);
		
		((Player)e.getEntity()).spigot().sendMessage(ChatMessageType.ACTION_BAR, dodger);
		
		if (e.getDamager() instanceof Player) {
			((Player)e.getDamager()).spigot().sendMessage(ChatMessageType.ACTION_BAR, attacker);
		}
		
		chorusTeleport((Player)e.getEntity());
		
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
	
}
