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
import net.jahcraft.jahskills.skills.Butcher;
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
			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SkillMenuAnim(inv, p));

		}
		else if (e.getSlot() == 22) {
			if (SkillManager.getPoints(p) > 0 && SkillManager.getLevel(p, SkillType.BUTCHER) < 20) {
				SkillManager.levelUp(p, SkillType.BUTCHER);
				e.getInventory().setItem(22, Butcher.getSkillButton(p));
				e.getInventory().setItem(49, SkillMenu.getInfoButton(p));
			}
		}
		else if (e.getSlot() == 36) {
			clickPerk(e,p, Perk.BLOODMONEY);
		}
		else if (e.getSlot() == 37) {
			clickPerk(e,p, Perk.SPOILSOFWAR);
		}
		else if (e.getSlot() == 38) {
			clickPerk(e,p, Perk.SELFDEFENSE);
		}
		else if (e.getSlot() == 39) {
			clickPerk(e,p, Perk.KILLINGBLOW);
		}
		else if (e.getSlot() == 40) {
			clickPerk(e,p, Perk.HITMAN);
		}
		else if (e.getSlot() == 41) {
			clickPerk(e,p, Perk.THEPUMMELER);
		}
		else if (e.getSlot() == 42) {
			clickPerk(e,p, Perk.SERRATIONS);
		}
		else if (e.getSlot() == 43) {
			clickPerk(e,p, Perk.BLUNTFORCETRAUMA);
		}
		else if (e.getSlot() == 44) {
			clickPerk(e,p, Perk.THEGRINDR);
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
		else if (Perks.canBuy(p, SkillType.BUTCHER, perk)) {
			SkillManager.buyPerk(p, perk);
		}
		e.getInventory().setItem(e.getSlot(), Perks.getButton(p, perk));
		e.getInventory().setItem(49, SkillMenu.getInfoButton(p));

	}
	
	int getSlot(Perk p) {
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
		default:
			return -1;
		
		}
	}

}
