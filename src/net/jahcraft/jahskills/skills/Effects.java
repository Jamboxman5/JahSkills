package net.jahcraft.jahskills.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.util.Colors;

public class Effects {
	
	public static List<Player> knockedOuts = new ArrayList<>();
	
	public static void knockOut(Player player, int durationTicks) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, durationTicks, 255, false, false, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, durationTicks, 255, false, false, false));
		player.sendMessage(Colors.BRIGHTRED + "You just got knocked the fuck out!");
		knockedOuts.add(player);
		Location loc = player.getLocation();
		loc.setPitch(90);
		player.teleport(loc);
		loc.add(0,1,0);
		BlockData above = loc.getBlock().getBlockData();
	    BlockData barrier = Bukkit.createBlockData("minecraft:barrier");
		player.sendBlockChange(loc, barrier);
		player.setSwimming(true);

		Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new Runnable() {

			@Override
			public void run() {
								
				try {
					Thread.sleep(durationTicks*40);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				knockedOuts.remove(player);
				player.sendBlockChange(loc, above);

				
			}
			
		});
	}

	public static boolean knockedOut(Player player) {
		return knockedOuts.contains(player);
	}

}
