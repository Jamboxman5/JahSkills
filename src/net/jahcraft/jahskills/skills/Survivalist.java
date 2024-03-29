package net.jahcraft.jahskills.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.jahcraft.jahskills.gui.menus.SkillMenu;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.Colors;
import net.md_5.bungee.api.ChatColor;

public class Survivalist {
	
	private static SkillType type = SkillType.SURVIVALIST;
	private static String name = Colors.GOLD + "" + ChatColor.BOLD + "Survivalist";
	private static Material displayItem = Material.TOTEM_OF_UNDYING;
	private static String breaker = Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                   ";

	public static ItemStack getButton(Player player) {
		ItemStack button = new ItemStack(displayItem);
		ItemMeta meta = button.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(SkillMenu.getLore(player, type));
		meta.setDisplayName(name);
		
		button.setItemMeta(meta);
		return button;
		
	}
	
	public static String getDisplayName() { return name; }

	
	public static Perk[] getPerks() {
		Perk[] perks = {
		Perk.YOUMISSEDME,
		Perk.ARTFULDODGER,
		Perk.RESURRECTION,
		Perk.ATTACKOFTHECLONES,
		Perk.GORDONRAMSAY,
		Perk.EMTCERTIFIED,
		Perk.SUPERFOODS,
		Perk.UNMATCHEDWILLPOWER,
		Perk.SUPERSAIYAN};
		return perks;
	}



	public static ItemStack getSkillButton(Player p) {
		ItemStack button = new ItemStack(displayItem);
		ItemMeta meta = button.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		List<String> lore = new ArrayList<>();
		int level = SkillManager.getLevel(p, type);
		lore.add(Colors.PALEBLUE + "Current Level: " + Colors.GOLD + level);
		lore.add(Colors.PALEBLUE + "Maximum Level: " + Colors.GOLD + SkillManager.MAXSKILLLEVEL);
		lore.add(breaker);
		lore.add(Colors.PALEBLUE + "Leveling up this skill will");
		lore.add(Colors.PALEBLUE + "unlock new perks! The higher ");
		lore.add(Colors.PALEBLUE + "your level, the more effective ");
		lore.add(Colors.PALEBLUE + "your perks will be!");
		lore.add(breaker);
		if (SkillManager.getMainSkill(p) == type) {
			lore.add(Colors.PALEBLUE + "This is your main skill!");
			lore.add(Colors.PALEBLUE + "All perks will be even more effective!");
			lore.add(breaker);
		}
		if (SkillManager.canLevelUp(p, type)) {
			lore.add(Colors.BRIGHTBLUE + "Click to level up! Cost: " + Colors.GOLD + SkillManager.getLevelCost(p, type));
			lore.add(Colors.BRIGHTBLUE + "Shift click to auto level up!");
		} else if (level >= 20) {
			lore.add(Colors.BRIGHTBLUE + "You've maxed out this skill!");
		} else if (level < 20) {
			int morePoints = SkillManager.getPointsToLevelUp(p, type);
			if (morePoints > 1) {
				lore.add(ChatColor.RED + "You need " + SkillManager.getPointsToLevelUp(p, type) + " more points");
			} else {
				lore.add(ChatColor.RED + "You need " + SkillManager.getPointsToLevelUp(p, type) + " more point");
			}
			lore.add(ChatColor.RED + "to level up this skill!");		}
		
		meta.setLore(lore);
		meta.setDisplayName(name);
		
		button.setItemMeta(meta);
		return button;
	}

	

}
