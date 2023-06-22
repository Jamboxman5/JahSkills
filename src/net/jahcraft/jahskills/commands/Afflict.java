package net.jahcraft.jahskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.jahcraft.jahskills.effects.Affliction;
import net.jahcraft.jahskills.effects.Afflictions;
import net.md_5.bungee.api.ChatColor;

public class Afflict implements CommandExecutor {
	
	CommandUtil util;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!label.equalsIgnoreCase("afflict")) return false;
		util = new CommandUtil(sender, label);
		
		if (!util.canDoCommand()) {
			util.sendNoPermission();
		}

		if (args.length == 1) {
			if (util.isPlayer(args[0])) {
				// /afflict <player>
				afflictOther(sender, args, true);
				return true;
			}
			
			// /afflict <affliction>
			afflictSelf(sender, args);
			return true;
		}
		
		if (args.length == 2) {
			// /afflict <player> <affliction>
			afflictOther(sender, args, false);
			return true;
		}
		
		sendUsage(sender);
		return true;

	}

	private void sendUsage(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Afflicts a player with a JahSkills PVP affliction.");
		sender.sendMessage(ChatColor.DARK_RED + "Afflict other player:");
		util.sendUsage("/afflict <player> <affliction>");
		sender.sendMessage(ChatColor.DARK_RED + "Afflict self:");
		util.sendUsage("/afflict <affliction>");
		sender.sendMessage(ChatColor.DARK_RED + "Afflict player w/ random affliction:");
		util.sendUsage("/afflict <player>");


		
		
	}

	private void afflictOther(CommandSender sender, String[] args, boolean isRandom) {

		if (isRandom) {
			Player target = Bukkit.getPlayer(args[0]);
			afflict(target, getAffliction());
			return;
		} else {
			if (!util.isPlayer(args[0])) {
				util.sendPlayerNotFound();
				return;
			}
			Player target = Bukkit.getPlayer(args[0]);
			afflict(target, getAffliction(args[1]));
		}
		
	}

	private void afflictSelf(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			util.denyConsole();
			return;
		}
		
		Affliction toApply = getAffliction(args[0]);
		
		Player player = (Player) sender;
		afflict(player, toApply);
		
	}
	
	private void afflict(Player player, Affliction toApply) {
		if (toApply == null) return;
		switch (toApply) {
		case BLEED:
			Afflictions.bleed(player, 8);
			return;
		case DAZE:
			Afflictions.daze(player, 6000);
			return;
		case KNOCKOUT:
			Afflictions.knockOut(player, 80);
			return;
		default:
			return;
		
		}
	}
	
	private Affliction getAffliction(String arg) {
		Affliction toApply;
		try {
			toApply = Affliction.valueOf(arg.toUpperCase());
		} catch (IllegalArgumentException e) {
			util.sendErrMessage(arg + " is not a valid affliction! Accepted values:");
			for (Affliction aff : Affliction.values()) {
				util.sendErrMessage("- " + aff.toString());
			}
			return null;
		}
		return toApply;
	}
	
	private Affliction getAffliction() {
		int index = (int)(Math.random() * Affliction.values().length);
		return Affliction.values()[index];
	}

}
