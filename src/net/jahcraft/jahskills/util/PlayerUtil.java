package net.jahcraft.jahskills.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class PlayerUtil {
	
	public static ItemStack getSkull(Player p) {
		ItemStack i = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) i.getItemMeta();
		
		meta.setOwningPlayer(p);
		meta.setDisplayName(ChatColor.RED + p.getName() + "'s skull");
		i.setItemMeta(meta);
		return i;
	}

}
