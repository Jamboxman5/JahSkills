package net.jahcraft.jahskills.gui.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.jahcraft.jahskills.gui.animations.SkillMenuAnim;
import net.jahcraft.jahskills.gui.menus.SkillMenu;
import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.perks.Perks;
import net.jahcraft.jahskills.skills.Butcher;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.md_5.bungee.api.ChatColor;

public class MenuFunctions {
	
	public static void skillLevelUpButton(Player p, SkillType type, InventoryClickEvent e) {
		if (SkillManager.canLevelUp(p, type)) {
			if (e.getClick() == ClickType.SHIFT_LEFT) {
				while (SkillManager.canLevelUp(p, type)) {
					SkillManager.levelUp(p, type);
				}
			} else {
				SkillManager.levelUp(p, type);
			}
			e.getInventory().setItem(22, Butcher.getSkillButton(p));
			e.getInventory().setItem(49, SkillMenu.getInfoButton(p));
			p.sendMessage(ChatColor.RED + "You have " + SkillManager.getPoints(p) + " points remaining.");
		}
	}
	
	static void clickPerk(InventoryClickEvent e, SkillType type, Player p, Perk perk) {
		if (SkillManager.hasPerk(p, perk)) {
			if (SkillManager.activePerk(p, perk)) {
				SkillManager.deactivatePerk(p, perk);
			} else {
				for (Perk conflict : Perks.getConflicts(perk)) {
//					p.sendMessage(conflict.toString());
					SkillManager.deactivatePerk(p, conflict);
					e.getInventory().setItem(getSlot(conflict), Perks.getButton(p, conflict));
				}
				SkillManager.activatePerk(p, perk);
			}
		}
		else if (Perks.canBuy(p, type, perk)) {
			SkillManager.buyPerk(p, perk);
			p.sendMessage(ChatColor.RED + "You have " + SkillManager.getPoints(p) + " points remaining.");
		}
		e.getInventory().setItem(e.getSlot(), Perks.getButton(p, perk));
		e.getInventory().setItem(49, SkillMenu.getInfoButton(p));

	}
	
	public static int getSlot(Perk p) {
		switch(p) {
		case BLOODMONEY:
			return 36;
		case BLUNTFORCETRAUMA:
			return 43;
		case HITMAN:
			return 40;
		case KILLINGBLOW:
			return 39;
		case SELFDEFENSE:
			return 38;
		case SERRATIONS:
			return 42;
		case SPOILSOFWAR:
			return 37;
		case THEGRINDR:
			return 44;
		case THEPUMMELER:
			return 41;
		case MOTHERLODE:
			return 36;
		case WEEPINGCREEPERS:
			return 43;
		case CLIMBINGGEAR:
			return 40;
		case CAVEVISION:
			return 39;
		case EFFICIENTDIGGER:
			return 38;
		case DIVININGROD:
			return 42;
		case OREWHISPERER:
			return 37;
		case MANICMINING:
			return 44;
		case THERMALINSULATION:
			return 41;
		default:
			return -1;
		
		}
	}
	
	public static void clickBackButton(List<Inventory> invs, InventoryClickEvent e, Player p) {
		invs.remove(e.getClickedInventory());
		Inventory inv = SkillMenu.getInv(p);
		SkillMenuListener.invs.add(inv);
		e.getWhoClicked().openInventory(inv);
		Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SkillMenuAnim(inv, p));
	}

}
