package net.jahcraft.jahskills.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Breedable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;

public class HarvesterEffects implements Listener {

static SkillType type = SkillType.HARVESTER;
	
	HashMap<Entity, Player> sweetMeatsMobs = new HashMap<>();
	HashMap<Entity, Player> pigWhispererMobs = new HashMap<>();
	List<Player> excavReady = new ArrayList<>();

	//private int getRandom(int i) { return (int) (Math.random() * (i+1)); }
	void debugMSG(String s) { Bukkit.broadcastMessage(s); }
	static boolean mainSkill(Player p) { return SkillManager.getMainSkill(p) == type; }
	
	@EventHandler
	public void agriculturalStudies(BlockBreakEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.AGRICULTURALSTUDIES)) return;
		if (!(e.getBlock().getBlockData() instanceof Ageable)) return;
		Ageable data = (Ageable) e.getBlock().getBlockData();
		if (data.getAge() != data.getMaximumAge()) return;
		e.setExpToDrop((int) (.25 * SkillManager.getLevel(e.getPlayer(), type)));
	}
	@EventHandler
	public void birdsAndBees(EntityBreedEvent e) {
		if (e.getBreeder() == null) return;
		if (!(e.getBreeder() instanceof Player)) return;
		if (!SkillManager.activePerk((Player)e.getBreeder(), Perk.THEBIRDSANDTHEBEES)) return;
		e.setExperience((int) (e.getExperience() + (.25 * SkillManager.getLevel((Player)e.getBreeder(), type))));
	}	
	@EventHandler
	public void fertilityTreatment(EntityBreedEvent e) {
		if (e.getBreeder() == null) return;
		if (!(e.getBreeder() instanceof Player)) return;
		if (!SkillManager.activePerk((Player)e.getBreeder(), Perk.FERTILITYTREATMENT)) return;
		
		
		if (Math.random() > .5) {
			Entity newBaby = e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), e.getEntityType());
			if (!(newBaby instanceof org.bukkit.entity.Breedable)) return;
			Breedable ageBaby = (Breedable) newBaby;
			ageBaby.setBaby();
		}
		if (mainSkill((Player)e.getBreeder()) && Math.random() < .15) {
			Entity newBaby = e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), e.getEntityType());
			if (!(newBaby instanceof Breedable)) return;
			Breedable ageBaby = (Breedable) newBaby;
			ageBaby.setBaby();
		}
	}
	@EventHandler
	public void harshParenting(PlayerInteractEntityEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.HARSHPARENTING)) return;
		if (e.getRightClicked() == null) return;
		if (!(e.getRightClicked() instanceof Breedable)) return;
		if (!e.getPlayer().isSneaking()) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
		
		Breedable ageEntity = (Breedable) e.getRightClicked();
		
		if (ageEntity.canBreed()) return;
		
		if (ageEntity.isAdult()) {
			if (!mainSkill(e.getPlayer())) return;
			ageEntity.setBreed(true);
			Location local = ageEntity.getLocation();
			ageEntity.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, local.getX(), local.getY()+.5, local.getZ(), 10, .5, .5, .5);
			return;
		}
		
		int ageToAdd = (80 * SkillManager.getLevel(e.getPlayer(), type));
		if (mainSkill(e.getPlayer())) ageToAdd *= 1.5;
		ageEntity.setAge(ageEntity.getAge() + ageToAdd);
		Location local = ageEntity.getLocation();
		ageEntity.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, local.getX(), local.getY()+.5, local.getZ(), 4, .5, .5, .5);	
	}
	@EventHandler
	public void replanter(BlockBreakEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.REPLANTER)) return;
		if (!(e.getBlock().getBlockData() instanceof Ageable)) return;
		if (!e.getPlayer().getInventory().getItemInMainHand().getType().toString().contains("_HOE")) return;
		
		e.setDropItems(false);
		replant(e.getPlayer(), e.getBlock());
		
	}
	@EventHandler
	public void greenThumb(BlockBreakEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.GREENTHUMB)) return;
		if (e.getBlock().getBlockData() instanceof Ageable) {
			
			Ageable data = (Ageable) e.getBlock().getBlockData();
			if (data.getAge() != data.getMaximumAge()) return;
			if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;

			e.setDropItems(false);
			List<ItemStack> drops = new ArrayList<>();
			
			
			
			for (ItemStack i : e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand())) {
				double chance = (SkillManager.getLevel(e.getPlayer(), type)/100.0) * 5.0;
				if (chance > Math.random()) {
					int ogAmount = i.getAmount();
					i.setAmount(ogAmount * 2);
					if (mainSkill(e.getPlayer())) {
						if (chance > Math.random()) {
							i.setAmount(ogAmount * 3);
						}
					}
				}
				drops.add(i);
			}
			
			for (ItemStack i : drops) {
				e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), i);
			}
			
		} else {
			if (e.getBlock().getType() != Material.MELON && e.getBlock().getType() != Material.PUMPKIN) return;
			if (!SkillDatabase.isNatural(e.getBlock().getLocation())) return;

			e.setDropItems(false);
			List<ItemStack> drops = new ArrayList<>();
						
			for (ItemStack i : e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand())) {
				double chance = (SkillManager.getLevel(e.getPlayer(), type)/100.0) * 5.0;
				if (chance > Math.random()) {
					int ogAmount = i.getAmount();
					i.setAmount(ogAmount * 2);
					if (mainSkill(e.getPlayer())) {
						if (chance > Math.random()) {
							i.setAmount(ogAmount * 3);
						}
					}
				}
				drops.add(i);
			}
			
			for (ItemStack i : drops) {
				e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), i);
			}
		}
		
	}
	@EventHandler
	public void FreakishFarming(BlockBreakEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.FREAKISHFARMING)) return;
		if (!(e.getBlock().getBlockData() instanceof Ageable)) return;
		Ageable data = (Ageable) e.getBlock().getBlockData();
		if (data.getAge() != data.getMaximumAge()) return;

		if (e.getPlayer().isSneaking()) {
			e.setCancelled(true);
			new BukkitRunnable() {

				@Override
				public void run() {
					veinMine(e.getBlock(), e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
					
				}
				
			}.runTaskAsynchronously(Main.plugin);
		}
		
	}
	@EventHandler
	public void fertilization(PlayerInteractEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.FERTILIZATION)) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!(e.getClickedBlock().getBlockData() instanceof Ageable)) return;
		if (!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BONE_MEAL)) return;
		
		Ageable data = (Ageable) e.getClickedBlock().getBlockData();
		
		if (data.getAge() >= data.getMaximumAge()) return;

		Location local = e.getClickedBlock().getLocation();
		e.getClickedBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, local.getX()+.5, local.getY()+.5, local.getZ()+.5, 10, .5, .5, .5);
		data.setAge(data.getMaximumAge());
		e.getClickedBlock().setBlockData(data);
		ItemStack bmeal = e.getPlayer().getInventory().getItemInMainHand();
		bmeal.setAmount(bmeal.getAmount()-1);
		
		if (!mainSkill(e.getPlayer())) return;
		Block clicked = e.getClickedBlock();
		BlockFace[] adjacents = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
		BlockFace[] corners = {BlockFace.NORTH_WEST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.SOUTH_EAST};
		
		for (BlockFace b : adjacents) {
			if (clicked.getRelative(b).getBlockData() instanceof Ageable) {
				clicked.getRelative(b).applyBoneMeal(BlockFace.UP);
				clicked.getRelative(b).applyBoneMeal(BlockFace.UP);
			}
		}
		for (BlockFace b : corners) {
			if (clicked.getRelative(b).getBlockData() instanceof Ageable) {
				clicked.getRelative(b).applyBoneMeal(BlockFace.UP);
			}
		}
		
	}
	@EventHandler
	public void farmingFrenzy(BlockBreakEvent e) {
		if (e.getPlayer() == null) return;
		if (!SkillManager.activePerk(e.getPlayer(), Perk.FARMINGFRENZY)) return;
		if (!(e.getBlock().getBlockData() instanceof Ageable)) return;
		if (!e.getPlayer().getInventory().getItemInMainHand().getType().toString().contains("_HOE")) return;
		
		e.setDropItems(false);
		
		boolean replanted = false;
		boolean adjacentsReplanted = false;
		
		Block clicked = e.getBlock();
		Ageable clickedData = (Ageable) clicked.getBlockData();
		if (clickedData.getAge() >= clickedData.getMaximumAge()) {
			replant(e.getPlayer(), clicked);
			replanted = true;
		}
		
		BlockFace[] adjacents = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
		BlockFace[] corners = {BlockFace.NORTH_WEST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.SOUTH_EAST};
		
		for (BlockFace b : adjacents) {
			if (clicked.getRelative(b).getBlockData() instanceof Ageable) {
				Ageable data = (Ageable) clicked.getRelative(b).getBlockData();
				if (data.getAge() >= clickedData.getMaximumAge()) {
					replant(e.getPlayer(), clicked.getRelative(b));
					adjacentsReplanted = true;
				}
			}
		}
		for (BlockFace b : corners) {
			if (clicked.getRelative(b).getBlockData() instanceof Ageable) {
				Ageable data = (Ageable) clicked.getRelative(b).getBlockData();
				if (data.getAge() >= clickedData.getMaximumAge()) {
					replant(e.getPlayer(), clicked.getRelative(b));
					adjacentsReplanted = true;
				}
			}
		}
		if (!replanted && adjacentsReplanted) e.setCancelled(true);
	}
	
	private void replant(Player p, Block b) {
		Material seeds = null;
		Material oldCrop = b.getType();
				
		for (ItemStack i : b.getDrops()) {
			if (i.getType().toString().contains("SEEDS")) seeds = i.getType();
			if (i.getType().toString().contains("CARROT")) seeds = i.getType();
			if (i.getType().toString().contains("COCOA")) seeds = i.getType();
			if (i.getType().toString().equals("POTATO")) seeds = i.getType();
		}
		if (seeds == null) return;
		final Material finalSeeds = seeds;
		boolean hasSeeds = p.getInventory().contains(seeds);
		
		if (hasSeeds) {
			
			new BukkitRunnable() {

				@Override
				public void run() {
					p.getInventory().removeItem(new ItemStack(finalSeeds));
					for (ItemStack i : b.getDrops()) {
						b.getWorld().dropItemNaturally(b.getLocation(), i);
					}
					b.setType(oldCrop);
					
//					e.getBlock().setBlockData(data);					
				}
				
			}.runTaskLater(Main.plugin, 2);
			
		}
	}
	private void veinMine(Block b, Player p, ItemStack tool) {
		if (b.getBlockData() instanceof Ageable) {

			new BukkitRunnable() {
				@Override
				public void run() {
					b.breakNaturally(tool);
				}
			}.runTask(Main.plugin);
			try {
//				if (b.getRelative(BlockFace.UP).getType() == m) veinMine(b.getRelative(BlockFace.UP), xp, m, p, tool);
//				if (b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType() == m) veinMine(b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH), xp, m, p, tool);
//				if (b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType() == m) veinMine(b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH), xp, m, p, tool);
//				if (b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType() == m) veinMine(b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST), xp, m, p, tool);
//				if (b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType() == m) veinMine(b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST), xp, m, p, tool);
//				if (b.getRelative(BlockFace.DOWN).getType() == m) veinMine(b.getRelative(BlockFace.DOWN), xp, m, p, tool);
				if (b.getRelative(BlockFace.NORTH).getBlockData() instanceof Ageable && ((Ageable)b.getRelative(BlockFace.NORTH).getBlockData()).getAge() == ((Ageable)b.getRelative(BlockFace.NORTH).getBlockData()).getMaximumAge()) veinMine(b.getRelative(BlockFace.NORTH), p, tool);
				if (b.getRelative(BlockFace.SOUTH).getBlockData() instanceof Ageable && ((Ageable)b.getRelative(BlockFace.SOUTH).getBlockData()).getAge() == ((Ageable)b.getRelative(BlockFace.SOUTH).getBlockData()).getMaximumAge()) veinMine(b.getRelative(BlockFace.SOUTH), p, tool);
				if (b.getRelative(BlockFace.EAST).getBlockData() instanceof Ageable && ((Ageable)b.getRelative(BlockFace.EAST).getBlockData()).getAge() == ((Ageable)b.getRelative(BlockFace.EAST).getBlockData()).getMaximumAge()) veinMine(b.getRelative(BlockFace.EAST), p, tool);
				if (b.getRelative(BlockFace.WEST).getBlockData() instanceof Ageable && ((Ageable)b.getRelative(BlockFace.WEST).getBlockData()).getAge() == ((Ageable)b.getRelative(BlockFace.WEST).getBlockData()).getMaximumAge()) veinMine(b.getRelative(BlockFace.WEST), p, tool);
//				if (b.getRelative(BlockFace.SOUTH).getType() == m) veinMine(b.getRelative(BlockFace.SOUTH), xp, m, p, tool);
//				if (b.getRelative(BlockFace.EAST).getType() == m) veinMine(b.getRelative(BlockFace.EAST), xp, m, p, tool);
//				if (b.getRelative(BlockFace.WEST).getType() == m) veinMine(b.getRelative(BlockFace.WEST), xp, m, p, tool);
			} catch(Exception e) {
				return;
			}

		}
	}
}
