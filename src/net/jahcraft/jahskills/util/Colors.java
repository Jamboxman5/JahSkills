package net.jahcraft.jahskills.util;

import net.md_5.bungee.api.ChatColor;

public class Colors {
	
	public static final ChatColor GOLD = ChatColor.of("#FFD700");
	public static final ChatColor YELLOW = ChatColor.of("#FFFF00");
	public static final ChatColor BLUE = ChatColor.of("#49B3FF");
	public static final ChatColor PALEBLUE = ChatColor.of("#76B9E6");
	public static final ChatColor BRIGHTBLUE = ChatColor.of("#00E8FF");
	public static final ChatColor BRIGHTRED = ChatColor.of("#FF0000");
	public static final String MUDBROWN = ChatColor.of("#c2a389") + "";
	public static final String DARKBLUE = ChatColor.of("#007AD0") + "";
	public static String format(String before) {
		return ChatColor.translateAlternateColorCodes('&', before);
	}

}
