package net.jahcraft.jahskills.util;

import org.bukkit.entity.EntityType;

public class EntityValues {
	
	public static double get(EntityType ent) {
		//TODO: COMPLETE VALUE LIST
		switch(ent) {
		case ZOMBIE:
		{
			return 10.0;
		}
		default:
			{
				return 5.0;
			}
		
		}
			
	}

}
