package net.jahcraft.jahskills.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.jahcraft.jahskills.main.Main;

public class BiomeFinder {
	
	private boolean biomeFound;

	public static Inventory biomeMenu;
	
	private Player p;
	private Location start;
	private Location found = null;
	private Biome finding;
	
	List<BukkitRunnable> activeTasks = new ArrayList<>();
	BlockFace[] directions = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, 
							  BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST};
	
	public BiomeFinder(Player player, Biome target) {
		biomeFound = false;
		p = player;
		start = player.getLocation();
		finding = target;
	}
	
	public void search() {
		
		p.sendMessage("Searching for " + finding.toString() + "...");
		
		if (start.getWorld().getBiome(start).toString().contains(finding.toString())) {
			p.sendMessage("You are already in that biome!");
			return;
		}
		
		for (BlockFace direction : directions) {
			BukkitRunnable task = getBiomeSearchTask(direction);
			task.runTaskAsynchronously(Main.plugin);
			activeTasks.add(task);
		}
		
		new BukkitRunnable() {

			@Override
			public void run() {
				
				int pollCount = 0;
				
				while (found == null) {
					pollCount += 1;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (pollCount >= 10) break;
				}
				
				cancelTasks();
				
				if (found != null) {
					p.sendMessage("Biome found at X: " + found.getBlockX() + " Z: " + found.getBlockZ());
				} else {
					p.sendMessage("Biome could not be located within reasonable distance!");
				}
				
			}
			
		}.runTaskAsynchronously(Main.plugin);
				
	}
	
	private void cancelTasks() {
		for (BukkitRunnable task : activeTasks) {
			try {
				task.cancel();
			} catch(IllegalStateException e) {
				
			}
		}
	}
	
	public static void initBiomeMenu() {
		biomeMenu = Bukkit.createInventory(null, 18, "Select A Biome:");
		
		ItemStack item = new ItemStack(Material.OAK_SAPLING);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Colors.format("&rForest"));
		item.setItemMeta(meta);
		biomeMenu.setItem(0, item);
		
		item.setType(Material.GRASS);
		meta.setDisplayName(Colors.format("&rPlains"));
		item.setItemMeta(meta);
		biomeMenu.setItem(1, item);
		
		item.setType(Material.DEAD_BUSH);
		meta.setDisplayName(Colors.format("&rBadlands"));
		item.setItemMeta(meta);
		biomeMenu.setItem(2, item);
		
		item.setType(Material.CACTUS);
		meta.setDisplayName(Colors.format("&rDesert"));
		item.setItemMeta(meta);
		biomeMenu.setItem(3, item);
		
		item.setType(Material.KELP);
		meta.setDisplayName(Colors.format("&rOcean"));
		item.setItemMeta(meta);
		biomeMenu.setItem(4, item);
		
		item.setType(Material.SPRUCE_SAPLING);
		meta.setDisplayName(Colors.format("&rTaiga"));
		item.setItemMeta(meta);
		biomeMenu.setItem(5, item);
		
		item.setType(Material.VINE);
		meta.setDisplayName(Colors.format("&rSwamp"));
		item.setItemMeta(meta);
		biomeMenu.setItem(6, item);
		
		item.setType(Material.STONE);
		meta.setDisplayName(Colors.format("&rMountains"));
		item.setItemMeta(meta);
		biomeMenu.setItem(7, item);
		
		item.setType(Material.PINK_PETALS);
		meta.setDisplayName(Colors.format("&rCherry Grove"));
		item.setItemMeta(meta);
		biomeMenu.setItem(8, item);

		item.setType(Material.ACACIA_SAPLING);
		meta.setDisplayName(Colors.format("&rSavanna"));
		item.setItemMeta(meta);
		biomeMenu.setItem(9, item);
		
		item.setType(Material.BLUE_ICE);
		meta.setDisplayName(Colors.format("&rGlacier"));
		item.setItemMeta(meta);
		biomeMenu.setItem(10, item);
		
		item.setType(Material.JUNGLE_SAPLING);
		meta.setDisplayName(Colors.format("&rJungle"));
		item.setItemMeta(meta);
		biomeMenu.setItem(11, item);
		
		item.setType(Material.SAND);
		meta.setDisplayName(Colors.format("&rBeach"));
		item.setItemMeta(meta);
		biomeMenu.setItem(12, item);
		
		item.setType(Material.SEAGRASS);
		meta.setDisplayName(Colors.format("&rRiver"));
		item.setItemMeta(meta);
		biomeMenu.setItem(13, item);
	}

	private BukkitRunnable getBiomeSearchTask(BlockFace direction) {
		return new BukkitRunnable() {

			@Override
			public void run() {
				
				Location newStart = start.clone();
				
				while (!biomeFound) {
					
					switch(direction) {
					case NORTH:
						newStart = newStart.add(0, 0, -64);
						if (newStart.getWorld().getBiome(newStart).toString().contains(finding.toString())) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case SOUTH:
						newStart = newStart.add(0, 0, 64);
						if (newStart.getWorld().getBiome(newStart).toString().contains(finding.toString())) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case EAST:
						newStart = newStart.add(64, 0, 0);
						if (newStart.getWorld().getBiome(newStart).toString().contains(finding.toString())) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case WEST:
						newStart = newStart.add(-64, 0, 0);
						if (newStart.getWorld().getBiome(newStart).toString().contains(finding.toString())) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case NORTH_EAST:
						newStart = newStart.add(64, 0, -64);
						if (newStart.getWorld().getBiome(newStart).toString().contains(finding.toString())) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case NORTH_WEST:
						newStart = newStart.add(-64, 0, -64);
						if (newStart.getWorld().getBiome(newStart).toString().contains(finding.toString())) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case SOUTH_EAST:
						newStart = newStart.add(64, 0, 64);
						if (newStart.getWorld().getBiome(newStart).toString().contains(finding.toString())) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case SOUTH_WEST:
						newStart = newStart.add(-64, 0, 64);
						if (newStart.getWorld().getBiome(newStart).toString().contains(finding.toString())) {
							biomeFound = true;
							found = newStart;
						}
						break;
					
					default:
						break;
					
					}
					
				}
				
			}
			
		};
	}
	
}
