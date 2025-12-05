package net.jahcraft.jahskills.util;

import org.bukkit.entity.EntityType;

public class EntityValues {
	
	public static double get(EntityType ent) {
		//TODO: COMPLETE VALUE LIST
		switch(ent) {
		case ZOMBIE:
			return 5.0;
		case BLAZE:
			return 15.0;
		case SKELETON:
			return 7.5;
		case WITHER_SKELETON:
			return 25.0;
		case ENDERMAN:
			return 15.0;
		case SPIDER:
			return 5.0;
		case PHANTOM:
			return 50.0;
		default:
			return 5.0;
		}
	}

}
