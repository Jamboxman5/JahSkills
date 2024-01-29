package net.jahcraft.jahskills.afflicts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.Colors;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Afflictions {
	
	public static List<Player> knockedOuts = new ArrayList<>();
	
	public static void knockOut(Player player, int durationTicks) {
		if (SkillManager.activePerk(player, Perk.UNMATCHEDWILLPOWER)) {
			int random = (int) (Math.random() * 2);
			if (random == 0) {
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("You resisted being knocked out!"));
				return;
			}
		}
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, durationTicks, 255, false, false, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, durationTicks, 255, false, false, false));
		TextComponent ko = new TextComponent("You just got knocked the fuck out!");
		ko.setColor(Colors.BRIGHTRED);
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, ko);
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

	public static void bleed(Entity entity, int bleedCount) {
		
		if (entity instanceof Player) {
			if (SkillManager.activePerk((Player)entity, Perk.UNMATCHEDWILLPOWER)) {
				int random = (int) (Math.random() * 2);
				if (random == 0) {
					((Player)entity).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("You resisted bleeding out!"));
					return;
				}
			}
		}

		
		Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new Runnable() {
		
			@Override
			public void run() {

				if (entity instanceof Player) {
					TextComponent ko = new TextComponent("You're bleeding out!");
					ko.setColor(ChatColor.RED);
					((Player)entity).spigot().sendMessage(ChatMessageType.ACTION_BAR, ko);
				}
				
				for (int i = 1; i <= bleedCount; i++) {
					try {
//						entity.sendMessage("bleed " + i);
						Thread.sleep(500);
						entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 5, new Particle.DustOptions(org.bukkit.Color.RED, i));
						entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_CALCITE_BREAK, 1, 1);

						Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
							@Override
							public void run() {
								LivingEntity ent = (LivingEntity) entity;
								ent.damage(.33);

							}
						});
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
		
	}
	
	public static void daze(Player entity, int timeMS) {
		
		if (SkillManager.activePerk(entity, Perk.UNMATCHEDWILLPOWER)) {
			int random = (int) (Math.random() * 2);
			if (random == 0) {
				entity.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("You resisted being dazed!"));
			}
		}
		
		entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 1, false, false, false));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15, 1, false, false, false));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (timeMS/25), 2, false, false, false));
		TextComponent ko = new TextComponent("You've been dazed and confused!");
		ko.setColor(ChatColor.RED);
		entity.spigot().sendMessage(ChatMessageType.ACTION_BAR, ko);
	}
}
