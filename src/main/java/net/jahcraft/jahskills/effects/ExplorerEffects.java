package net.jahcraft.jahskills.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StructureSearchResult;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.BiomeFinder;

public class ExplorerEffects implements Listener {

static SkillType type = SkillType.EXPLORER;

	public static List<Horse> wildHorses = new ArrayList<>();
	public static List<Horse> boostedHorses = new ArrayList<>();
	public static HashMap<Villager, List<MerchantRecipe>> moddedVillagers = new HashMap<>();
	public static HashMap<Player, Integer> selectedTradeCost = new HashMap<>();
	
	private int getRandom(int i) { return (int) (Math.random() * (i+1)); }
	void debugMSG(String s) { Bukkit.broadcastMessage(s); }
	static boolean mainSkill(Player p) { return SkillManager.getMainSkill(p) == type; }
	
	@EventHandler
	public void navigationalSkillsActivate(PlayerInteractEvent e) {
	
		if (e.getAction() != Action.RIGHT_CLICK_AIR) return;
		if (e.getItem().getType() != Material.COMPASS) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.NAVIGATIONALSKILLS)) return;
		
		e.getPlayer().openInventory(BiomeFinder.biomeMenu);
		
//		BiomeFinder finder = new BiomeFinder(e.getPlayer(), Biome.BADLANDS);
//		finder.search();
		
	}
	
	@EventHandler
	public void navigationalSkillsSelect(InventoryClickEvent e) {
		if (!e.getInventory().equals(BiomeFinder.biomeMenu)) return;
		e.setCancelled(true);
		
		switch(e.getSlot()) {
		case 0:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.FOREST).search();
			break;
		case 1:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.PLAINS).search();
			break;
		case 2:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.BADLANDS).search();
			break;
		case 3:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.DESERT).search();
			break;
		case 4:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.OCEAN).search();
			break;
		case 5:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.TAIGA).search();
			break;
		case 6:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.SWAMP).search();
			break;
		case 7:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.JAGGED_PEAKS).search();
			break;
		case 8:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.CHERRY_GROVE).search();
			break;
		case 9:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.SAVANNA).search();
			break;
		case 10:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.DEEP_FROZEN_OCEAN).search();
			break;
		case 11:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.JUNGLE).search();
			break;
		case 12:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.BEACH).search();
			break;
		case 13:
			new BiomeFinder((Player) e.getWhoClicked(), Biome.RIVER).search();;
			break;
//		case 14:
//			new BiomeFinder((Player) e.getWhoClicked(), Biome.FOREST);
//			break;
			
		}
		
		e.getWhoClicked().closeInventory();
		
	}
	
	@EventHandler
	public void luckyLooter(LootGenerateEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getEntity(), Perk.LUCKYLOOTER)) return;
		
		List<ItemStack> contents = new ArrayList<>(e.getLoot());
		
		for (ItemStack i : contents) {
//			if (i.getAmount()*2 <= i.getMaxStackSize()) {
				if (mainSkill((Player) e.getEntity())) {
					i.setAmount(i.getAmount()*3);
				} else {
					i.setAmount(i.getAmount()*2);
				}
//			}
		}
		
		e.setLoot(contents);
						
	}
	
	
	
	@EventHandler
	public void highDemand(InventoryClickEvent e) {
		
		if (!(e.getInventory() instanceof MerchantInventory)) return;
		if (e.getSlot() != 2) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
		if (e.isCancelled()) return;
		if (!SkillManager.activePerk((Player) e.getWhoClicked(), Perk.HIGHDEMAND)) return;
		
		MerchantInventory tradeMenu = (MerchantInventory) e.getClickedInventory();
		MerchantRecipe activeRecipe = tradeMenu.getSelectedRecipe();
		
		if (activeRecipe.getUses()-1 <= 0) {
			if (getRandom(4) == 1) {
				activeRecipe.setUses(activeRecipe.getUses()-1);
				debugMSG("trade count ignored");
			}
		}
		
	}
	
	@EventHandler 
	public void artofTheDealOpen(PlayerInteractAtEntityEvent e) {
		if (!(e.getRightClicked() instanceof Villager)) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.ARTOFTHEDEAL)) return;
		Villager villager = (Villager) e.getRightClicked();
		List<MerchantRecipe> reps = villager.getRecipes();
		moddedVillagers.put(villager, reps);
		if (reps.size() == 0) return;
		for (MerchantRecipe rep : reps) {
			rep.setSpecialPrice((rep.getAdjustedIngredient1().getAmount()/2)*-1);
		}
		villager.setRecipes(reps);
	}
	
	@EventHandler 
	public void artofTheDealClose(InventoryCloseEvent e) {
		if (!(e.getInventory() instanceof MerchantInventory)) return;
		if (!(e.getInventory().getHolder() instanceof Villager)) return;
		Villager villager = (Villager) e.getInventory().getHolder();
		if (!moddedVillagers.containsKey(villager)) return;
		
		villager.setRecipes(moddedVillagers.get(villager));
		moddedVillagers.remove(villager);

	}
	
	@EventHandler
	public void neptuneFlight(PlayerRiptideEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.NEPTUNEFLIGHT)) return;
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(4));
				
			}
		}.runTaskAsynchronously(Main.plugin);
		
	}
	
	@EventHandler
	public void treasureSniffer(PlayerInteractEvent e) {
		
		if (e.getAction() != Action.RIGHT_CLICK_AIR) return;
		if (!(e.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof MapMeta)) return;
		if (!(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Buried Treasure"))) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.TREASURESNIFFER)) return;
		
		StructureSearchResult structSearch = e.getPlayer().getWorld().locateNearestStructure(e.getPlayer().getLocation(), StructureType.BURIED_TREASURE, 1, false);
		
		if (structSearch == null) {
			e.getPlayer().sendMessage("No treasure detected!");
		} else {
			Location treasure = structSearch.getLocation();
			e.getPlayer().sendMessage("X: " + treasure.getBlockX() + " | Z: " + treasure.getBlockZ());
		}
	}
	
	@EventHandler
	public void kentuckyDerbyMount(EntityMountEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		if (!(e.getMount() instanceof Horse)) return;
		Player player = (Player) e.getEntity();
		if (!SkillManager.activePerk(player, Perk.KENTUCKYDERBY)) return;
		
		Horse horse = (Horse) e.getMount();
		horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * 2);
		boostedHorses.add(horse);
	}
	@EventHandler
	public void kentuckyDerbyDismount(EntityDismountEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		if (!(e.getDismounted() instanceof Horse)) return;
		
		Horse horse = (Horse) e.getDismounted();
		
		if (!boostedHorses.contains(horse)) return;
		
		horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() / 2);
		boostedHorses.remove(horse);
	
	}
	
	public static void removeHorse(Horse horse) {
		new BukkitRunnable() {

			@Override
			public void run() {
				horse.remove();	
				for (ItemStack i : horse.getInventory().getContents()) {
					if (i != null) {
						horse.getWorld().dropItemNaturally(horse.getLocation(), i);
					}
				}
				horse.getWorld().playSound(horse.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1f, 1f);
			}
			
		}.runTask(Main.plugin);
	}
	
	@EventHandler
	public void horseRemover(InventoryClickEvent e) {
		if (e.getWhoClicked() == null) return;
		if (!(e.getClickedInventory() instanceof HorseInventory)) return;
		if (e.getCurrentItem().getType() != Material.SADDLE) return;
		
		HorseInventory hi = (HorseInventory) e.getClickedInventory();
		Horse horse = (Horse) hi.getHolder();
		if (wildHorses.contains(horse)) {
			e.setCancelled(true);
			
			new BukkitRunnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(20);
						removeHorse(horse);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}.runTaskAsynchronously(Main.plugin);
			

		}
		
	}
	
	@EventHandler
	public void callOfTheWild(PlayerDropItemEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getItemDrop().getItemStack().getType() != Material.SADDLE) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.CALLOFTHEWILD)) return;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				new BukkitRunnable() {

					@Override
					public void run() {
						e.getItemDrop().remove();
						Horse spawned = (Horse) e.getItemDrop().getWorld().spawnEntity(e.getItemDrop().getLocation(), EntityType.HORSE);
						Location local = spawned.getLocation();
						spawned.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX(), local.getY()+1, local.getZ(), 20, .7, .7, .7);
						spawned.getWorld().playSound(local, Sound.BLOCK_BEACON_POWER_SELECT, 1f, 1f);
						 spawned.setColor(Color.BLACK);
						 spawned.setStyle(Style.WHITE);
						 spawned.getInventory().addItem(new ItemStack(Material.SADDLE));
						 spawned.setAI(false);	
						 spawned.setBreed(false);
						 spawned.setOwner(e.getPlayer());
						 spawned.setTamed(true);
						 wildHorses.add(spawned);
					}
					
				}.runTask(Main.plugin);
				
			}
			
		}.runTaskAsynchronously(Main.plugin);
		
		
	}
	
}
