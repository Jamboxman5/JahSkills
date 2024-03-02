package net.jahcraft.jahskills.commands;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.util.Colors;
import net.md_5.bungee.api.ChatColor;

public class SkillQuery implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!label.equalsIgnoreCase("skillquery")) return false;
		if (!sender.hasPermission("JahSkills.admin")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage("Usage: /skillquery <query>");
			return true;
		}
		String query = "";
		for (int i = 0; i<args.length; i++) {
			query += args[i] + " ";
		}
		
		try {
			SkillDatabase.sendUnsafeQuery(query);
			sender.sendMessage(Colors.BLUE + "Query sent!");
			return true;
		} catch (SQLException e) {
			sender.sendMessage(ChatColor.RED + "Invalid query! Check console for details!");
			e.printStackTrace();
			return true;
		}
		
	}

}
