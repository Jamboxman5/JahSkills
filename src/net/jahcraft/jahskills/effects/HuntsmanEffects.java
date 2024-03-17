package net.jahcraft.jahskills.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class HuntsmanEffects implements Listener {

	static SkillType type = SkillType.HUNTSMAN;
	
	HashMap<Entity, Player> sweetMeatsMobs = new HashMap<>();
	HashMap<Entity, Player> pigWhispererMobs = new HashMap<>();

	HashMap<Player, Long> barehandedCooldown = new HashMap<>();
	HashMap<Player, Long> bomberCooldown = new HashMap<>();
	
	List<Player> excavReady = new ArrayList<>();

	//private int getRandom(int i) { return (int) (Math.random() * (i+1)); }
	void debugMSG(String s) { Bukkit.broadcastMessage(s); }
	static boolean mainSkill(Player p) { return SkillManager.getMainSkill(p) == type; }
	
	@EventHandler
	public void slaughterHouse(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getDamager(), Perk.SLAUGHTERHOUSE)) return;
		if (!getSlaugherHouseMobs().contains(e.getEntityType())) return;
		e.setDamage(SkillManager.getLevel((Player)e.getDamager(), type));
	}
	
	@EventHandler
	public void sweetMeatsAttack(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getDamager(), Perk.SWEETMEATS)) return;
		if (!getSlaugherHouseMobs().contains(e.getEntityType())) return;
		sweetMeatsMobs.put(e.getEntity(), (Player) e.getDamager());
	}
	
	@EventHandler
	public void sweetMeatsKill(EntityDeathEvent e) {
		if (e.getEntity() == null) return;
		if (!sweetMeatsMobs.containsKey(e.getEntity())) return;
		if (!SkillManager.activePerk(sweetMeatsMobs.get(e.getEntity()), Perk.SWEETMEATS)) return;
		
		for (ItemStack i : e.getDrops()) {
			if (i.getType().toString().contains("BEEF") ||
				i.getType().toString().contains("PORKCHOP") ||
				i.getType().toString().contains("MUTTON") ||
				i.getType().toString().contains("RABBIT") ||
				i.getType().toString().contains("CHICKEN")) {
				double multiplier = 2;
				if (mainSkill(sweetMeatsMobs.get(e.getEntity()))) multiplier *= 1.5;
				i.setAmount((int) (i.getAmount()*multiplier));
			}
			
		}
		
	}
	@EventHandler
	public void pigWhispererAttack(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		if (!SkillManager.activePerk((Player) e.getDamager(), Perk.PIGWHISPERER)) return;
		if (!getSlaugherHouseMobs().contains(e.getEntityType())) return;
		pigWhispererMobs.put(e.getEntity(), (Player) e.getDamager());
	}
	
	@EventHandler
	public void pigWhispererKill(EntityDeathEvent e) {
		if (e.getEntity() == null) return;
		if (!pigWhispererMobs.containsKey(e.getEntity())) return;
		if (!SkillManager.activePerk(pigWhispererMobs.get(e.getEntity()), Perk.PIGWHISPERER)) return;
		
		double multiplier = 2.0;
		if (mainSkill(pigWhispererMobs.get(e.getEntity()))) multiplier *= 1.5;
		e.setDroppedExp((int) (e.getDroppedExp()*multiplier));

		pigWhispererMobs.remove(e.getEntity());
		
	}
	
	@EventHandler
	public void chrisKyle(ProjectileHitEvent e) {
	}
	
	@EventHandler
	public void bareHandedArchery(PlayerInteractEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR &&
			e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (e.getPlayer().getInventory().getItemInMainHand() == null) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.ARROW &&
			e.getPlayer().getInventory().getItemInMainHand().getType() != Material.SPECTRAL_ARROW &&
			e.getPlayer().getInventory().getItemInMainHand().getType() != Material.TIPPED_ARROW) return;
		
		if (!SkillManager.activePerk(e.getPlayer(), Perk.BAREHANDEDARCHERY)) return;
				
		Player shooter = e.getPlayer();
		ItemStack handArrow = shooter.getInventory().getItemInMainHand();
		
		int cooldownMS = 500;
		
		if (mainSkill(shooter)) {
			cooldownMS /= 2;
		}
		if (barehandedCooldown.containsKey(shooter)) {
			if (System.currentTimeMillis() - barehandedCooldown.get(shooter) < cooldownMS) return;
		}

		barehandedCooldown.put(shooter, System.currentTimeMillis());
		
		handArrow.setAmount(handArrow.getAmount()-1);
		
		Arrow arrow = shooter.launchProjectile(Arrow.class);
		arrow.setPickupStatus(AbstractArrow.PickupStatus.ALLOWED);
		if (mainSkill(shooter)) arrow.setCritical(true);
		
		if (handArrow.hasItemMeta()) {
			PotionMeta meta = (PotionMeta) handArrow.getItemMeta();
			arrow.setBasePotionData(meta.getBasePotionData());
		}
		
	}
	
	@EventHandler
	public void bombThrower(PlayerInteractEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR) return;
		if (e.getPlayer().getInventory().getItemInMainHand() == null) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.FIRE_CHARGE) return;
		
		if (!SkillManager.activePerk(e.getPlayer(), Perk.BOMBTHROWER)) return;
				
		Player shooter = e.getPlayer();
		ItemStack fireCharge = shooter.getInventory().getItemInMainHand();
		
		int cooldownMS = 2000;
		
		if (mainSkill(shooter)) {
			cooldownMS /= 2;
		}
		if (bomberCooldown.containsKey(shooter)) {
			if (System.currentTimeMillis() - bomberCooldown.get(shooter) < cooldownMS) return;
		}

		bomberCooldown.put(shooter, System.currentTimeMillis());
		
		fireCharge.setAmount(fireCharge.getAmount()-1);
		
		Fireball fireball = shooter.launchProjectile(Fireball.class);
		if (mainSkill(shooter)) {
			new BukkitRunnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(100);
						Vector velocity = fireball.getVelocity();
//						Bukkit.broadcastMessage(velocity.getX() + ", " + velocity.getY() + ", " + velocity.getZ());
						fireball.setVelocity(velocity.multiply(2.2));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.runTaskAsynchronously(Main.plugin);
			
		}
		
	}
	
	private List<EntityType> getSlaugherHouseMobs() {
		List<EntityType> types = new ArrayList<>();
		types.add(EntityType.PIG);
		types.add(EntityType.COW);
		types.add(EntityType.SHEEP);
		types.add(EntityType.RABBIT);
		types.add(EntityType.HORSE);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.DONKEY);
		types.add(EntityType.MULE);
		types.add(EntityType.MUSHROOM_COW);
		types.add(EntityType.HOGLIN);
		types.add(EntityType.GOAT);
		types.add(EntityType.LLAMA);
		types.add(EntityType.PIGLIN);
		types.add(EntityType.ZOGLIN);
		return types;
	}
	
}
