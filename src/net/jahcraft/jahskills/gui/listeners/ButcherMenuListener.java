package net.jahcraft.jahskills.gui.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.jahcraft.jahskills.gui.menus.SkillMenu;
import net.jahcraft.jahskills.skills.Butcher;
import net.jahcraft.jahskills.skills.Perk;
import net.jahcraft.jahskills.skills.Perks;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class ButcherMenuListener implements Listener {
	
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
		}
		else if (e.getSlot() == 22) {
			if (SkillManager.getPoints(p) > 0 && SkillManager.getLevel(p, SkillType.BUTCHER) < 20) {
				SkillManager.levelUp(p, SkillType.BUTCHER);
				e.getInventory().setItem(22, Butcher.getSkillButton(p));
			}
		}
		else if (e.getSlot() == 36) {
			if (SkillManager.hasPerk(p, Perk.BLOODMONEY)) {
				if (SkillManager.activePerk(p, Perk.BLOODMONEY)) {
					SkillManager.deactivatePerk(p, Perk.BLOODMONEY);
				} else {
					SkillManager.activatePerk(p, Perk.BLOODMONEY);
				}
			}
			else if (Perks.canBuy(p, SkillType.BUTCHER, Perk.BLOODMONEY)) {
				SkillManager.buyPerk(p, Perk.BLOODMONEY);
			}
			e.getInventory().setItem(36, Perks.getButton(p, Perk.BLOODMONEY));

		}
		else if (e.getSlot() == 37) {
			if (SkillManager.hasPerk(p, Perk.SPOILSOFWAR)) {
				if (SkillManager.activePerk(p, Perk.SPOILSOFWAR)) {
					SkillManager.deactivatePerk(p, Perk.SPOILSOFWAR);
				} else {
					SkillManager.activatePerk(p, Perk.SPOILSOFWAR);
				}
			}
			else if (Perks.canBuy(p, SkillType.BUTCHER, Perk.SPOILSOFWAR)) {
				SkillManager.buyPerk(p, Perk.SPOILSOFWAR);
			}
			e.getInventory().setItem(37, Perks.getButton(p, Perk.SPOILSOFWAR));

		}
	}

}
