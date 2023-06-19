package net.jahcraft.jahskills.gui.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;

public class HuntsmanMenuListener implements Listener {
	
public static List<Inventory> invs = new ArrayList<>();
	
	SkillType type = SkillType.HUNTSMAN;

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!invs.contains(e.getClickedInventory())) return;
		
		Player p = (Player) e.getWhoClicked();
		
		e.setCancelled(true);
		if (e.getSlot() == 4) {
			MenuFunctions.clickBackButton(invs, e, p);
		}
		else if (e.getSlot() == 22) {
			MenuFunctions.skillLevelUpButton(p, type, e);
		}
		else if (e.getSlot() == 36) {
			MenuFunctions.clickPerk(e,type,p, Perk.SLAUGHTERHOUSE);
		}
		else if (e.getSlot() == 37) {
			MenuFunctions.clickPerk(e,type,p, Perk.SWEETMEATS);
		}
		else if (e.getSlot() == 38) {
			MenuFunctions.clickPerk(e,type,p, Perk.PIGWHISPERER);
		}
		else if (e.getSlot() == 39) {
			MenuFunctions.clickPerk(e,type,p, Perk.CHRISKYLE);
		}
		else if (e.getSlot() == 40) {
			MenuFunctions.clickPerk(e,type,p, Perk.EXPLOSIVESHOTS);
		}
		else if (e.getSlot() == 41) {
			MenuFunctions.clickPerk(e,type,p, Perk.INCENDIARYROUNDS);
		}
		else if (e.getSlot() == 42) {
			MenuFunctions.clickPerk(e,type,p, Perk.BAREHANDEDARCHERY);
		}
		else if (e.getSlot() == 43) {
			MenuFunctions.clickPerk(e,type,p, Perk.BOMBTHROWER);
		}
		else if (e.getSlot() == 44) {
			MenuFunctions.clickPerk(e,type,p, Perk.SNIPERSENSE);
		}
	}

}
