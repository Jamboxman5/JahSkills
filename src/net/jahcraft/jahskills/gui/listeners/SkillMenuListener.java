package net.jahcraft.jahskills.gui.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.jahcraft.jahskills.gui.animations.SubMenuAnim;
import net.jahcraft.jahskills.gui.menus.ButcherMenu;
import net.jahcraft.jahskills.gui.menus.CavemanMenu;
import net.jahcraft.jahskills.gui.menus.NaturalistMenu;
import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.Colors;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class SkillMenuListener implements Listener {

	public static List<Inventory> invs = new ArrayList<>();
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!invs.contains(e.getClickedInventory())) return;
		
		e.setCancelled(true);
		if (e.getSlot() == 10) {
			if (SkillManager.canClaimSkill((Player)e.getWhoClicked(), SkillType.BUTCHER) && e.getClick() == ClickType.SHIFT_LEFT) {
				invs.remove(e.getClickedInventory());
				sendClaimMainSkillConfirmation((Player)e.getWhoClicked(), SkillType.BUTCHER);
				
				
			} else {
				invs.remove(e.getClickedInventory());
				Inventory inv = ButcherMenu.getInv((Player)e.getWhoClicked());
				ButcherMenuListener.invs.add(inv);
				e.getWhoClicked().openInventory(inv);
				Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SubMenuAnim(inv, (Player)e.getWhoClicked()));
			}
			
			
		}
		if (e.getSlot() == 12) {
			invs.remove(e.getClickedInventory());
			Inventory inv = CavemanMenu.getInv((Player)e.getWhoClicked());
			CavemanMenuListener.invs.add(inv);
			e.getWhoClicked().openInventory(inv);
			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SubMenuAnim(inv, (Player)e.getWhoClicked()));

		}
		
		if (e.getSlot() == 14) {
			invs.remove(e.getClickedInventory());
			Inventory inv = NaturalistMenu.getInv((Player)e.getWhoClicked());
			NaturalistMenuListener.invs.add(inv);
			e.getWhoClicked().openInventory(inv);
			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SubMenuAnim(inv, (Player)e.getWhoClicked()));

		}
	}
	
	public static void sendClaimMainSkillConfirmation(Player player, SkillType type){
		TextComponent click = new TextComponent("Click ");
		TextComponent yes = new TextComponent("YES ");
		TextComponent or = new TextComponent("to confirm or ");
		TextComponent no = new TextComponent("NO ");
		TextComponent toCancel = new TextComponent("to cancel.");
		
		TextComponent[] comps = {click, yes, or, no, toCancel};
		
		click.setColor(Colors.PALEBLUE);
		yes.setColor(ChatColor.GREEN);
		or.setColor(Colors.PALEBLUE);
		no.setColor(ChatColor.RED);
		toCancel.setColor(Colors.PALEBLUE);
		click.setBold(false);
		yes.setBold(true);
		no.setBold(true);
		yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/claimmainskill " + type.toString()));
		no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/skills"));
		yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
				Colors.format("&7Click to confirm this skill as your main."))));
		no.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
				Colors.format("&7Click to cancel."))));
		player.closeInventory();
		player.sendMessage(Colors.PALEBLUE + "Are you sure you wish to claim this as your main skill?");
		player.spigot().sendMessage(comps);
		player.sendMessage(ChatColor.RED + "This costs 5 skill points and cannot be undone!");
		
	}
	
}
