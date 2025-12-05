package net.jahcraft.jahskills.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class PlayerUtil {
	
	public static ItemStack getSkull(Player p) {
		ItemStack i = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) i.getItemMeta();
		
		meta.setOwningPlayer(p);
		meta.setDisplayName(ChatColor.RED + p.getName() + "'s skull");
		i.setItemMeta(meta);
		return i;
	}
	public static boolean isCriticalHit(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return false;
		
		Player damager = (Player) e.getDamager();
		
		boolean inLiquid = isInLiquid(damager);
		boolean onGround = isOnGround(damager);
		boolean falling = isFalling(damager);
		boolean blind = hasEffect(damager, PotionEffectType.BLINDNESS);
		boolean inVehicle = isInVehicle(damager);
		boolean sprinting = isSprinting(damager);
		boolean climbing = isClimbing(damager);
		
		return (!inLiquid && !onGround && falling && !blind && !inVehicle && !sprinting && !climbing);
	}
	private static boolean isClimbing(Player player) {
		Location loc = player.getLocation();
		Block block = loc.getBlock();
		if (block.getType() == Material.LADDER ||
				block.getType() == Material.SCAFFOLDING ||
				block.getType().toString().contains("VINE")) return true;
		return false;
	}
	public static boolean isInLiquid(Entity player) {
		Location loc = player.getLocation();
		Block block = loc.getBlock();
		return (block.isLiquid());
	}
	public static boolean isOnGround(Entity player) {
		return (player.isOnGround());
	}
	public static boolean isFalling(Entity player) {
		return (player.getFallDistance() > 0);
	}
	public static boolean hasEffect(Player player, PotionEffectType type) {
		if (player.getActivePotionEffects().size() == 0) return false;
		for (PotionEffect effect : player.getActivePotionEffects()) {
			if (effect.getType() == type) return true;
		}
		return false;
	}
	public static boolean isInVehicle(Entity player) {
		return (player.getVehicle() != null);
	}
	public static boolean isSprinting(Player player) {
		return player.isSprinting();
	}
	public static boolean isHoldingShovel(Player player) {
		return player.getInventory().getItemInMainHand().getType().toString().contains("SHOVEL");
	}
	public static boolean isHitSufficient(EntityDamageByEntityEvent e) {
		return (e.getDamage() >= 5.5);
	}
}
