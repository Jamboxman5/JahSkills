package net.jahcraft.jahskills.gui.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.jahcraft.jahskills.gui.animations.SubMenuAnim;
import net.jahcraft.jahskills.gui.menus.ButcherMenu;
import net.jahcraft.jahskills.main.Main;

public class SkillMenuListener implements Listener {

	public static List<Inventory> invs = new ArrayList<>();
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!invs.contains(e.getClickedInventory())) return;
		
		e.setCancelled(true);
		if (e.getSlot() == 10) {
			invs.remove(e.getClickedInventory());
			Inventory inv = ButcherMenu.getInv((Player)e.getWhoClicked());
			ButcherMenuListener.invs.add(inv);
			e.getWhoClicked().openInventory(inv);
			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SubMenuAnim(inv, (Player)e.getWhoClicked()));

		}
	}
	
}
