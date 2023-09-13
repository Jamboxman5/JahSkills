package net.jahcraft.jahskills.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.jahcraft.jahskills.main.Main;

public class BiomeFinder {
	
	private boolean biomeFound;

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
	
	public void cancelTasks() {
		for (BukkitRunnable task : activeTasks) {
			try {
				task.cancel();
			} catch(IllegalStateException e) {
				
			}
		}
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
						if (newStart.getWorld().getBiome(newStart) == finding) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case SOUTH:
						newStart = newStart.add(0, 0, 64);
						if (newStart.getWorld().getBiome(newStart) == finding) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case EAST:
						newStart = newStart.add(64, 0, 0);
						if (newStart.getWorld().getBiome(newStart) == finding) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case WEST:
						newStart = newStart.add(-64, 0, 0);
						if (newStart.getWorld().getBiome(newStart) == finding) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case NORTH_EAST:
						newStart = newStart.add(64, 0, -64);
						if (newStart.getWorld().getBiome(newStart) == finding) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case NORTH_WEST:
						newStart = newStart.add(-64, 0, -64);
						if (newStart.getWorld().getBiome(newStart) == finding) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case SOUTH_EAST:
						newStart = newStart.add(64, 0, 64);
						if (newStart.getWorld().getBiome(newStart) == finding) {
							biomeFound = true;
							found = newStart;
						}
						break;
					case SOUTH_WEST:
						newStart = newStart.add(-64, 0, 64);
						if (newStart.getWorld().getBiome(newStart) == finding) {
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
