package net.jahcraft.jahskills.util;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Format {
	
	public static void sendEffectCooldown(Player p, String effect, int seconds) {
		TextComponent wait = new TextComponent("You must wait ");
		TextComponent secs = new TextComponent(seconds + "");
		TextComponent toUse = new TextComponent(" more seconds to use " + effect + " again!");

		wait.setColor(ChatColor.RED);
		secs.setColor(Colors.GOLD);
		toUse.setColor(ChatColor.RED);
		
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, wait, secs, toUse);
	}
	
	public static void sendReady(Player p, String tool) {
		TextComponent youReady = new TextComponent("You ready your ");
		TextComponent tool2 = new TextComponent(tool);
		TextComponent period = new TextComponent(".");

		youReady.setColor(Colors.BLUE);
		tool2.setColor(Colors.GOLD);
		period.setColor(Colors.BLUE);
		
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, youReady, tool2, period);
	}
	public static void sendLower(Player p, String tool) {
		TextComponent youLower = new TextComponent("You lower your ");
		TextComponent tool2 = new TextComponent(tool);
		TextComponent period = new TextComponent(".");

		youLower.setColor(ChatColor.GRAY);
		tool2.setColor(Colors.GOLD);
		period.setColor(ChatColor.GRAY);
		
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, youLower, tool2, period);
	}

}
