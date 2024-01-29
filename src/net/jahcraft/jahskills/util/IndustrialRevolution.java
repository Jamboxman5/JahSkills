package net.jahcraft.jahskills.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class IndustrialRevolution {

	public static Inventory redstoneSelector;
	public static HashMap<Player, ItemStack> menuItems = new HashMap<>();
	
	public static Material[] getExchangeables() {
		Material[] mats = {
				Material.REDSTONE, 
				Material.REDSTONE_TORCH, 
				Material.REPEATER,
				Material.COMPARATOR,
				Material.DAYLIGHT_DETECTOR,
				Material.TRIPWIRE_HOOK,
				Material.POWERED_RAIL,
				Material.DETECTOR_RAIL,
				Material.ACTIVATOR_RAIL,
				Material.LIGHTNING_ROD,
				Material.IRON_DOOR,
				Material.IRON_TRAPDOOR,
				Material.OBSERVER,
				Material.DROPPER,
				Material.DISPENSER,
				Material.PISTON,
				Material.REDSTONE_LAMP,
				Material.TARGET
		};
		return mats;
	}

	public static void initRedstoneMenu() {
		redstoneSelector = Bukkit.createInventory(null, 18, "Exchange for:");
		
		ItemStack item = new ItemStack(Material.REDSTONE);
		
		int i = 0;
		for (Material m : getExchangeables()) {
			item.setType(m);
			redstoneSelector.setItem(i, item);
			i++;
		}
	}
	
}
