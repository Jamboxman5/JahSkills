package net.jahcraft.jahskills.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.EntityValues;
import net.md_5.bungee.api.ChatColor;

public class EffectListeners implements Listener {
	
	Player spwAttacker;
	
	@EventHandler
	public void bloodMoney(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		boolean dead = (((LivingEntity) e.getEntity()).getHealth() - e.getDamage()) <= 0;
		if (!dead) return;
		Player player = (Player) e.getDamager();
		if (!SkillManager.activePerk(player, Perk.BLOODMONEY)) return;
		
		double baseValue = EntityValues.get(e.getEntityType());
		if (baseValue <= 0) return;
		
		double multiplier = 1.0 + SkillManager.getLevel(player, SkillType.BUTCHER)/100.0;
		double finalValue = baseValue * multiplier;
		
		
		Main.eco.depositPlayer(player, finalValue);
		player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "+ $" + String.format("%,.2f", finalValue));
		
	}
	@EventHandler
	public void spoilsOfWarAttack(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		if (e.getEntity() instanceof Player) return;
		if (!SkillManager.activePerk((Player)e.getDamager(), Perk.SPOILSOFWAR)) {
			spwAttacker = null; 
			return;
		} else {
			spwAttacker = (Player) e.getDamager();
		}
	}
	@EventHandler
	public void spoilsOfWarDeath(EntityDeathEvent e) {
		System.out.println((spwAttacker == null));
		if (spwAttacker == null) return;
		System.out.println((!SkillManager.activePerk(spwAttacker, Perk.SPOILSOFWAR)));
		if (!SkillManager.activePerk(spwAttacker, Perk.SPOILSOFWAR)) return;
		System.out.println((e.getDrops() == null));
		if (e.getDrops() == null) return;
		System.out.println((e.getDrops().size() == 0));
		if (e.getDrops().size() == 0) return;
		System.out.println("Adding drops");
		for (ItemStack i : e.getDrops()) {
			i.setAmount(i.getAmount()*2);
		}
		
	}
}
