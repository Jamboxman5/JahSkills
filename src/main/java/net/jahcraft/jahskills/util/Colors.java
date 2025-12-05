package net.jahcraft.jahskills.util;

import org.bukkit.Material;

import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;
import net.md_5.bungee.api.ChatColor;

public class Colors {
	
	public static final ChatColor GOLD = ChatColor.of("#FFD700");
	public static final ChatColor YELLOW = ChatColor.of("#FFFF00");
	public static final ChatColor BLUE = ChatColor.of("#49B3FF");
	public static final ChatColor PALEBLUE = ChatColor.of("#76B9E6");
	public static final ChatColor BRIGHTBLUE = ChatColor.of("#00E8FF");
	public static final ChatColor BRIGHTRED = ChatColor.of("#FF0000");
	public static final ChatColor MUDBROWN = ChatColor.of("#c2a389");
	public static final ChatColor DARKBLUE = ChatColor.of("#007AD0");
	public static final ChatColor NATUREGREEN = ChatColor.of("#65e057");
	public static final ChatColor BRIGHTPURPLE = ChatColor.of("#e6008a");
	public static final ChatColor BEIGE = ChatColor.of("#fbe7c6");
	public static String format(String before) {
		return ChatColor.translateAlternateColorCodes('&', before);
	}
	public final static String getFormattedName(Material material ) {
        if ( material == null ) {
            return null;
        }
        StringBuilder friendlyName = new StringBuilder();
        for ( String word : material.name().split( "_" ) ) {
            friendlyName.append( word.substring( 0, 1 ).toUpperCase() + word.substring( 1 ).toLowerCase() + " " );
        }
        return friendlyName.toString().trim();
    }
	public final static String getFormattedName(SkillType type) {
        if ( type == null ) {
            return null;
        }
        StringBuilder friendlyName = new StringBuilder();
        for ( String word : type.name().split( "_" ) ) {
            friendlyName.append( word.substring( 0, 1 ).toUpperCase() + word.substring( 1 ).toLowerCase() + " " );
        }
        return friendlyName.toString().trim();
    }
	public final static String getFormattedName(Perk perk) {
        if ( perk == null ) {
            return null;
        }
        StringBuilder friendlyName = new StringBuilder();
        for ( String word : perk.name().split( "_" ) ) {
            friendlyName.append( word.substring( 0, 1 ).toUpperCase() + word.substring( 1 ).toLowerCase() + " " );
        }
        return friendlyName.toString().trim();
    }

}
