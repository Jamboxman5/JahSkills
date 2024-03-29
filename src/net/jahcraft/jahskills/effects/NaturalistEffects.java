package net.jahcraft.jahskills.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.Colors;
import net.jahcraft.jahskills.util.Format;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class NaturalistEffects implements Listener {

	static SkillType type = SkillType.NATURALIST;
	
	List<Player> treeCapReady = new ArrayList<>();
	HashMap<Player, Long> treeCapCooldown = new HashMap<>();
	List<Player> excavReady = new ArrayList<>();
	HashMap<Player, Long> excavCooldown = new HashMap<>();
	List<Player> mmNTcooldown = new ArrayList<>();

	private int getRandom(int i) { return (int) (Math.random() * (i+1)); }
	void debugMSG(String s) { Bukkit.broadcastMessage(s); }
	static boolean mainSkill(Player p) { return SkillManager.getMainSkill(p) == type; }
	
	@EventHandler
	public void treeCapitator(PlayerInteractEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.TREECAPITATOR)) return;
		
		
		Player p = e.getPlayer();
		ItemStack tool = p.getInventory().getItemInMainHand();

		if (!tool.getType().toString().contains("_AXE")) return;
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			int cooldownseconds = (120 - (SkillManager.getLevel(p, type)*3));
			if (treeCapCooldown.containsKey(p) &&
				!mainSkill(p) &&
				(System.currentTimeMillis() - treeCapCooldown.get(p)) < (cooldownseconds*1000)) {
				if (e.getAction() == Action.RIGHT_CLICK_AIR) {
					int seconds =  (int)(cooldownseconds -((System.currentTimeMillis() - treeCapCooldown.get(p))/1000));
					Format.sendEffectCooldown(p, "TreeCapitator", seconds);
				}
				return;
			}
			if (treeCapCooldown.containsKey(p) &&
				mainSkill(p) &&
			   (System.currentTimeMillis() - treeCapCooldown.get(p)) < ((cooldownseconds/4)*1000)) {
				if (e.getAction() == Action.RIGHT_CLICK_AIR) {
					int seconds =  (int)((cooldownseconds/4)-((System.currentTimeMillis() - treeCapCooldown.get(p))/1000));
					Format.sendEffectCooldown(p, "TreeCapitator", seconds);
				}
				return;
			}
				
			if (!treeCapReady.contains(p)) {
				treeCapReady.add(p);
				Format.sendReady(p, "Axe");
				try {
					new BukkitRunnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(3000);
								if (treeCapReady.contains(p)) {
									treeCapReady.remove(p);
									Format.sendLower(p, "Axe");
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								
						}
							
					}.runTaskAsynchronously(Main.plugin);
				
				} catch(Exception e2) {
						
				}

						
			}
		}
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getClickedBlock() == null) return;
			if (!e.getClickedBlock().getType().toString().contains("WOOD") &&
				!e.getClickedBlock().getType().toString().contains("LOG")) return;
			if (!treeCapReady.contains(p)) return;
			Block start = e.getClickedBlock();
			int xp = 0;
			Material m = start.getType();
			p.sendMessage(Colors.BRIGHTBLUE + "TreeCapitated!");
			try {
				new BukkitRunnable() {

					@Override
					public void run() {
						veinMine(start, xp, m, p, tool);
					}
					
				}.runTaskAsynchronously(Main.plugin);
			} catch(Exception ex) {
				
			}
			
			treeCapReady.remove(p);
			treeCapCooldown.put(p, System.currentTimeMillis());
		}
		
		
		
	}
	@EventHandler
	public void lumberJack(BlockBreakEvent e) {
		//INITIAL CHECKS (IS THIS EVENT ELIGIBLE FOR CONSIDERATION?)
		
				if (e.getPlayer() == null) return;
				if (!SkillManager.activePerk(e.getPlayer(), Perk.LUMBERJACK)) return;
				if (!e.getBlock().getType().toString().contains("LOG") &&
					!e.getBlock().getType().toString().contains("WOOD")) return;
				if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;

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
						if (mainSkill(p)) {
							world.dropItemNaturally(location, i);
						}
					}
				}
				
				//DONE!
	}
	@EventHandler
	public void iSpeakForTheTrees(BlockBreakEvent e) {
		
		//INITIAL CHECKS (IS THIS EVENT ELIGIBLE FOR CONSIDERATION?)
		
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.ISPEAKFORTHETREES)) return;
		if (!e.getBlock().getType().toString().contains("LOG") &&
			!e.getBlock().getType().toString().contains("WOOD")) return;
		if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;

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
		if (mainSkill(p)) multiplier += 1.5;
		
		//DO THE THING

		{
			int originalPoints = e.getExpToDrop()+1;
			int newPoints = (int) (originalPoints * multiplier);
			e.setExpToDrop(newPoints);
		}
		
		//DONE!
		
	}
	@EventHandler
	public void megaExcavatorToggle(PlayerInteractEvent e) {
		//INITIAL CHECKS (IS THIS EVENT ELIGIBLE FOR CONSIDERATION?)
		
		if (e.getPlayer() == null) return;
		if (e.getItem() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.MEGAEXCAVATOR)) return;
		if (e.getAction() == Action.LEFT_CLICK_BLOCK && e.getClickedBlock() == null) return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.getPlayer().isSneaking()) return;
				
		Player p = e.getPlayer();
		ItemStack tool = e.getItem();
		
		if (!tool.getType().toString().contains("SHOVEL")) return;
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			int cooldownseconds = (180 - (SkillManager.getLevel(p, type)*3));
			if (excavCooldown.containsKey(p) &&
				!mainSkill(p) &&
				(System.currentTimeMillis() - excavCooldown.get(p)) < (cooldownseconds*1000)) {
				if (e.getAction() == Action.RIGHT_CLICK_AIR) {
					int seconds =  (int)(cooldownseconds -((System.currentTimeMillis() - excavCooldown.get(p))/1000));
					Format.sendEffectCooldown(p, "MegaExcavator", seconds);
				}
				return;
			}
			if (excavCooldown.containsKey(p) &&
				mainSkill(p) &&
			   (System.currentTimeMillis() - excavCooldown.get(p)) < ((cooldownseconds/2)*1000)) {
				if (e.getAction() == Action.RIGHT_CLICK_AIR) {
					int seconds =  (int)((cooldownseconds/2)-((System.currentTimeMillis() - excavCooldown.get(p))/1000));
					Format.sendEffectCooldown(p, "MegaExcavator", seconds);
				}
				return;
			}
				
			if (!excavReady.contains(p)) {
				excavReady.add(p);
				Format.sendReady(p, "Shovel");
				try {
					new BukkitRunnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(3000);
								if (excavReady.contains(p)) {
									excavReady.remove(p);
									Format.sendLower(p, "Shovel");
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								
						}
							
					}.runTaskAsynchronously(Main.plugin);
				
				} catch(Exception e2) {
						
				}

						
			}
		}
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (excavReady.contains(p)) {
				excavReady.remove(p);
				p.sendMessage(Colors.BRIGHTBLUE + "Mega Excavator Activated!");
				int oldLevel = tool.getEnchantmentLevel(Enchantment.DIG_SPEED);
				ItemMeta meta = tool.getItemMeta();
				meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
				tool.setItemMeta(meta);
				try {
					new BukkitRunnable() {

						@Override
						public void run() {
							try {
								if (mainSkill(p)) {
									Thread.sleep(SkillManager.getLevel(p, type) * 2000);
								} else {
									Thread.sleep(SkillManager.getLevel(p, type) * 1000);
								}
								ItemMeta meta = tool.getItemMeta();
								if (oldLevel > 0) {
									meta.addEnchant(Enchantment.DIG_SPEED, oldLevel, true);
								} else {
									meta.removeEnchant(Enchantment.DIG_SPEED);
								}
								tool.setItemMeta(meta);								
								p.sendMessage(Colors.BRIGHTBLUE + "Mega Excavator Finished.");
								excavCooldown.put(p, System.currentTimeMillis());
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					}.runTaskAsynchronously(Main.plugin);
				} catch (Exception e2) {
					
				}
			}
		}
		
	}
	@EventHandler
	public void biggerShovel(BlockBreakEvent e) {

		
		//INITIAL CHECKS (IS THIS EVENT ELIGIBLE FOR CONSIDERATION?)
		
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.BIGGERSHOVEL)) return;
		if (!e.getBlock().getType().equals(Material.DIRT) &&
			!e.getBlock().getType().equals(Material.SAND) &&
			!e.getBlock().getType().equals(Material.GRASS_BLOCK) &&
			!e.getBlock().getType().equals(Material.PODZOL) &&
			!e.getBlock().getType().equals(Material.MYCELIUM) &&
			!e.getBlock().getType().equals(Material.COARSE_DIRT) &&
			!e.getBlock().getType().equals(Material.RED_SAND) &&
			!e.getBlock().getType().equals(Material.SOUL_SAND) &&
			!e.getBlock().getType().equals(Material.GRAVEL) &&
			!e.getBlock().getType().equals(Material.DIRT_PATH) &&
			!e.getBlock().getType().equals(Material.ROOTED_DIRT) &&
			!e.getBlock().getType().equals(Material.FARMLAND) &&
			!e.getBlock().getType().equals(Material.MUD)) return;
		if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;

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
		if (!tool.getType().toString().contains("SHOVEL")) return;

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
				if (mainSkill(p)) {
					world.dropItemNaturally(location, i);
				}
			}
		}
		
		//DONE!
		
	}
	public static BukkitRunnable getHippyHealingTask() {
		return new BukkitRunnable() {

			@Override
			public void run() {

				try {
					
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (canHippyHeal(p)) {
							try {
								double healFactor = (20.0 + (20.0 - SkillManager.getLevel(p, type)));
								double healthToAdd = (p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()/healFactor);
								if (SkillManager.getMainSkill(p) == type) healthToAdd *= 2.0;
								p.setHealth(p.getHealth() + healthToAdd);
								TextComponent healMSG = new TextComponent("You're Hippy Healing!");
								healMSG.setColor(ChatColor.GREEN);
								p.spigot().sendMessage(ChatMessageType.ACTION_BAR, healMSG);
							} catch (IllegalArgumentException e) {
								p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
							}
//							p.sendMessage("Hippy healed.");
						} else {
//							Location loc = p.getLocation().subtract(0,+1,0);
//							p.sendMessage(loc.getBlock().getType().toString());
						}
					}
										
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		};
	}
	@EventHandler
	public void natureTouch(PlayerInteractEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getItem() != null) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.NATURESTOUCH)) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!e.getPlayer().isSneaking()) return;
		if (e.getClickedBlock() == null) return;
		if (e.getClickedBlock().getType() == Material.AIR) return;
		if (mmNTcooldown.contains(e.getPlayer())) return;

		Block b = e.getClickedBlock();
		Material clickedBlockType = e.getClickedBlock().getType();
		
		switch(clickedBlockType) {
		case COARSE_DIRT: {
			b.setType(Material.DIRT);
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			startMMNTCooldown(e.getPlayer());
			break;
		}
		case DIRT: {
			b.setType(Material.ROOTED_DIRT);
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			startMMNTCooldown(e.getPlayer());
			break;
		}
		case ROOTED_DIRT: {
			b.setType(Material.GRASS_BLOCK);
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			startMMNTCooldown(e.getPlayer());
			break;
		}
		case GRASS_BLOCK: {
			
			if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
				b.getRelative(BlockFace.UP).setType(Material.GRASS, false);
			} else {
				b.setType(Material.MOSS_BLOCK);
			}
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			startMMNTCooldown(e.getPlayer());
			break;
		}
		case DEAD_BUSH: {
			b.setType(Material.FERN);
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			startMMNTCooldown(e.getPlayer());
			break;
		}
		case MOSS_BLOCK: {
			
			if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
				Location local = e.getClickedBlock().getLocation();
				e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
				b.getRelative(BlockFace.UP).setType(Material.MOSS_CARPET, false);
			} 
			startMMNTCooldown(e.getPlayer());
			break;
		}
		case GRASS: {
			if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
				Bisected data = (Bisected) Bukkit.createBlockData(Material.TALL_GRASS);
				data.setHalf(Half.BOTTOM);
				b.setBlockData(data);
				data.setHalf(Half.TOP);
				b.getRelative(BlockFace.UP).setBlockData(data);
				Location local = e.getClickedBlock().getLocation();
				e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			} else {
				Location local = e.getClickedBlock().getLocation();
				e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
				b.setType(Material.MOSS_CARPET);
			}
			break;
		}
		case FERN: {
			if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
				Bisected data = (Bisected) Bukkit.createBlockData(Material.LARGE_FERN);
				data.setHalf(Half.BOTTOM);
				b.setBlockData(data);
				data.setHalf(Half.TOP);
				b.getRelative(BlockFace.UP).setBlockData(data);
				Location local = e.getClickedBlock().getLocation();
				e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			} else {
				Location local = e.getClickedBlock().getLocation();
				e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
				b.setType(Material.MOSS_CARPET);
			}
			break;
		}
		case AZALEA: {
			b.setType(Material.FLOWERING_AZALEA);
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			startMMNTCooldown(e.getPlayer());
			break;
		}
		case AZALEA_LEAVES: {
			b.setType(Material.FLOWERING_AZALEA_LEAVES);
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			startMMNTCooldown(e.getPlayer());
			break;
		}
		default:
			break;
		
		}
		
		if (SkillManager.getMainSkill(e.getPlayer()) != type) return;
		
		if (e.getClickedBlock().getBlockData() instanceof Sapling) {
			try {
				Material original = e.getClickedBlock().getType();
				if (original.toString().contains("MUSHROOM")) return;
				TreeType tree = getTreeType(e.getClickedBlock());
				Location loc = e.getClickedBlock().getLocation();
				e.getClickedBlock().setType(Material.AIR);
				if (!e.getClickedBlock().getWorld().generateTree(loc, tree)) {
					e.getClickedBlock().setType(original);
					e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("You can't grow a tree there!"));
				} else {
					Location local = e.getClickedBlock().getLocation();
					e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (e.getClickedBlock().getBlockData() instanceof Ageable) {
			Ageable data = (Ageable) e.getClickedBlock().getBlockData();
			if (data.getAge() < data.getMaximumAge()) {
				data.setAge(data.getAge()+1);
				e.getClickedBlock().setBlockData(data);
				Location local = e.getClickedBlock().getLocation();
				e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 5, .3, .3, .3);
			}
		}
		
	}
	@EventHandler
	public void mushroomMan(PlayerInteractEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getItem() != null) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.MUSHROOMMAN)) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!e.getPlayer().isSneaking()) return;
		if (e.getClickedBlock() == null) return;
		if (e.getClickedBlock().getType() == Material.AIR) return;
		if (mmNTcooldown.contains(e.getPlayer())) return;
		
		Block b = e.getClickedBlock();
		Material clickedBlockType = e.getClickedBlock().getType();
		
		switch(clickedBlockType) {
		case COARSE_DIRT: {
			b.setType(Material.DIRT);
			startMMNTCooldown(e.getPlayer());
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			break;
		}
		case DIRT: {
			b.setType(Material.ROOTED_DIRT);
			startMMNTCooldown(e.getPlayer());
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			break;
		}
		case ROOTED_DIRT: {
			b.setType(Material.MYCELIUM);
			startMMNTCooldown(e.getPlayer());
			Location local = e.getClickedBlock().getLocation();
			e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
			break;
		}
		case MYCELIUM: {
			
			if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
				int random = (int) (Math.random()*101);
				if (random > 50) {
					b.getRelative(BlockFace.UP).setType(Material.RED_MUSHROOM, false);
				} else {
					b.getRelative(BlockFace.UP).setType(Material.BROWN_MUSHROOM, false);
				}
				Location local = e.getClickedBlock().getLocation();
				e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
				startMMNTCooldown(e.getPlayer());
			}
			break;
		}
//		case GRASS: {
//			if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
//				b.setBlockData(null);
//			} else {
//				b.setType(Material.MOSS_CARPET);
//			}
//			break;
//		}
		
		default:
			break;
		
		}
		if (SkillManager.getMainSkill(e.getPlayer()) != type) return;

		if (e.getClickedBlock().getType() == Material.RED_MUSHROOM ||
			e.getClickedBlock().getType() == Material.BROWN_MUSHROOM) {
			try {
				Material original = e.getClickedBlock().getType();
				if (!original.toString().contains("MUSHROOM")) return;
				TreeType tree = getTreeType(e.getClickedBlock());
				Location loc = e.getClickedBlock().getLocation();
				e.getClickedBlock().setType(Material.AIR);
				if (!e.getClickedBlock().getWorld().generateTree(loc, tree)) {
					e.getClickedBlock().setType(original);
					e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("You can't grow a mushroom there!"));
				} else {
					Location local = e.getClickedBlock().getLocation();
					e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	@EventHandler
	public void manVsWild(PlayerInteractEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
		if (e.getItem() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.MANVSWILD)) return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR) return;
		
		ItemStack item = e.getItem();
		Material type = item.getType();
		
		if (!mvwEatables().contains(type)) return;
		
		if (e.getPlayer().getFoodLevel() < 20) {
			item.setAmount(item.getAmount() - 1);
			e.getPlayer().getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EAT, 1f, 1f);
			e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + 1);
		}
		
		
	}
	
	private static boolean canHippyHeal(Player p) {
		boolean notCreative = p.getGameMode() != GameMode.CREATIVE;
		boolean activePerk = SkillManager.activePerk(p, Perk.HIPPYHEALING);
		boolean onGrass = p.getLocation().subtract(0,+1,0).getBlock().getType() == Material.GRASS_BLOCK;
		boolean bareFoot = p.getInventory().getBoots() == null;
		boolean canHeal = p.getHealth() < p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
//		p.sendMessage("notcreative: " + notCreative);
//		p.sendMessage("activePerk: " + activePerk);
//		p.sendMessage("onGrass: " + onGrass);
//		p.sendMessage("bareFoot: " + bareFoot);
//		p.sendMessage("canHeal: " + canHeal);
		return (notCreative && activePerk && onGrass && bareFoot && canHeal);
	}
	private TreeType getTreeType(Block sapling) {
		switch(sapling.getType()) {
		default:
			return null;
		case SPRUCE_SAPLING:
			if (Math.random() > 0.5) {
				return TreeType.REDWOOD;
			} else {
				return TreeType.TALL_REDWOOD;
			}
		case OAK_SAPLING:
			if (Math.random() > 0.2) {
				return TreeType.TREE;
			} else {
				return TreeType.BIG_TREE;
			}
		case BIRCH_SAPLING:
			if (Math.random() > 0.2) {
				return TreeType.BIRCH;
			} else {
				return TreeType.TALL_BIRCH;
			}
		case DARK_OAK_SAPLING:
			return TreeType.DARK_OAK;
		case ACACIA_SAPLING:
			return TreeType.ACACIA;
		case JUNGLE_SAPLING:
			return TreeType.SMALL_JUNGLE;
		case MANGROVE_PROPAGULE:
			if (Math.random() > 0.2) {
				return TreeType.MANGROVE;
			} else {
				return TreeType.TALL_MANGROVE;
			}			
		case CHERRY_SAPLING:
			return TreeType.CHERRY;		
		case BROWN_MUSHROOM:
			return TreeType.BROWN_MUSHROOM;		
		case RED_MUSHROOM:
			return TreeType.RED_MUSHROOM;	
		}
		
		
	}
	private void startMMNTCooldown(Player p) {
		mmNTcooldown.add(p);
		try {
			new BukkitRunnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(200);
						if (mmNTcooldown.contains(p)) {
							mmNTcooldown.remove(p);
						}
					} catch (Exception e) {
						
					}
					
				}
			}.runTaskAsynchronously(Main.plugin);
		} catch (Exception e) {
			
		}
	}
	private void veinMine(Block b, int xp, Material m, Player p, ItemStack tool) {
		
		if (b.getType() == m && SkillDatabase.isNatural(b.getLocation())) {
			
			Damageable meta = (Damageable) tool.getItemMeta();
			meta.setDamage(meta.getDamage()+1);
			boolean isBroken = (meta.getDamage() >= tool.getType().getMaxDurability());
			
			new BukkitRunnable() {
				@Override
				public void run() {
					b.breakNaturally(tool);
					p.giveExp(xp);
					tool.setItemMeta(meta);
					if (isBroken) {
						p.getInventory().remove(tool);
						p.playSound(p, Sound.ENTITY_ITEM_BREAK, 1, 1);
						return;
					}
				}
			}.runTask(Main.plugin);
			
			if (b.getRelative(BlockFace.UP).getType() == m) veinMine(b.getRelative(BlockFace.UP), xp, m, p, tool);
			if (b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType() == m) veinMine(b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH), xp, m, p, tool);
			if (b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType() == m) veinMine(b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH), xp, m, p, tool);
			if (b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType() == m) veinMine(b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST), xp, m, p, tool);
			if (b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType() == m) veinMine(b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST), xp, m, p, tool);
//			if (b.getRelative(BlockFace.DOWN).getType() == m) veinMine(b.getRelative(BlockFace.DOWN), xp, m, p, tool);
			if (b.getRelative(BlockFace.NORTH).getType() == m) veinMine(b.getRelative(BlockFace.NORTH), xp, m, p, tool);
			if (b.getRelative(BlockFace.SOUTH).getType() == m) veinMine(b.getRelative(BlockFace.SOUTH), xp, m, p, tool);
			if (b.getRelative(BlockFace.EAST).getType() == m) veinMine(b.getRelative(BlockFace.EAST), xp, m, p, tool);
			if (b.getRelative(BlockFace.WEST).getType() == m) veinMine(b.getRelative(BlockFace.WEST), xp, m, p, tool);
		}
		
		
	
	}
	private List<Material> mvwEatables() {
		ArrayList<Material> materials = new ArrayList<>();
		materials.add(Material.GRASS);
		materials.add(Material.SUGAR_CANE);
		materials.add(Material.SUGAR);
		materials.add(Material.TURTLE_EGG);
		materials.add(Material.SEA_PICKLE);
		materials.add(Material.KELP);
		materials.add(Material.FERN);
		materials.add(Material.RED_MUSHROOM);
		materials.add(Material.BROWN_MUSHROOM);
		materials.add(Material.WARPED_FUNGUS);
		materials.add(Material.CRIMSON_FUNGUS);
		materials.add(Material.WHEAT_SEEDS);
		materials.add(Material.PUMPKIN_SEEDS);
		materials.add(Material.MELON_SEEDS);
		materials.add(Material.BEETROOT_SEEDS);
		materials.add(Material.COCOA_BEANS);
		materials.add(Material.NETHER_WART);
		materials.add(Material.GLISTERING_MELON_SLICE);
		materials.add(Material.WHEAT);
		return materials;
	}
}
