package net.jahcraft.jahskills.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.skilltracking.ProgressBar;
import net.jahcraft.jahskills.util.Colors;
import net.md_5.bungee.api.ChatColor;

public class SkillDB implements CommandExecutor, TabCompleter {
	
	List<String> arguments1 = new ArrayList<>();
	List<String> skilltypes = new ArrayList<>();
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("jahskills.admin")) return null;
		if (arguments1.isEmpty()) {
			arguments1.add("reset");
			arguments1.add("info");
			arguments1.add("lookup");
			arguments1.add("givepoints");
			arguments1.add("setmainskill");
			arguments1.add("setlevel");
		}
		if (skilltypes.isEmpty()) {
			for (SkillType type : SkillType.values()) {
				skilltypes.add(type.toString().toLowerCase());
			}
			skilltypes.add("none");
		}
		List<String> result = new ArrayList<>();
		if (args.length == 1) {
			for (String s : arguments1) {
				if (s.toLowerCase().startsWith(args[0])) {
					result.add(s);
				}
			}
			return result;
		} else if (args.length == 3 && args[0].contains("setmainskill")) {
			for (String s : skilltypes) {
				if (s.toLowerCase().startsWith(args[2])) {
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
		case "info": {
			info(sender, args);
			break;
		}
		case "lookup": {
			info(sender, args);
			break;
		}
		case "givepoints": {
			givePoints(sender, args);
			break;
		}
		case "setmainskill": {
			setMainSkill(sender, args);
			break;
		}
		case "setlevel": {
			setLevel(sender, args);
			break;
		}
		case "remove": {
			break;
		}
		}
		
		return false;

	}
	
	private void setLevel(CommandSender sender, String[] args) {
		CommandUtil util = new CommandUtil(sender, "skilldb");
		
		if (args.length != 3) {
			util.sendUsage("/skilldb setlevel <player> <level>");
			return;
		}
		int lvl = 0;
		try {
			lvl = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			sender.sendMessage("Invalid Level!");
			return;
		}
		if (!util.isPlayer(args[1])) {
			util.sendPlayerNotFound();
			return;
		}
		
		Player target = Bukkit.getPlayer(args[1]);

		SkillManager.setLevel(target, lvl);
		sender.sendMessage(Colors.BLUE + "Their level has been updated.");
		
	}
	
	private void setMainSkill(CommandSender sender, String[] args) {
		CommandUtil util = new CommandUtil(sender, "skilldb");
		
		if (args.length != 3) {
			util.sendUsage("/skilldb setmainskill <player> <skilltype>");
			return;
		}
		boolean clearMainSkill = false;
		try {
			if (args[2].equalsIgnoreCase("none")) {
				clearMainSkill = true;
			} else {
				SkillType.valueOf(args[2].toUpperCase());
			}
		} catch (IllegalArgumentException e) {
			sender.sendMessage("Skill not found!");
			return;
		}
		if (!util.isPlayer(args[1])) {
			util.sendPlayerNotFound();
			return;
		}
		
		Player target = Bukkit.getPlayer(args[1]);

		if (clearMainSkill) {
			SkillManager.removeMainSkill(target);
			sender.sendMessage(Colors.BLUE + "Their main skill has been removed.");
		} else {
			SkillType type = SkillType.valueOf(args[2].toUpperCase());
			SkillManager.setMainSkill(target, type);
			sender.sendMessage(Colors.BLUE + "Their main skill has been changed.");
		}
		
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

	private void info(CommandSender sender, String[] args) {
		
		if (Bukkit.getPlayer(args[1]) == null) {
			//PLAYER OFFLINE
			if (SkillDatabase.getUUID(args[1]).equals(args[1])) {
				//playernotfound
				sender.sendMessage(ChatColor.RED + "Player not found!");
				return;
			}
			
			String uuid = SkillDatabase.getUUID(args[1]);
			sender.sendMessage(Colors.PALEBLUE + "Fetching Skill Data for " + Colors.GOLD + SkillDatabase.getUsername(uuid) + Colors.PALEBLUE + "...");
			new BukkitRunnable() {

				@Override
				public void run() {
					List<String> msgs = getInfoMessages(args[1], uuid);
					for (String s : msgs) {
						sender.sendMessage(s);
					}
					
				}
				
			}.runTaskAsynchronously(Main.plugin);
			
			return;
		}
				
		Player target = Bukkit.getPlayer(args[1]);
		
		sender.sendMessage(Colors.PALEBLUE + "Fetching Skill Data for " + Colors.GOLD + target.getDisplayName() + Colors.PALEBLUE + "...");
		
		new BukkitRunnable() {

			@Override
			public void run() {
				List<String> msgs = getInfoMessages(target);
				for (String s : msgs) {
					sender.sendMessage(s);
				}
				
			}
			
		}.runTaskAsynchronously(Main.plugin);
		
	}
	
	private List<String> getInfoMessages(Player p) {
		ArrayList<String> messages = new ArrayList<>();
		
		messages.add(Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                    ");
		messages.add(Colors.PALEBLUE + "Player: " + Colors.GOLD + p.getDisplayName());
		messages.add(Colors.PALEBLUE + "Skill Level: " + Colors.GOLD + SkillManager.getLevel(p));
		messages.add(Colors.PALEBLUE + "Skill Points: " + Colors.GOLD + SkillManager.getPoints(p));
		messages.add(Colors.PALEBLUE + "Level Progress: " + Colors.GOLD + String.format("%,.2f", SkillManager.getProgress(p).doubleValue()));
		if (SkillManager.getMainSkill(p) != null) {
			messages.add(Colors.PALEBLUE + "Main Skill: " + Colors.GOLD + SkillManager.getMainSkill(p));
		} else {
			messages.add(Colors.PALEBLUE + "Main Skill: " + Colors.GOLD + "None");
		}
		messages.add(Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                    ");
		for (SkillType type : SkillType.values()) {
			messages.add(Colors.PALEBLUE + Colors.getFormattedName(type) + " Level: " + Colors.GOLD + SkillManager.getLevel(p, type));
			String perks = Colors.PALEBLUE + Colors.getFormattedName(type) + " Perks: ";
			boolean firstPerk = true;
			for (String perk : SkillManager.getActivePerks(p, type)) {
				if (perk == null) {
					//do nothing
				}
				else if (firstPerk) {
					perks += Colors.GOLD + perk;
					firstPerk = false;
				} else {
					perks += Colors.PALEBLUE + ", " + Colors.GOLD + perk;
				}
			}
			if (!firstPerk) {
				messages.add(perks);
			}
		}
		messages.add(Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                    ");

		return messages;
	}
	
	private List<String> getInfoMessages(String name, String uuid) {
		ArrayList<String> messages = new ArrayList<>();
		
		messages.add(Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                    ");
		messages.add(Colors.PALEBLUE + "Player: " + Colors.GOLD + SkillDatabase.getUsername(uuid));
		messages.add(Colors.PALEBLUE + "Skill Level: " + Colors.GOLD + SkillDatabase.getData(uuid, "userlevels"));
		messages.add(Colors.PALEBLUE + "Skill Points: " + Colors.GOLD + SkillDatabase.getData(uuid, "userpoints"));
		messages.add(Colors.PALEBLUE + "Level Progress: " + Colors.GOLD + String.format("%,.2f", Double.parseDouble(SkillDatabase.getData(uuid, "userprogress"))));
		if (SkillDatabase.getMainSkill(uuid) != null) {
			messages.add(Colors.PALEBLUE + "Main Skill: " + Colors.GOLD + SkillDatabase.getMainSkill(uuid));
		} else {
			messages.add(Colors.PALEBLUE + "Main Skill: " + Colors.GOLD + "None");
		}
		messages.add(Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                    ");
		for (SkillType type : SkillType.values()) {
			String table = type.toString().toLowerCase() + "level";
			messages.add(Colors.PALEBLUE + Colors.getFormattedName(type) + " Level: " + Colors.GOLD + SkillDatabase.getLevel(uuid, table));
			String perks = Colors.PALEBLUE + Colors.getFormattedName(type) + " Perks: ";
			boolean firstPerk = true;
			for (String perk : SkillDatabase.getActivePerks(uuid, type)) {
				if (perk == null) {
					//do nothing
				}
				else if (firstPerk) {
					perks += Colors.GOLD + perk;
					firstPerk = false;
				} else {
					perks += Colors.PALEBLUE + ", " + Colors.GOLD + perk;
				}
			}
			if (!firstPerk) {
				messages.add(perks);
			}
		}
		messages.add(Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                    ");

		return messages;
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
