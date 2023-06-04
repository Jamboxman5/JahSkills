package net.jahcraft.jahskills.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.jahcraft.jahskills.gui.menus.SkillMenu;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.Colors;
import net.md_5.bungee.api.ChatColor;

public class Butcher {
	
	private static SkillType type = SkillType.BUTCHER;
	private static String name = Colors.BRIGHTRED + ChatColor.BOLD + "Butcher";
	private static Material displayItem = Material.NETHERITE_SWORD;
	private static String breaker = Colors.BLUE + ChatColor.STRIKETHROUGH + "                   ";

	public static ItemStack getButton(Player player) {
		ItemStack button = new ItemStack(displayItem);
		ItemMeta meta = button.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(SkillMenu.getLore(player, type));
		meta.setDisplayName(name);
		
		button.setItemMeta(meta);
		return button;
		
	}
	
	
	
	public static Perk[] getPerks() {
		Perk[] perks = {
		Perk.BLOODMONEY,
		Perk.SPOILSOFWAR,
		Perk.SELFDEFENSE,
		Perk.KILLINGBLOW,
		Perk.HITMAN,
		Perk.THEPUMMELER,
		Perk.SERRATIONS,
		Perk.BLUNTFORCETRAUMA,
		Perk.THEGRINDR};
		return perks;
	}



	public static ItemStack getSkillButton(Player p) {
		ItemStack button = new ItemStack(displayItem);
		ItemMeta meta = button.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		List<String> lore = new ArrayList<>();
		int level = SkillManager.getLevel(p, type);
		lore.add(Colors.PALEBLUE + "Current Level: " + Colors.GOLD + level);
		lore.add(Colors.PALEBLUE + "Maximum Level: " + Colors.GOLD + 20);
		lore.add(breaker);
		lore.add(Colors.PALEBLUE + "Leveling up this skill will make");
		lore.add(Colors.PALEBLUE + "you deal more damage to hostiles");
		lore.add(Colors.PALEBLUE + "and increase your resistance to");
		lore.add(Colors.PALEBLUE + "enemy perks of this type!");
		lore.add(breaker);
		if (level < 20 && SkillManager.hasPoints(p)) {
			lore.add(Colors.BRIGHTBLUE + "Click to level up!");
		} else if (level >= 20) {
			lore.add(Colors.BRIGHTBLUE + "You've maxed out this skill!");
		} else if (level < 20) {
			lore.add(ChatColor.RED + "You can't afford to level up this skill!");
		}
		
		meta.setLore(lore);
		meta.setDisplayName(name);
		
		button.setItemMeta(meta);
		return button;
	}

	

}
