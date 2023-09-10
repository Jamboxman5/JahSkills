package net.jahcraft.jahskills.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class IntellectualEffects implements Listener {

static SkillType type = SkillType.INTELLECTUAL;
	
	HashMap<Entity, Player> sweetMeatsMobs = new HashMap<>();
	HashMap<Entity, Player> pigWhispererMobs = new HashMap<>();
	List<Player> excavReady = new ArrayList<>();

	//private int getRandom(int i) { return (int) (Math.random() * (i+1)); }
	void debugMSG(String s) { Bukkit.broadcastMessage(s); }
	static boolean mainSkill(Player p) { return SkillManager.getMainSkill(p) == type; }
	
	@EventHandler
	public void slaughterHouse(EntityDamageByEntityEvent e) {
		
	}
	
}
