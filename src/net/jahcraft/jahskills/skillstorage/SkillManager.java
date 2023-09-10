package net.jahcraft.jahskills.skillstorage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.perks.Perks;
import net.jahcraft.jahskills.skills.Butcher;
import net.jahcraft.jahskills.skills.Caveman;
import net.jahcraft.jahskills.skills.Explorer;
import net.jahcraft.jahskills.skills.Harvester;
import net.jahcraft.jahskills.skills.Huntsman;
import net.jahcraft.jahskills.skills.Intellectual;
import net.jahcraft.jahskills.skills.Naturalist;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skills.Survivalist;
import net.jahcraft.jahskills.skilltracking.ProgressBar;
import net.jahcraft.jahskills.util.Colors;

public class SkillManager {
	
	public static int getLevel(Player player) {
		return SkillDatabase.skillLevel.get(player);
	}
	public static int getLevel(Player player, SkillType skill) {
		if (SkillDatabase.getSkill(skill).get(player) == null) {
			SkillDatabase.getSkill(skill).put(player, 0);
		}
		return SkillDatabase.getSkill(skill).get(player);
	}
	public static void levelUp(Player p) {
		int skillLevel = SkillDatabase.skillLevel.get(p) + 1;
		int skillPoints = SkillDatabase.skillPoints.get(p);
		int pointsAdded = 1;
		if (skillLevel % 5 == 0) {
			pointsAdded = 3;
		}
		int newPoints = skillPoints + pointsAdded;
		SkillDatabase.skillLevel.put(p, skillLevel);
		SkillDatabase.skillPoints.put(p, newPoints);
		p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, .4f, .4f);
		if (pointsAdded > 1) {
			p.sendMessage(Colors.GOLD + "Level up!" + Colors.BRIGHTBLUE + " » " + Colors.BLUE + "You gained " + Colors.BRIGHTBLUE + pointsAdded + Colors.BLUE + " skill points!" + Colors.BRIGHTBLUE + " (Total: " + newPoints + ")");
		} else {
			p.sendMessage(Colors.GOLD + "Level up!" + Colors.BRIGHTBLUE + " » " + Colors.BLUE + "You gained " + Colors.BRIGHTBLUE + pointsAdded + Colors.BLUE + " skill point!" + Colors.BRIGHTBLUE + " (Total: " + newPoints + ")");
		}
	}
	public static BigDecimal getProgress(Player player) {
		return SkillDatabase.skillProgress.get(player);
	}
	public static void addProgress(Player player, BigDecimal toAdd) {
		BigDecimal newProgress = SkillDatabase.skillProgress.get(player).add(toAdd);
		while (newProgress.doubleValue() >= 1) {
			levelUp(player);
			newProgress = newProgress.subtract(BigDecimal.valueOf(1));
		}
		SkillDatabase.skillProgress.put(player, newProgress);
		ProgressBar.updateBar(player);
		
	}
	public static boolean hasPoints(Player player) {
		return (SkillDatabase.skillPoints.get(player) > 0);
	}
	public static int getPoints(Player player) {
		return SkillDatabase.skillPoints.get(player);
	}
	public static void setPoints(Player player, int points) {
		SkillDatabase.skillPoints.put(player, points);
	}
	public static void addPoints(Player player, int points) {
		int ptsBefore = SkillDatabase.skillPoints.get(player);
		SkillDatabase.skillPoints.put(player, points + ptsBefore);
	}
	public static void loadSkills() {
		
		setupSkills();
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			SkillDatabase.load(p);
			ProgressBar.createBar(p);
		}
	}
	private static void setupSkills() {
		ProgressBar.bars = new HashMap<>();
		SkillDatabase.mainSkills = new HashMap<>();
		SkillDatabase.ownedPerks = new HashMap<>();
		SkillDatabase.activePerks = new HashMap<>();
		SkillDatabase.skillLevel = new HashMap<>();
		SkillDatabase.skillProgress = new HashMap<>();
		SkillDatabase.skillPoints = new HashMap<>();
		SkillDatabase.butcherLevel = new HashMap<>();
		SkillDatabase.cavemanLevel = new HashMap<>();
		SkillDatabase.explorerLevel = new HashMap<>();
		SkillDatabase.harvesterLevel = new HashMap<>();
		SkillDatabase.huntsmanLevel = new HashMap<>();
		SkillDatabase.intellectualLevel = new HashMap<>();
		SkillDatabase.naturalistLevel = new HashMap<>();
		SkillDatabase.survivalistLevel = new HashMap<>();
	}
	public static void reset(Player target) {
		SkillDatabase.mainSkills.put(target, null);
		SkillDatabase.ownedPerks.put(target, new ArrayList<>());
		SkillDatabase.activePerks.put(target, new ArrayList<>());
		SkillDatabase.skillLevel.put(target, 1);
		SkillDatabase.skillProgress.put(target, BigDecimal.valueOf(0));
		SkillDatabase.skillPoints.put(target, 0);
		SkillDatabase.butcherLevel.put(target, 0);
		SkillDatabase.cavemanLevel.put(target, 0);
		SkillDatabase.explorerLevel.put(target, 0);
		SkillDatabase.harvesterLevel.put(target, 0);
		SkillDatabase.huntsmanLevel.put(target, 0);
		SkillDatabase.intellectualLevel.put(target, 0);
		SkillDatabase.naturalistLevel.put(target, 0);
		SkillDatabase.survivalistLevel.put(target, 0);
		ProgressBar.updateBar(target);
	}
	public static int getAvailablePerks(Player player, SkillType type) {
		int available = 0;
		for (Perk perk : getPerks(type)) {
			if (isPerkAvailable(perk, player, type) && !hasPerk(player, perk)) available++;
		}
		return available;
	}
	public static boolean ownsAllPerks(Player player, SkillType type) {
		for (Perk p : getPerks(type)) {
			if (!hasPerk(player, p)) return false;
		}
		return true;
	}
	private static boolean isPerkAvailable(Perk perk, Player player, SkillType type) {
		if (getLevel(player, type) >= getRequiredAttributeLevel(perk) &&
			getPoints(player) >= getRequiredPoints(perk)) return true;
		return false;
	}
	private static int getRequiredPoints(Perk perk) {
		return Perks.getPointCost(perk);
	}
	private static int getRequiredAttributeLevel(Perk perk) {
		return Perks.getLevelRequirement(perk);
	}
	public static Perk[] getPerks(SkillType type) {
		switch(type) {
		case BUTCHER:
			return Butcher.getPerks();
		case CAVEMAN:
			return Caveman.getPerks();
		case EXPLORER:
			return Explorer.getPerks();
		case HARVESTER:
			return Harvester.getPerks();
		case HUNTSMAN:
			return Huntsman.getPerks();
		case INTELLECTUAL:
			return Intellectual.getPerks();
		case NATURALIST:
			return Naturalist.getPerks();
		case SURVIVALIST:
			return Survivalist.getPerks();
		}
		return null;
	}
	public static String[] getActivePerks(Player player, SkillType type) {
		String[] perks = new String[9];
		int count = 0;
		for (Perk perk : getPerks(type)) {
			if (activePerk(player, perk)) {
				perks[count] = getFormattedName(perk);
				count++; 
			}
		}
		return perks;
	}
	public static boolean hasPerk(Player player, Perk perk) {
		return SkillDatabase.ownedPerks.get(player).contains(perk);
	}
	public static String getFormattedName(Perk perk) {
		switch(perk) {
		case BLOODMONEY:
			return "Blood Money";
		case BLUNTFORCETRAUMA:
			return "Blunt Force Trauma";
		case HITMAN:
			return "Hitman";
		case KILLINGBLOW:
			return "Killing Blow";
		case SELFDEFENSE:
			return "Self Defense";
		case SERRATIONS:
			return "Serrations";
		case SPOILSOFWAR:
			return "Spoils of War";
		case THEGRINDR:
			return "The Grindr";
		case THEPUMMELER:
			return "The Pummeler";
		case CAVEVISION:
			return "Cave Vision";
		case CLIMBINGGEAR:
			return "Climbing Gear";
		case DIVININGROD:
			return "Divining Rod";
		case EFFICIENTDIGGER:
			return "Efficient Digger";
		case MANICMINING:
			return "Manic Mining";
		case OREWHISPERER:
			return "Ore Whisperer";
		case THERMALINSULATION:
			return "Thermal Insulation";
		case SUPERIORSMELTING:
			return "Superior Smelting";
		case MOTHERLODE:
			return "Motherlode";
		case AGRICULTURALSTUDIES:
			return "Agricultural Studies";
		case ARTFULDODGER:
			return "Artful Dodger";
		case ARTOFTHEDEAL:
			return "Art of the Deal";
		case ATTACKOFTHECLONES:
			return "Attack of the Clones";
		case BAREHANDEDARCHERY:
			return "Bare Handed Archery";
		case BETTERMENDER:
			return "Better Mender";
		case BIGGERSHOVEL:
			return "Bigger Shovel";
		case BOMBTHROWER:
			return "Bomb Thrower";
		case CALLOFTHEWILD:
			return "Call of the Wild";
		case CHRISKYLE:
			return "Chris Kyle";
		case EMTCERTIFIED:
			return "EMT Certified";
		case EXPLOSIVESHOTS:
			return "Explosive Shots";
		case FAMILYRECIPES:
			return "Family Recipes";
		case FARMINGFRENZY:
			return "Farming Frenzy";
		case FERTILITYTREATMENT:
			return "Fertility Treatment";
		case FERTILIZATION:
			return "Fertilization";
		case FREAKISHFARMING:
			return "Freakish Farming";
		case GORDONRAMSAY:
			return "Gordon Ramsay";
		case GREENTHUMB:
			return "Green Thumb";
		case INCENDIARYROUNDS:
			return "Incendiary Rounds";
		case INDUSTRIALREVOLUTION:
			return "Industrial Revolution";
		case ISPEAKFORTHETREES:
			return "I Speak for the Trees";
		case KENTUCKYDERBY:
			return "Kentucky Derby";
		case LUCKYLOOTER:
			return "Lucky Looter";
		case LUMBERJACK:
			return "Lumberjack";
		case MAGICMAN:
			return "Magic Man";
		case MAJORSWINDLER:
			return "Major Swindler";
		case MANVSWILD:
			return "Man vs. Wild";
		case MEGAEXCAVATOR:
			return "Mega Excavator";
		case MUSHROOMMAN:
			return "Mushroom Man";
		case NATURESTOUCH:
			return "Nature's Touch";
		case NAVIGATIONALSKILLS:
			return "Navigational Skills";
		case NEPTUNEFLIGHT:
			return "Neptune Flight";
		case HARSHPARENTING:
			return "Paleolithic Prowess";
		case PIGWHISPERER:
			return "Pig Whisperer";
		case QUICKLEARNER:
			return "Quick Learner";
		case REPLANTER:
			return "Replanter";
		case RESURRECTION:
			return "Resurrection";
		case SALVAGEOPERATION:
			return "Salvage Operation";
		case SLAUGHTERHOUSE:
			return "Slaughterhouse";
		case SNIPERSENSE:
			return "Sniper Sense";
		case SUPERFOODS:
			return "Superfoods";
		case SUPERSAIYAN:
			return "Super Saiyan";
		case SWEETMEATS:
			return "Sweet Meats";
		case SWIFTSTEPS:
			return "Swift Steps";
		case THEBIRDSANDTHEBEES:
			return "The Birds & The Bees";
		case HIPPYHEALING:
			return "Hippy Healing";
		case THEYFLYNOW:
			return "They Fly Now";
		case TREASURESNIFFER:
			return "Treasure Sniffer";
		case TREECAPITATOR:
			return "TreeCapitator";
		case UNMATCHEDWILLPOWER:
			return "Unmatched Willpower";
		case USETHEFORCE:
			return "Use the Force";
		case XPREBATES:
			return "XP Rebates";
		case YOUMISSEDME:
			return "You Missed Me";
		default:
			return "Placeholder!";
		
		}
	}
	public static boolean activePerk(Player player, Perk perk) {
		return SkillDatabase.activePerks.get(player).contains(perk);

	}
	public static void buyPerk(Player p, Perk perk) {
//		SkillDatabase.activePerks.get(p).add(perk);
		SkillDatabase.ownedPerks.get(p).add(perk);
		setPoints(p, getPoints(p) - Perks.getPointCost(perk));
	}
	public static void levelUp(Player p, SkillType type) {
		int skillLevel = SkillDatabase.getSkill(type).get(p) + 1;
		int skillPoints = SkillDatabase.skillPoints.get(p);
		int newPoints = skillPoints - getLevelCost(p, type);
		SkillDatabase.getSkill(type).put(p, skillLevel);
		SkillDatabase.skillPoints.put(p, newPoints);		
	}
	public static void deactivatePerk(Player p, Perk perk) {
		SkillDatabase.activePerks.get(p).remove(perk);
	}
	public static void activatePerk(Player p, Perk perk) {
		SkillDatabase.activePerks.get(p).add(perk);
	}
	public static int getLevelCost(Player p, SkillType type) {
		if (getLevel(p, type) < 5) return 1;
		if (getLevel(p, type) < 10) return 2;
		if (getLevel(p, type) < 15) return 3;
		if (getLevel(p, type) <= 20) return 5;
		return 0;
	}
	public static boolean canLevelUp(Player p, SkillType type) {
		return (SkillManager.getPoints(p) >= SkillManager.getLevelCost(p, type) && SkillManager.getLevel(p, type) < 20);
	}
	public static int getPointsToLevelUp(Player p, SkillType type) {
		return (getLevelCost(p, type) - getPoints(p));
	}
	public static boolean canClaimSkill(Player p, SkillType type) {
		boolean meetsLevelReq = (getLevel(p,type) >= 5);
		boolean hasEnoughPoints = (getPoints(p) >= 5);
		boolean hasClaimedSkill = (getMainSkill(p) != null);
		return meetsLevelReq && hasEnoughPoints && !hasClaimedSkill;
	}
	public static SkillType getMainSkill(Player p) {
		try {
			return SkillDatabase.mainSkills.get(p);
		} catch (Exception e) {
			return null;
		}
	}
	public static void setMainSkill(Player p, SkillType type) {
		SkillDatabase.mainSkills.put(p, type);
	}
	public static void removeMainSkill(Player p) {
		SkillDatabase.mainSkills.put(p, null);
	}

}
