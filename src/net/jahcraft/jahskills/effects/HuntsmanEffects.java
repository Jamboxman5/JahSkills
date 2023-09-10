package net.jahcraft.jahskills.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class HuntsmanEffects implements Listener {

	static SkillType type = SkillType.HUNTSMAN;
	
	HashMap<Entity, Player> sweetMeatsMobs = new HashMap<>();
	HashMap<Entity, Player> pigWhispererMobs = new HashMap<>();
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
