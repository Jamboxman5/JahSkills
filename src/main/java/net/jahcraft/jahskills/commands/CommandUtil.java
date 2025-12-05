package net.jahcraft.jahskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class CommandUtil {

	private CommandSender sender;
	private String label;
	
	public CommandUtil(CommandSender cmdsender, String cmdlabel) {
		sender = cmdsender;
		label = cmdlabel;
	}
	
	public boolean hasAdminPermission() {
		return sender.hasPermission("jahskills.admin");
	}
	
	public boolean canDoCommand() {
		return hasAdminPermission() || sender.hasPermission("jahskills." + label.toLowerCase());
	}
	
	public void sendNoPermission() {
		sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
	}
	
	public void sendPlayerNotFound() {
		sender.sendMessage(ChatColor.RED + "Player not found!");
	}
	
	public void sendUsage(String usage) {
		sender.sendMessage(ChatColor.RED + "Usage: " + usage);
	}
	
	public boolean isPlayer(String name) {
		return Bukkit.getPlayer(name) != null;
	}
	
	public void denyConsole() {
		sender.sendMessage(ChatColor.RED + "Console users can't do that!");
	}
	
	public void sendErrMessage(String msg) {
		sender.sendMessage(ChatColor.RED + msg);
	}
	
}
