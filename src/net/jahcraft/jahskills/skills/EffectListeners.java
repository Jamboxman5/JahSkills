package net.jahcraft.jahskills.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
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
	List<Player> selfDefenseQueue = new ArrayList<>();
	HashMap<Player, Long> selfDefenseCooldown = new HashMap<>();
	HashMap<Player, Long> selfDefenseHitTime = new HashMap<>();
	
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
		if (spwAttacker == null) return;
		if (!SkillManager.activePerk(spwAttacker, Perk.SPOILSOFWAR)) return;
		if (e.getDrops() == null) return;
		if (e.getDrops().size() == 0) return;
		for (ItemStack i : e.getDrops()) {
			i.setAmount(i.getAmount()*2);
		}
		
	}
	@EventHandler
	public void selfDefenseInitial(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if (!SkillManager.activePerk(p, Perk.SELFDEFENSE)) return;
		if (selfDefenseCooldown.containsKey(p)) {
			if (System.currentTimeMillis() - selfDefenseCooldown.get(p) <= 3000) return;
			selfDefenseCooldown.remove(p);
//			p.sendMessage("cooldown removed");
		}
		selfDefenseHitTime.put(p, System.currentTimeMillis());
		if (!selfDefenseQueue.contains(p)) {
			selfDefenseQueue.add(p);
//			p.sendMessage("added to queue");
		}
		Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					if (System.currentTimeMillis() - selfDefenseHitTime.get(p) > 3000) {
						if (selfDefenseQueue.contains(p)) {
//							p.sendMessage("removed from queue");
							selfDefenseQueue.remove(p);
						}
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	@EventHandler
	public void selfDefenseRetaliation(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		Player p = (Player) e.getDamager();
		if (!SkillManager.activePerk(p, Perk.SELFDEFENSE)) return;
//		p.sendMessage("damage added");
		selfDefenseCooldown.put(p, System.currentTimeMillis());
		if (!selfDefenseQueue.contains(p)) return;
		e.setDamage(e.getDamage()*1.5);

//		p.sendMessage("cooldown added");
		selfDefenseQueue.remove(p);
//		p.sendMessage("removed from queue");
	}
}
