package net.jahcraft.jahskills.perks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.jahcraft.jahskills.skills.Butcher;
import net.jahcraft.jahskills.skills.Caveman;
import net.jahcraft.jahskills.skills.Explorer;
import net.jahcraft.jahskills.skills.Harvester;
import net.jahcraft.jahskills.skills.Huntsman;
import net.jahcraft.jahskills.skills.Intellectual;
import net.jahcraft.jahskills.skills.Naturalist;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skills.Survivalist;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.util.Colors;
import net.md_5.bungee.api.ChatColor;

public class Perks {
	
	private static String breaker = Colors.BLUE + "" + ChatColor.STRIKETHROUGH + "                   ";
	private static String active = ChatColor.GREEN + "Active Perk! Click to Deactivate.";
	private static String inactive = Colors.BRIGHTRED + "Inactive Perk! Click to Activate.";
	private static String requirements = Colors.PALEBLUE + "Requirements:";
	
	public static ItemStack getButton(Player player, Perk perk) {
		
		
		switch(perk) {
		case BLOODMONEY:
		{
			ItemStack button = constructPerkButton(Material.EMERALD, 
												   "Blood Money", 
												   "Get paid in cash for", 
												   "all of your kills!", 
												   perk, 
												   player);
			return button;
		}
		case BLUNTFORCETRAUMA:
		{
			ItemStack button = constructPerkButton(Material.IRON_SHOVEL, 
												   "Blunt Force Trauma", 
												   "Bonus damage effects when", 
												   "attacking with your trusty spade!", 
												   perk, 
												   player);
			return button;
		}
		case HITMAN:
		{
			ItemStack button = constructPerkButton(Material.DIAMOND_SWORD, 
												   "Hitman", 
												   "Gain boosted experience from player", 
												   "kills. Players will drop their heads.", 
												   perk, 
												   player);
			return button;
		}
		case KILLINGBLOW:
		{
			ItemStack button = constructPerkButton(Material.GUNPOWDER, 
												   "Killing Blow", 
												   "Chance to inflict an explosive", 
												   "killing blow when using a weapon.", 
												   perk, 
												   player);
			return button;
		}
		case SELFDEFENSE:
		{
			ItemStack button = constructPerkButton(Material.GOLDEN_SWORD, 
												   "Self Defense", 
												   "Damage increase when retaliating", 
												   "against an attack.", 
												   perk, 
												   player);
			return button;
		}
		case SERRATIONS:
		{
			ItemStack button = constructPerkButton(Material.SHEARS, 
												   "Serrations", 
												   "Chance to inflict bleeding cuts", 
												   "when using bladed weapons.", 
												   perk, 
												   player);
			return button;
		}
		case SPOILSOFWAR:
		{
			ItemStack button = constructPerkButton(Material.GOLD_NUGGET, 
												   "Spoils of War", 
												   "Get extra fat loots when", 
												   "killing fat mobs.", 
												   perk, 
												   player);
			return button;
		}
		case THEGRINDR:
		{
			ItemStack button = constructPerkButton(Material.SPAWNER, 
												   "The Grindr", 
												   "Gain experience much quicker", 
												   "when killing mobs from spawers.", 
												   perk, 
												   player);
			return button;
		}
		case THEPUMMELER:
		{
			ItemStack button = constructPerkButton(Material.RABBIT_FOOT, 
												   "The Pummeler", 
												   "Throw some hands with the opps", 
												   "and knock they asses out!", 
												   perk, 
												   player);
			return button;
		}
		case CAVEVISION:
		{
			ItemStack button = constructPerkButton(Material.GOLDEN_CARROT, 
												   "Cave Vision", 
												   "Get down on your knees, it'll", 
												   "help you see in the dark.", 
												   perk, 
												   player);
			return button;
		}
		case CLIMBINGGEAR:
		{
			ItemStack button = constructPerkButton(Material.LEAD, 
												   "Climbing Gear", 
												   "Gravity has become a much", 
												   "less significant problem.", 
												   perk, 
												   player);
			return button;
		}
		case DIVININGROD:
		{
			ItemStack button = constructPerkButton(Material.BLAZE_ROD, 
												   "Divining Rod", 
												   "Helps you sniff out ores", 
												   "while you're deep underground.", 
												   perk, 
												   player);
			return button;
		}
		case EFFICIENTDIGGER:
		{
			ItemStack button = constructPerkButton(Material.COBBLESTONE, 
												   "Efficient Digger", 
												   "Get yourself a better yield", 
												   "when mining terrain blocks.", 
												   perk, 
												   player);
			return button;
		}
		case MANICMINING:
		{
			ItemStack button = constructPerkButton(Material.GOLDEN_PICKAXE, 
												   "Manic Miner", 
												   "You'll be digging so fast you'll", 
												   "hardly remember the crack you sniffed.", 
												   perk, 
												   player);
			return button;
		}
		case OREWHISPERER:
		{
			ItemStack button = constructPerkButton(Material.EXPERIENCE_BOTTLE, 
												   "The Ore Whisperer", 
												   "Learn how to finesse the ores", 
												   "into giving you more experience.", 
												   perk, 
												   player);
			return button;
		}
		case THERMALINSULATION:
		{
			ItemStack button = constructPerkButton(Material.LEATHER, 
												   "Thermal Insulation", 
												   "Fall into lava and you'll have", 
												   "more time to get the hell out!", 
												   perk, 
												   player);
			return button;
		}
		case SUPERIORSMELTING:
		{
			ItemStack button = constructPerkButton(Material.BLAST_FURNACE, 
												   "Superior Smelting", 
												   "You can get twice the yield", 
												   "when smelting your raw ores!", 
												   perk, 
												   player);
			return button;
		}
		case MOTHERLODE:
		{
			ItemStack button = constructPerkButton(Material.DIAMOND, 
												   "Motherlode", 
												   "Bring home the motherlode and ", 
												   "finally cross the poverty line!", 
												   perk, 
												   player);
			return button;
		}
		case AGRICULTURALSTUDIES:
		{
			ItemStack button = constructPerkButton(Material.EXPERIENCE_BOTTLE, 
												   "Agricultural Studies", 
												   "Gain the knowledge and experience", 
												   "of all the crops you harvest!", 
												   perk, 
												   player);
			return button;
		}
		case ARTFULDODGER:
		{
			ItemStack button = constructPerkButton(Material.CHORUS_FRUIT, 
												   "Artful Dodger", 
												   "A chance to make a quick getaway", 
												   "after an enemy attacks you!", 
												   perk, 
												   player);
			return button;
		}
		case ARTOFTHEDEAL:
		{
			ItemStack button = constructPerkButton(Material.BOOK, 
												   "The Art of the Deal", 
												   "Villagers love giving you", 
												   "massive trade discounts!", 
												   perk, 
												   player);
			return button;
		}
		case ATTACKOFTHECLONES:
		{
			ItemStack button = constructPerkButton(Material.ZOMBIE_HEAD, 
												   "Attack of the Clones", 
												   "Summon a group of temporary", 
												   "clones to aid your defense!", 
												   perk, 
												   player);
			return button;
		}
		case BAREHANDEDARCHERY:
		{
			ItemStack button = constructPerkButton(Material.RABBIT_FOOT, 
												   "Bare Handed Archery", 
												   "Who needs a bow? Grab that arrow", 
												   "and throw it yourself!", 
												   perk, 
												   player);
			return button;
		}
		case BETTERMENDER:
		{
			ItemStack button = constructPerkButton(Material.ENCHANTED_BOOK, 
												   "Better Mender", 
												   "Why should repairs take time?", 
												   "Mend your gear much quicker!", 
												   perk, 
												   player);
			return button;
		}
		case BIGGERSHOVEL:
		{
			ItemStack button = constructPerkButton(Material.DIAMOND_SHOVEL, 
												   "A Bigger Shovel", 
												   "Dig up more dirty dirt", 
												   "with your big ol' shovel.", 
												   perk, 
												   player);
			return button;
		}
		case BOMBTHROWER:
		{
			ItemStack button = constructPerkButton(Material.FIRE_CHARGE, 
												   "Bomb Thrower", 
												   "Put those fire charges to use", 
												   "and launch fireballs at your enemies!", 
												   perk, 
												   player);
			return button;
		}
		case CALLOFTHEWILD:
		{
			ItemStack button = constructPerkButton(Material.SADDLE, 
												   "Call of the Wild", 
												   "Throw a saddle on the ground", 
												   "to instantly summon a mount!", 
												   perk, 
												   player);
			return button;
		}
		case CHRISKYLE:
		{
			ItemStack button = constructPerkButton(Material.BOW, 
												   "Chris Kyle", 
												   "Your sniping abilities will", 
												   "leave your targets in a daze!", 
												   perk, 
												   player);
			return button;
		}
		case EMTCERTIFIED:
		{
			ItemStack button = constructPerkButton(Material.GHAST_TEAR, 
												   "EMT Certified", 
												   "Craft first aid kits to give", 
												   "yourself the quick health you need!", 
												   perk, 
												   player);
			ItemMeta meta = button.getItemMeta();
			meta.setCustomModelData(1);
			button.setItemMeta(meta);
			return button;
		}
		case EXPLOSIVESHOTS:
		{
			ItemStack button = constructPerkButton(Material.TNT, 
												   "Explosive Shots", 
												   "A rare chance to shoot a real", 
												   "bomber right at your target.", 
												   perk, 
												   player);
			return button;
		}
		case FAMILYRECIPES:
		{
			ItemStack button = constructPerkButton(Material.CRAFTING_TABLE, 
												   "Family Recipes", 
												   "Unlock the ability to craft", 
												   "many uncraftable items.", 
												   perk, 
												   player);
			return button;
		}
		case FARMINGFRENZY:
		{
			ItemStack button = constructPerkButton(Material.GOLDEN_HOE, 
												   "Farming Frenzy", 
												   "Harvest and replant your crops", 
												   "up to nine at time!", 
												   perk, 
												   player);
			return button;
		}
		case FERTILITYTREATMENT:
		{
			ItemStack button = constructPerkButton(Material.WHEAT, 
												   "Fertility Treatment", 
												   "Animals have a chance at having", 
												   "twins and triplets when you breed them.", 
												   perk, 
												   player);
			return button;
		}
		case FERTILIZATION:
		{
			ItemStack button = constructPerkButton(Material.BONE_MEAL, 
												   "Fertilization", 
												   "Bone meal will always fully", 
												   "grow your crops!", 
												   perk, 
												   player);
			return button;
		}
		case FREAKISHFARMING:
		{
			ItemStack button = constructPerkButton(Material.DIAMOND_HOE, 
												   "Freakish Farming", 
												   "Completely harvest and replant a", 
												   "whole chain of crops at once!", 
												   perk, 
												   player);
			return button;
		}
		case GORDONRAMSAY:
		{
			ItemStack button = constructPerkButton(Material.COOKED_BEEF, 
												   "Gordon Ramsay", 
												   "Your professional chef skills will", 
												   "make your meals more nourishing!", 
												   perk, 
												   player);
			return button;
		}
		case GREENTHUMB:
		{
			ItemStack button = constructPerkButton(Material.WHEAT_SEEDS, 
												   "Green Thumb", 
												   "Your crops never fail to give", 
												   "you a little extra in your harvest!", 
												   perk, 
												   player);
			return button;
		}
		case INCENDIARYROUNDS:
		{
			ItemStack button = constructPerkButton(Material.FLINT_AND_STEEL, 
												   "Incendiary Rounds", 
												   "Some of your shots will burn", 
												   "your target alive!", 
												   perk, 
												   player);
			return button;
		}
		case INDUSTRIALREVOLUTION:
		{
			ItemStack button = constructPerkButton(Material.PISTON, 
												   "Industrial Revolution", 
												   "Easily convert your redstone", 
												   "into simple machines!", 
												   perk, 
												   player);
			return button;
		}
		case ISPEAKFORTHETREES:
		{
			ItemStack button = constructPerkButton(Material.EXPERIENCE_BOTTLE, 
												   "I Speak for the Trees!", 
												   "Gain experience from rampant", 
												   "deforestation! How bad can I be?", 
												   perk, 
												   player);
			return button;
		}
		case KENTUCKYDERBY:
		{
			ItemStack button = constructPerkButton(Material.GOLDEN_HORSE_ARMOR, 
												   "Kentucky Derby", 
												   "Turn any horse you ride into", 
												   "a real racing horse!", 
												   perk, 
												   player);
			return button;
		}
		case LUCKYLOOTER:
		{
			ItemStack button = constructPerkButton(Material.DIAMOND, 
												   "Lucky Looter", 
												   "Get more treasure from the chests", 
												   "you loot in your travels!", 
												   perk, 
												   player);
			return button;
		}
		case LUMBERJACK:
		{
			ItemStack button = constructPerkButton(Material.DIAMOND_AXE, 
												   "Lumberjack", 
												   "You know your way around a tree!", 
												   "Harvest more wood when chopping logs.", 
												   perk, 
												   player);
			return button;
		}
		case ENDERINFUSION:
		{
			ItemStack button = constructPerkButton(Material.ENDER_EYE, 
												   "Ender Infusion", 
												   "Engage your ender instincts!", 
												   "Instinctually avoid arrows and water!", 
												   perk, 
												   player);
			ItemMeta meta = button.getItemMeta();
			meta.setCustomModelData(1);
			button.setItemMeta(meta);
			return button;
		}
		case HIGHDEMAND:
		{
			ItemStack button = constructPerkButton(Material.EMERALD, 
												   "High Demand", 
												   "Corner the market! Villagers will", 
												   "buy more of whatever you have!", 
												   perk, 
												   player);
			return button;
		}
		case MANVSWILD:
		{
			ItemStack button = constructPerkButton(Material.GRASS, 
												   "Man vs. Wild", 
												   "Is this edible? It is now!", 
												   "You can ingest questionable items.", 
												   perk, 
												   player);
			return button;
		}
		case MEGAEXCAVATOR:
		{
			ItemStack button = constructPerkButton(Material.GOLDEN_SHOVEL, 
												   "Mega Excavator", 
												   "You've got the fastest shovel in town.", 
												   "A short burst of energy makes it faster.", 
												   perk, 
												   player);
			return button;
		}
		case MUSHROOMMAN:
		{
			ItemStack button = constructPerkButton(Material.RED_MUSHROOM, 
												   "Mushroom Man", 
												   "Your poor hygiene lets you grow", 
												   "fungus wherever you want!", 
												   perk, 
												   player);
			return button;
		}
		case NATURESTOUCH:
		{
			ItemStack button = constructPerkButton(Material.MOSS_BLOCK, 
												   "Nature's Touch", 
												   "Everything you touch manages", 
												   "to become overgrown!",
												   perk, 
												   player);
			return button;
		}
		case NAVIGATIONALSKILLS:
		{
			ItemStack button = constructPerkButton(Material.COMPASS, 
												   "Navigational Skills", 
												   "You always know where you're going!", 
												   "Locate any biome with ease!",
												   perk, 
												   player);
			return button;
		}
		case NEPTUNEFLIGHT:
		{
			ItemStack button = constructPerkButton(Material.TRIDENT, 
												   "Neptune Flight", 
												   "Riptide launching will come", 
												   "to you much easier!",
												   perk, 
												   player);
			return button;
		}
		case HARSHPARENTING:
		{
			ItemStack button = constructPerkButton(Material.LEAD, 
												   "Harsh Parenting", 
												   "Harness the power of negative reinforcement", 
												   "and make those babies grow up before their time!",
												   perk, 
												   player);
			return button;
		}
		case PIGWHISPERER:
		{
			ItemStack button = constructPerkButton(Material.EXPERIENCE_BOTTLE, 
												   "Pig Whisperer", 
												   "Learn the secrets of the swine", 
												   "and gain XP when culling the herd.",
												   perk, 
												   player);
			return button;
		}
		case QUICKLEARNER:
		{
			ItemStack button = constructPerkButton(Material.EXPERIENCE_BOTTLE, 
												   "Quick Learner", 
												   "No matter the activity, experience", 
												   "just seems to come easier to you.",
												   perk, 
												   player);
			return button;
		}
		case REPLANTER:
		{
			ItemStack button = constructPerkButton(Material.BEETROOT_SEEDS, 
												   "Replanter", 
												   "Any crops you harvest with a hoe", 
												   "will automatically be replanted!",
												   perk, 
												   player);
			return button;
		}
		case RESURRECTION:
		{
			ItemStack button = constructPerkButton(Material.TOTEM_OF_UNDYING, 
												   "Resurrection", 
												   "You get a rare chance at a second", 
												   "life! No totem required!",
												   perk, 
												   player);
			return button;
		}
		case SALVAGEOPERATION:
		{
			ItemStack button = constructPerkButton(Material.ANVIL, 
												   "Salvage Operation", 
												   "Craft your tools and armor", 
												   "down and reclaim your materials!",
												   perk, 
												   player);
			return button;
		}
		case SLAUGHTERHOUSE:
		{
			ItemStack button = constructPerkButton(Material.STONECUTTER, 
												   "Slaughterhouse", 
												   "Killing innocent animals has never", 
												   "been easier! Eat it, PETA!",
												   perk, 
												   player);
			return button;
		}
		case SNIPERSENSE:
		{
			ItemStack button = constructPerkButton(Material.SPYGLASS, 
												   "Sniper Sense", 
												   "Your targets will stick out of the", 
												   "scene like a sore thumb!",
												   perk, 
												   player);
			return button;
		}
		case SUPERFOODS:
		{
			ItemStack button = constructPerkButton(Material.GOLDEN_APPLE, 
												   "Superfoods", 
												   "Gain some health by eating ", 
												   "high quality meals!",
												   perk, 
												   player);
			return button;
		}
		case SUPERSAIYAN:
		{
			ItemStack button = constructPerkButton(Material.YELLOW_DYE, 
												   "Super Saiyan", 
												   "Go absolutely berserk when you're", 
												   "near death in battle!",
												   perk, 
												   player);
			return button;
		}
		case SWEETMEATS:
		{
			ItemStack button = constructPerkButton(Material.PORKCHOP, 
												   "Sweet Meats", 
												   "Carve more steaks and chops", 
												   "off from your animal carcasses.",
												   perk, 
												   player);
			return button;
		}
		case SWIFTSTEPS:
		{
			ItemStack button = constructPerkButton(Material.GOLDEN_BOOTS, 
												   "Swift Steps", 
												   "You're much quicker on your", 
												   "feet! Walk and sprint faster!",
												   perk, 
												   player);
			return button;
		}
		case THEBIRDSANDTHEBEES:
		{
			ItemStack button = constructPerkButton(Material.EXPERIENCE_BOTTLE, 
												   "The Birds & The Bees", 
												   "Learn the birds and the bees.", 
												   "Gain more experience from animal breeding.",
												   perk, 
												   player);
			return button;
		}
		case HIPPYHEALING:
		{
			ItemStack button = constructPerkButton(Material.GRASS_BLOCK, 
												   "Hippy Healing", 
												   "Shoes are an illusion, and so is death.", 
												   "Heal by feeling the grass on your bare feet!",
												   perk,
												   player);
			return button;
		}
		case THEYFLYNOW:
		{
			ItemStack button = constructPerkButton(Material.ELYTRA, 
												   "They Fly Now", 
												   "Anyone can attain flight now!", 
												   "You can craft yourself elytra!",
												   perk, 
												   player);
			return button;
		}
		case TREASURESNIFFER:
		{
			ItemStack button = constructPerkButton(Material.CHEST, 
												   "Treasure Sniffer", 
												   "Sniff out buried treasure", 
												   "without the need for any X.",
												   perk, 
												   player);
			return button;
		}
		case TREECAPITATOR:
		{
			ItemStack button = constructPerkButton(Material.OAK_LOG, 
												   "TreeCapitator", 
												   "Chop down a whole tree in", 
												   "a matter of seconds!",
												   perk, 
												   player);
			return button;
		}
		case UNMATCHEDWILLPOWER:
		{
			ItemStack button = constructPerkButton(Material.TURTLE_HELMET, 
												   "Unmatched Willpower", 
												   "Become resistent to enemy attack", 
												   "effects such as stuns and bleeding.",
												   perk, 
												   player);
			return button;
		}
		case USETHEFORCE:
		{
			ItemStack button = constructPerkButton(Material.ENDER_PEARL, 
												   "Use the Force", 
												   "Push and pull your enemies!", 
												   "May the force be with you.",
												   perk, 
												   player);
			return button;
		}
		case XPREBATES:
		{
			ItemStack button = constructPerkButton(Material.LAPIS_LAZULI, 
												   "XP Rebates", 
												   "Regain some experience after", 
												   "using an enchantment table.",
												   perk, 
												   player);
			return button;
		}
		case YOUMISSEDME:
		{
			ItemStack button = constructPerkButton(Material.SHIELD, 
												   "You Missed Me!", 
												   "Rare chance to block or dodge", 
												   "to nullify enemy attacks.",
												   perk, 
												   player);
			return button;
		}
		default:
			break;


		}
		return new ItemStack(Material.BARRIER);
		
	}
	
	public static ItemStack constructPerkButton(Material m, String display, String lore1, String lore2, Perk perk, Player player) {
		ItemStack button = new ItemStack(m);
		ItemMeta meta = button.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setDisplayName(Colors.GOLD + display);
		List<String> lore = new ArrayList<>();
		lore.add(Colors.PALEBLUE + lore1);
		lore.add(Colors.PALEBLUE + lore2);
		lore.add(breaker);
		if (SkillManager.hasPerk(player, perk)) {
			if (SkillManager.activePerk(player, perk)) {
				meta.addEnchant(Enchantment.LURE, 5, true);
				lore.add(active);
			} else {
				lore.add(inactive);
				button.removeEnchantment(Enchantment.LURE);
			}
			lore.add(breaker);
			if (Perks.getConflicts(perk).size() > 0) {
				lore.add(Colors.GOLD + "Conflicts With:");
				for (Perk p : Perks.getConflicts(perk)) {
					lore.add(ChatColor.GRAY + "- " + Colors.BRIGHTBLUE + SkillManager.getFormattedName(p));
				}
			}
		} else {
			lore.add(requirements);
			lore.add(ChatColor.GRAY + "- " + Colors.GOLD + getPointCost(perk) + Colors.BRIGHTBLUE + " Skill Points");
			lore.add(ChatColor.GRAY + "- " + Colors.BRIGHTBLUE + getSkillName(perk) + " Level "+ Colors.GOLD + getLevelRequirement(perk));
		}
		meta.setLore(lore);
		button.setItemMeta(meta);
		return button;
		
	}
	
	public static int getPointCost(Perk p) {
		switch(p) {
		case BLOODMONEY:
			return 5;
		case BLUNTFORCETRAUMA:
			return 2;
		case HITMAN:
			return 8;
		case KILLINGBLOW:
			return 10;
		case SELFDEFENSE:
			return 3;
		case SERRATIONS:
			return 2;
		case SPOILSOFWAR:
			return 5;
		case THEGRINDR:
			return 6;
		case THEPUMMELER:
			return 2;
		case AGRICULTURALSTUDIES:
			return 3;
		case ARTFULDODGER:
			return 5;
		case ARTOFTHEDEAL:
			return 8;
		case ATTACKOFTHECLONES:
			return 6;
		case BAREHANDEDARCHERY:
			return 2;
		case BETTERMENDER:
			return 8;
		case BIGGERSHOVEL:
			return 2;
		case BOMBTHROWER:
			return 10;
		case CALLOFTHEWILD:
			return 3;
		case CAVEVISION:
			return 6;
		case CHRISKYLE:
			return 6;
		case CLIMBINGGEAR:
			return 3;
		case DIVININGROD:
			return 10;
		case EFFICIENTDIGGER:
			return 2;
		case EMTCERTIFIED:
			return 9;
		case EXPLOSIVESHOTS:
			return 10;
		case FAMILYRECIPES:
			return 3;
		case FARMINGFRENZY:
			return 3;
		case FERTILITYTREATMENT:
			return 5;
		case FERTILIZATION:
			return 3;
		case FREAKISHFARMING:
			return 6;
		case GORDONRAMSAY:
			return 3;
		case GREENTHUMB:
			return 6;
		case INCENDIARYROUNDS:
			return 3;
		case INDUSTRIALREVOLUTION:
			return 8;
		case ISPEAKFORTHETREES:
			return 4;
		case KENTUCKYDERBY:
			return 2;
		case LUCKYLOOTER:
			return 6;
		case LUMBERJACK:
			return 5;
		case ENDERINFUSION:
			return 10;
		case HIGHDEMAND:
			return 10;
		case MANICMINING:
			return 4;
		case MANVSWILD:
			return 2;
		case MEGAEXCAVATOR:
			return 4;
		case MOTHERLODE:
			return 7;
		case MUSHROOMMAN:
			return 2;
		case NATURESTOUCH:
			return 4;
		case NAVIGATIONALSKILLS:
			return 5;
		case NEPTUNEFLIGHT:
			return 6;
		case OREWHISPERER:
			return 6;
		case HARSHPARENTING:
			return 1;
		case PIGWHISPERER:
			return 2;
		case QUICKLEARNER:
			return 7;
		case REPLANTER:
			return 2;
		case RESURRECTION:
			return 10;
		case SALVAGEOPERATION:
			return 4;
		case SLAUGHTERHOUSE:
			return 2;
		case SNIPERSENSE:
			return 6;
		case SUPERFOODS:
			return 7;
		case SUPERIORSMELTING:
			return 5;
		case SUPERSAIYAN:
			return 8;
		case SWEETMEATS:
			return 5;
		case SWIFTSTEPS:
			return 8;
		case THEBIRDSANDTHEBEES:
			return 2;
		case THERMALINSULATION:
			return 5;
		case HIPPYHEALING:
			return 3;
		case THEYFLYNOW:
			return 8;
		case TREASURESNIFFER:
			return 8;
		case TREECAPITATOR:
			return 4;
		case UNMATCHEDWILLPOWER:
			return 10;
		case USETHEFORCE:
			return 6;
		case XPREBATES:
			return 4;
		case YOUMISSEDME:
			return 8;
		}
		return 0;
	}
	
	public static int getLevelRequirement(Perk p) {
		switch(p) {
		case BLOODMONEY:
			return 8;
		case BLUNTFORCETRAUMA:
			return 3;
		case HITMAN:
			return 12;
		case KILLINGBLOW:
			return 15;
		case SELFDEFENSE:
			return 8;
		case SERRATIONS:
			return 5;
		case SPOILSOFWAR:
			return 10;
		case THEGRINDR:
			return 12;
		case THEPUMMELER:
			return 3;
		case AGRICULTURALSTUDIES:
			return 6;
		case ARTFULDODGER:
			return 8;
		case ARTOFTHEDEAL:
			return 15;
		case ATTACKOFTHECLONES:
			return 15;
		case BAREHANDEDARCHERY:
			return 3;
		case BETTERMENDER:
			return 8;
		case BIGGERSHOVEL:
			return 1;
		case BOMBTHROWER:
			return 15;
		case CALLOFTHEWILD:
			return 3;
		case CAVEVISION:
			return 5;
		case CHRISKYLE:
			return 5;
		case CLIMBINGGEAR:
			return 6;
		case DIVININGROD:
			return 15;
		case EFFICIENTDIGGER:
			return 3;
		case EMTCERTIFIED:
			return 12;
		case EXPLOSIVESHOTS:
			return 15;
		case FAMILYRECIPES:
			return 5;
		case FARMINGFRENZY:
			return 8;
		case FERTILITYTREATMENT:
			return 5;
		case FERTILIZATION:
			return 10;
		case FREAKISHFARMING:
			return 15;
		case GORDONRAMSAY:
			return 6;
		case GREENTHUMB:
			return 15;
		case INCENDIARYROUNDS:
			return 8;
		case INDUSTRIALREVOLUTION:
			return 15;
		case ISPEAKFORTHETREES:
			return 6;
		case KENTUCKYDERBY:
			return 3;
		case LUCKYLOOTER:
			return 10;
		case LUMBERJACK:
			return 5;
		case ENDERINFUSION:
			return 15;
		case HIGHDEMAND:
			return 15;
		case MANICMINING:
			return 3;
		case MANVSWILD:
			return 1;
		case MEGAEXCAVATOR:
			return 3;
		case MOTHERLODE:
			return 10;
		case MUSHROOMMAN:
			return 1;
		case NATURESTOUCH:
			return 5;
		case NAVIGATIONALSKILLS:
			return 10;
		case NEPTUNEFLIGHT:
			return 8;
		case OREWHISPERER:
			return 6;
		case HARSHPARENTING:
			return 1;
		case PIGWHISPERER:
			return 5;
		case QUICKLEARNER:
			return 15;
		case REPLANTER:
			return 3;
		case RESURRECTION:
			return 15;
		case SALVAGEOPERATION:
			return 6;
		case SLAUGHTERHOUSE:
			return 3;
		case SNIPERSENSE:
			return 12;
		case SUPERFOODS:
			return 10;
		case SUPERIORSMELTING:
			return 10;
		case SUPERSAIYAN:
			return 12;
		case SWEETMEATS:
			return 5;
		case SWIFTSTEPS:
			return 5;
		case THEBIRDSANDTHEBEES:
			return 3;
		case THERMALINSULATION:
			return 10;
		case HIPPYHEALING:
			return 5;
		case THEYFLYNOW:
			return 12;
		case TREASURESNIFFER:
			return 15;
		case TREECAPITATOR:
			return 5;
		case UNMATCHEDWILLPOWER:
			return 15;
		case USETHEFORCE:
			return 12;
		case XPREBATES:
			return 5;
		case YOUMISSEDME:
			return 12;
		default:
			return 0;

		}
	}
	
	public static String getSkillName(Perk p) {

		for (Perk o : Butcher.getPerks()) {
			if (o==p) return "Butcher";
		}
		for (Perk o : Caveman.getPerks()) {
			if (o==p) return "Caveman";
		}
		for (Perk o : Naturalist.getPerks()) {
			if (o==p) return "Naturalist";
		}
		for (Perk o : Huntsman.getPerks()) {
			if (o==p) return "Huntsman";
		}
		for (Perk o : Harvester.getPerks()) {
			if (o==p) return "Harvester";
		}
		for (Perk o : Intellectual.getPerks()) {
			if (o==p) return "Intellectual";
		}
		for (Perk o : Explorer.getPerks()) {
			if (o==p) return "Explorer";
		}
		for (Perk o : Survivalist.getPerks()) {
			if (o==p) return "Survivalist";
		}
		
		return "Unknown";
		
	}

	public static boolean canBuy(Player p, SkillType skill, Perk perk) {
		boolean unlocked = (SkillManager.getLevel(p, skill) >= getLevelRequirement(perk));
		boolean canAfford = (SkillManager.getPoints(p) >= getPointCost(perk));
		return (unlocked && canAfford);
	}
	
	public static List<Perk> getConflicts(Perk p) {
		switch(p) {
		case BLOODMONEY: {
			Perk[] conflicts = {Perk.SPOILSOFWAR, Perk.THEGRINDR};
			return Arrays.asList(conflicts);
		}
		case BLUNTFORCETRAUMA:
		{
			Perk[] conflicts = {Perk.KILLINGBLOW};
			return Arrays.asList(conflicts);
		}		
		case KILLINGBLOW:
		{
			Perk[] conflicts = {Perk.SERRATIONS, Perk.THEPUMMELER, Perk.BLUNTFORCETRAUMA, Perk.SELFDEFENSE};
			return Arrays.asList(conflicts);
		}
		case SELFDEFENSE:
		{
			Perk[] conflicts = {Perk.KILLINGBLOW};
			return Arrays.asList(conflicts);
		}
		case SERRATIONS:
		{
			Perk[] conflicts = {Perk.KILLINGBLOW};
			return Arrays.asList(conflicts);
		}
		case SPOILSOFWAR:
		{
			Perk[] conflicts = {Perk.BLOODMONEY, Perk.THEGRINDR};
			return Arrays.asList(conflicts);
		}
		case THEGRINDR:
		{
			Perk[] conflicts = {Perk.SPOILSOFWAR, Perk.BLOODMONEY};
			return Arrays.asList(conflicts);
		}
		case THEPUMMELER:
		{
			Perk[] conflicts = {Perk.KILLINGBLOW};
			return Arrays.asList(conflicts);
		}
		case MUSHROOMMAN:
		{
			Perk[] conflicts = {Perk.NATURESTOUCH};
			return Arrays.asList(conflicts);
		}
		case NATURESTOUCH:
		{
			Perk[] conflicts = {Perk.MUSHROOMMAN};
			return Arrays.asList(conflicts);
		}
		default:
		{
			Perk[] conflicts = {};
			return Arrays.asList(conflicts);
		}
		
		}
	}

}
