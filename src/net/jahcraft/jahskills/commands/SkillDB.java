package net.jahcraft.jahskills.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.skilltracking.ProgressBar;
import net.jahcraft.jahskills.util.Colors;
import net.md_5.bungee.api.ChatColor;

public class SkillDB implements CommandExecutor, TabCompleter {
	
	List<String> arguments1 = new ArrayList<>();
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("jahskills.admin")) return null;
		if (arguments1.isEmpty()) {
			arguments1.add("reset");
			arguments1.add("clear");
			arguments1.add("givepoints");
		}
		List<String> result = new ArrayList<>();
		if (args.length == 1) {
			for (String s : arguments1) {
				if (s.toLowerCase().startsWith(args[0])) {
					result.add(s);
				}
			}
			return result;
		}
		return null;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!label.equalsIgnoreCase("skilldb")) return false;
		if (!sender.hasPermission("JahSkills.admin")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage("Usage: /skilldb <cmd> [target] [value]");
			sender.sendMessage("Commands: reset, clear, givepoints");
			return true;
		}
		
		String subcmd = args[0].toLowerCase();
		
		switch(subcmd) {
		case "reset": {
			reset(sender, args);
			break;
		}
		case "clear": {
			clear(sender);
			break;
		}
		case "givepoints": {
			givePoints(sender, args);
			break;
		}
		case "add": {
			break;
		}
		case "remove": {
			break;
		}
		}
		
		return false;

	}
	
	private void givePoints(CommandSender sender, String[] args) {
		if (Bukkit.getPlayer(args[1]) == null) {
			sender.sendMessage(ChatColor.RED + "Player not found!");
			return;
		}
		
		try {
			Integer.parseInt(args[2]);
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "Invalid point value!");
			return;
		}
		
		Player target = Bukkit.getPlayer(args[1]);
		int pts = Integer.parseInt(args[2]);
		
		SkillManager.addPoints(target, pts);
		target.sendMessage(Colors.BRIGHTBLUE + "You've been gifted " + Colors.GOLD + pts + Colors.BRIGHTBLUE + " skill points!");
		if (target != (Player) sender) {
			sender.sendMessage(Colors.BLUE + "They've been gifted " + Colors.GOLD + pts + Colors.BRIGHTBLUE + " skill points.");
		}
		ProgressBar.updateBar(target);		
	}

	private void clear(CommandSender sender) {
		SkillDatabase.clearDatabase();
		SkillDatabase.setupDatabase();
		sender.sendMessage(Colors.BLUE + "Database cleared.");	
		ProgressBar.updateAll();
	}

	private void reset(CommandSender sender, String[] args) {
		if (Bukkit.getPlayer(args[1]) == null) {
			sender.sendMessage(ChatColor.RED + "Player not found!");
			return;
		}
		
		Player target = Bukkit.getPlayer(args[1]);
		
		SkillManager.reset(target);
		target.sendMessage(ChatColor.RED + "Your skills have been reset.");
		if (target != (Player) sender) {
			sender.sendMessage(Colors.BLUE + "Their skills have been reset.");
		}
		ProgressBar.updateBar(target);
	}

}
