package net.jahcraft.jahskills.skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.jahcraft.jahskills.gui.menus.SkillMenu;

public class SkillUtil {

	public static ItemStack getButton(Player player, Material displayItem, SkillType type, String name) {
		ItemStack button = new ItemStack(displayItem);
		ItemMeta meta = button.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(SkillMenu.getLore(player, type));
		meta.setDisplayName(name);
		
		button.setItemMeta(meta);
		return button;
		
	}
	
}
