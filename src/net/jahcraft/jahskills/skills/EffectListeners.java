package net.jahcraft.jahskills.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.EntityValues;
import net.jahcraft.jahskills.util.PlayerUtil;
import net.md_5.bungee.api.ChatColor;

public class EffectListeners implements Listener {
	
	Player spwAttacker;
	Player hitAttacker;
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
	@EventHandler(priority = EventPriority.LOW)
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
	@EventHandler
	public void killingBlow(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		Player p = (Player) e.getDamager();
		String type = p.getInventory().getItemInMainHand().getType().toString();
		if (!type.contains("AXE") && !type.contains("SWORD")) return;
//		p.sendMessage(SkillManager.activePerk(p, Perk.KILLINGBLOW) + "");
		if (!SkillManager.activePerk(p, Perk.KILLINGBLOW)) return;
//		p.sendMessage(!(e.getEntity() instanceof LivingEntity) + "");
		if (!(e.getEntity() instanceof LivingEntity)) return;
		LivingEntity ent = (LivingEntity) e.getEntity();
//		p.sendMessage("" + (ent.getHealth() >= ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2.0));
		if (ent.getHealth() >= ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2.0) return;
		double roll = Math.random()*100.0;
//		p.sendMessage(roll + "");
		double barrier = 100.0 - SkillManager.getLevel(p, SkillType.BUTCHER)/2.0;
//		p.sendMessage(barrier + "");
		if (roll <= barrier) return;
		
		//Actual effect
		p.sendMessage("Dealt a killing blow!");
		ent.getLocation().getWorld().createExplosion(ent.getLocation(), .1F);
		ent.setHealth(0);
		
	}
	@EventHandler
	public void hitmanAttack(EntityDamageByEntityEvent e) {
//		e.getDamager().sendMessage((!(e.getDamager() instanceof Player)) + "");
		if (!(e.getDamager() instanceof Player)) return;
//		e.getDamager().sendMessage((!(e.getEntity() instanceof Player)) + "");
		if (!(e.getEntity() instanceof Player)) return;
//		e.getDamager().sendMessage((!SkillManager.activePerk((Player)e.getDamager(), Perk.HITMAN)) + "");
		if (!SkillManager.activePerk((Player)e.getDamager(), Perk.HITMAN)) {
			hitAttacker = null; 
			return;
		} else {
			hitAttacker = (Player) e.getDamager();
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void hitmanDeath(EntityDeathEvent e) {
//		e.getEntity().sendMessage((hitAttacker == null) + "");
		if (hitAttacker == null) return;
//		e.getEntity().sendMessage((!(e.getEntity() instanceof Player)) + "");
		if (!(e.getEntity() instanceof Player)) return;
//		e.getEntity().sendMessage((!SkillManager.activePerk(hitAttacker, Perk.HITMAN)) + "");
		if (!SkillManager.activePerk(hitAttacker, Perk.HITMAN)) return;
		e.setDroppedExp(e.getDroppedExp()*2);
		
		int chance = 0;
		
		ItemStack mainHand = hitAttacker.getInventory().getItemInMainHand();
		if (mainHand.getType().toString().contains("SWORD")) chance = 1;
		if (mainHand.getType().toString().contains("AXE")) chance = 2;
		
		int lootingLevel = 0;
		if (mainHand.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
			lootingLevel = mainHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
		}
		
		int roll = (int) (Math.random()*101);
		roll += (lootingLevel * 10);
		
		if (chance == 1) {
			if (roll > 75) e.getDrops().add(PlayerUtil.getSkull((Player)e.getEntity()));
		}
		if (chance == 2) {
			if (roll > 50) e.getDrops().add(PlayerUtil.getSkull((Player)e.getEntity()));
		}
		
	}
	@EventHandler
	public void pummeler(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		if (!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getDamager();
		if (p.getInventory().getItemInMainHand().getType() != Material.AIR) return;
//		p.sendMessage(SkillManager.activePerk(p, Perk.KILLINGBLOW) + "");
		if (!SkillManager.activePerk(p, Perk.THEPUMMELER)) return;
//		p.sendMessage(!(e.getEntity() instanceof LivingEntity) + "");
		double roll = Math.random()*100.0;
//		p.sendMessage(roll + "");
		double barrier = 100.0 - SkillManager.getLevel(p, SkillType.BUTCHER)/5;
//		p.sendMessage(barrier + "");
		if (roll <= barrier) return;
		
		int multiplier = SkillManager.getLevel(p, SkillType.BUTCHER)/5;
		int baseTicks = 10;
		
		//Actual effect
		p.sendMessage("KO!");
		Effects.knockOut((Player)e.getEntity(), baseTicks*multiplier);

	}
}
 