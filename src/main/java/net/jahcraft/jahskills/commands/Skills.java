package net.jahcraft.jahskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.jahcraft.jahskills.gui.animations.SkillMenuAnim;
import net.jahcraft.jahskills.gui.listeners.SkillMenuListener;
import net.jahcraft.jahskills.gui.menus.SkillMenu;
import net.jahcraft.jahskills.main.Main;
import net.md_5.bungee.api.ChatColor;

public class Skills implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!label.equalsIgnoreCase("skills")) return false;
		
		if (!(sender instanceof Player)) return true;
		if (!sender.hasPermission("jahskills.menu")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
			return true;
		}
		
		Player p = (Player) sender;
		
		Inventory inv = SkillMenu.getInv(p);
		SkillMenuListener.invs.add(inv);
		p.openInventory(inv);
		Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SkillMenuAnim(inv, p));
		
		return true;

	}
	
}
