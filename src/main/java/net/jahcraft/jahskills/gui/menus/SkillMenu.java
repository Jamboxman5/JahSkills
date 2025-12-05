package net.jahcraft.jahskills.gui.menus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.jahcraft.jahskills.skills.Butcher;
import net.jahcraft.jahskills.skills.Caveman;
import net.jahcraft.jahskills.skills.Explorer;
import net.jahcraft.jahskills.skills.Harvester;
import net.jahcraft.jahskills.skills.Huntsman;
import net.jahcraft.jahskills.skills.Intellectual;
import net.jahcraft.jahskills.skills.Naturalist;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skills.Survivalist;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.Colors;
import net.md_5.bungee.api.ChatColor;

public class SkillMenu {
			
	private static String breaker = Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                   ";
	
	public static Inventory getInv(Player player) {

		Inventory inv = Bukkit.createInventory(null, 54, "Skill Menu");
		setFillers(inv);
		setButtons(inv, player);
		
		return inv;
	}
	
	private static void setButtons(Inventory inv, Player p) {
		inv.setItem(10, Butcher.getButton(p));
		inv.setItem(12, Caveman.getButton(p));
		inv.setItem(14, Naturalist.getButton(p));
		inv.setItem(16, Huntsman.getButton(p));
		inv.setItem(28, Harvester.getButton(p));
		inv.setItem(30, Intellectual.getButton(p));
		inv.setItem(32, Explorer.getButton(p));
		inv.setItem(34, Survivalist.getButton(p));
		inv.setItem(49, getInfoButton(p));
		
	}

	public static ItemStack getInfoButton(Player p) {
		ItemStack i = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(" ");
		List<String> lore = new ArrayList<>();
		lore.add(Colors.GOLD + "Stats:");
		lore.add(Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                   ");
		lore.add(Colors.PALEBLUE + "Level: " + Colors.GOLD + SkillManager.getLevel(p));
		lore.add(Colors.PALEBLUE + "Skill Points: " + Colors.GOLD + SkillManager.getPoints(p));
		lore.add(Colors.PALEBLUE + "Level Progress: " + Colors.GOLD + SkillManager.getProgress(p).multiply(BigDecimal.valueOf(100.0)).intValue() + "%");
		lore.add(Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                   ");
		lore.add("");
		meta.setLore(lore);
		i.setItemMeta(meta);
		
		return i;
	}

	private static void setFillers(Inventory inv) {
		ItemStack filler = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		ItemMeta meta = filler.getItemMeta();
		meta.setDisplayName(" ");
		meta.setLore(null);
		filler.setItemMeta(meta);
		for (int i = 0; i < 54; i++) {
			inv.setItem(i, filler);
		}
		filler.setType(Material.BLUE_STAINED_GLASS_PANE);
		int[] right = {8,17,26,35,44,53};
		int[] top = {0,1,2,3,4,5,6,7,8};
		int[] bottom = {45,46,47,48,49,50,51,52,53};
		int[] left = {0,9,18,27,36,45};
		
		for (int i : right) {
			inv.setItem(i, filler);
		}
		for (int i : top) {
			inv.setItem(i, filler);
		}
		for (int i : left) {
			inv.setItem(i, filler);
		}
		for (int i : bottom) {
			inv.setItem(i, filler);
		}

	}
	
	public static List<String> getLore(Player player, SkillType type) {
		List<String> lore = new ArrayList<>();
		if (SkillManager.getLevel(player, type) == 20) {
			lore.add(Colors.BRIGHTBLUE + "You've maxed out this skill!");
		} else {
			lore.add(Colors.PALEBLUE + "Skill Level: " + Colors.GOLD + SkillManager.getLevel(player, type));
		}
		if (SkillManager.ownsAllPerks(player, type)) {
			lore.add(Colors.BRIGHTBLUE + "You've unlocked every perk!");
		} else {
			lore.add(Colors.PALEBLUE + "Available Perks: " + Colors.GOLD + SkillManager.getAvailablePerks(player, type));

		}
		lore.add(breaker);
		if (SkillManager.getMainSkill(player) == type) {
			lore.add(Colors.PALEBLUE + "This is your main skill.");
			lore.add(breaker);
		}
		lore.add(Colors.PALEBLUE + "Active Perks: ");
		int count = 0;
		for (String line : SkillManager.getActivePerks(player, type)) {
			if (line != null) {
				lore.add(formatActivePerk(line));
				count++;
			}
		}
		if (count == 0) lore.add(formatActivePerk("None"));
		if (SkillManager.canClaimSkill(player, type)) {
			lore.add(breaker);
			lore.add(ChatColor.GREEN + "Shift click to claim this");
			lore.add(ChatColor.GREEN + "as your main skill!");
		}
		return lore;
	}
	
	public static String formatActivePerk(String perk) {
		return ChatColor.GRAY + "- " + Colors.BRIGHTBLUE + perk;
	}

	public static ItemStack getBackButton() {
		ItemStack i = new ItemStack(Material.BARRIER);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "← Back");
		i.setItemMeta(meta);
		return i;
	}
	
	
}