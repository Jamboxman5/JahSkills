package net.jahcraft.jahskills.gui.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.jahcraft.jahskills.gui.animations.SkillMenuAnim;
import net.jahcraft.jahskills.gui.menus.SkillMenu;
import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.perks.Perks;
import net.jahcraft.jahskills.skills.Caveman;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class CavemanMenuListener implements Listener {
	
public static List<Inventory> invs = new ArrayList<>();
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!invs.contains(e.getClickedInventory())) return;
		
		Player p = (Player) e.getWhoClicked();
		
		e.setCancelled(true);
		if (e.getSlot() == 4) {
			invs.remove(e.getClickedInventory());
			Inventory inv = SkillMenu.getInv(p);
			SkillMenuListener.invs.add(inv);
			e.getWhoClicked().openInventory(inv);
			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SkillMenuAnim(inv, p));

		}
		else if (e.getSlot() == 22) {
			if (SkillManager.getPoints(p) > 0 && SkillManager.getLevel(p, SkillType.CAVEMAN) < 20) {
				SkillManager.levelUp(p, SkillType.CAVEMAN);
				e.getInventory().setItem(22, Caveman.getSkillButton(p));
				e.getInventory().setItem(49, SkillMenu.getInfoButton(p));

			}
		}
		else if (e.getSlot() == 36) {
			clickPerk(e,p, Perk.MOTHERLODE);
		}
		else if (e.getSlot() == 37) {
			clickPerk(e,p, Perk.OREWHISPERER);
		}
		else if (e.getSlot() == 38) {
			clickPerk(e,p, Perk.EFFICIENTDIGGER);
		}
		else if (e.getSlot() == 39) {
			clickPerk(e,p, Perk.CAVEVISION);
		}
		else if (e.getSlot() == 40) {
			clickPerk(e,p, Perk.CLIMBINGGEAR);
		}
		else if (e.getSlot() == 41) {
			clickPerk(e,p, Perk.THERMALINSULATION);
		}
		else if (e.getSlot() == 42) {
			clickPerk(e,p, Perk.DIVININGROD);
		}
		else if (e.getSlot() == 43) {
			clickPerk(e,p, Perk.WEEPINGCREEPERS);
		}
		else if (e.getSlot() == 44) {
			clickPerk(e,p, Perk.MANICMINING);
		}
	}
	
	void clickPerk(InventoryClickEvent e, Player p, Perk perk) {
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
		else if (Perks.canBuy(p, SkillType.CAVEMAN, perk)) {
			SkillManager.buyPerk(p, perk);
		}
		e.getInventory().setItem(e.getSlot(), Perks.getButton(p, perk));
		e.getInventory().setItem(49, SkillMenu.getInfoButton(p));

	}
	
	int getSlot(Perk p) {
		switch(p) {
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

}
