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

public class HarvesterMenuListener implements Listener {
	
public static List<Inventory> invs = new ArrayList<>();
	
	SkillType type = SkillType.HARVESTER;

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
			MenuFunctions.clickPerk(e,type,p, Perk.MOTHERLODE);
		}
		else if (e.getSlot() == 37) {
			MenuFunctions.clickPerk(e,type,p, Perk.OREWHISPERER);
		}
		else if (e.getSlot() == 38) {
			MenuFunctions.clickPerk(e,type,p, Perk.EFFICIENTDIGGER);
		}
		else if (e.getSlot() == 39) {
			MenuFunctions.clickPerk(e,type,p, Perk.CAVEVISION);
		}
		else if (e.getSlot() == 40) {
			MenuFunctions.clickPerk(e,type,p, Perk.CLIMBINGGEAR);
		}
		else if (e.getSlot() == 41) {
			MenuFunctions.clickPerk(e,type,p, Perk.THERMALINSULATION);
		}
		else if (e.getSlot() == 42) {
			MenuFunctions.clickPerk(e,type,p, Perk.DIVININGROD);
		}
		else if (e.getSlot() == 43) {
			MenuFunctions.clickPerk(e,type,p, Perk.SUPERIORSMELTING);
		}
		else if (e.getSlot() == 44) {
			MenuFunctions.clickPerk(e,type,p, Perk.MANICMINING);
		}
	}

}
