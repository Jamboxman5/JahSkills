package net.jahcraft.jahskills.skilltracking;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.Colors;

public class ProgressBar {
		
	public static HashMap<Player, BossBar> bars;
	
	public static BossBar createBar(Player player) {
		BossBar bar = Bukkit.createBossBar(getTitle(player), BarColor.BLUE, BarStyle.SEGMENTED_20);
		bars.put(player, bar);
		bar.addPlayer(player);
		bar.setVisible(true);
		bar.setProgress(SkillManager.getProgress(player).doubleValue());
		return bar;
	}
	
	public static void updateBar(Player player) {
		if (!bars.containsKey(player)) return;
		BossBar bar = bars.get(player);
		bar.setProgress(SkillManager.getProgress(player).doubleValue());
		bar.setTitle(getTitle(player));
	}
	
	public static String getTitle(Player player) {
		return Colors.BRIGHTBLUE + "" + SkillManager.getLevel(player);
	}
	
	public static void disposeBars() {
		for (Player p : bars.keySet()) {
			bars.get(p).removePlayer(p);
		}
	}
	
	public static void updateAll() {
		for (Player p : bars.keySet()) {
			BossBar bar = bars.get(p);
			bar.setProgress(SkillManager.getProgress(p).doubleValue());
			bar.setTitle(getTitle(p));
		}
	}


}
