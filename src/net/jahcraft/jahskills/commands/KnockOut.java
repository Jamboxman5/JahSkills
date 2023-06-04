package net.jahcraft.jahskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.jahcraft.jahskills.skills.Effects;

public class KnockOut implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!label.equalsIgnoreCase("knockout")) return false;

		if (args.length != 1) {
			sender.sendMessage("Usage: /knockout <player>");
			return true;
		}
		
		if (Bukkit.getPlayer(args[0]) == null) {
			sender.sendMessage("Usage: /knockout <player>");
			return true;
		}
		
		Effects.knockOut(Bukkit.getPlayer(args[0]), 100);
		return true;

	}

}
