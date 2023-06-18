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
import net.jahcraft.jahskills.skills.SkillType;
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
		case WEEPINGCREEPERS:
		{
			ItemStack button = constructPerkButton(Material.CREEPER_HEAD, 
												   "Weeping Creepers", 
												   "Keep a feline companion in the", 
												   "caves to keep the creepers at bay.", 
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
			return 9;
		case BLUNTFORCETRAUMA:
			return 3;
		case HITMAN:
			return 12;
		case KILLINGBLOW:
			return 15;
		case SELFDEFENSE:
			return 3;
		case SERRATIONS:
			return 3;
		case SPOILSOFWAR:
			return 6;
		case THEGRINDR:
			return 9;
		case THEPUMMELER:
			return 3;
		default:
			return 0;
		}
	}
	
	public static int getLevelRequirement(Perk p) {
		switch(p) {
		case BLOODMONEY:
			return 10;
		case BLUNTFORCETRAUMA:
			return 5;
		case HITMAN:
			return 20;
		case KILLINGBLOW:
			return 20;
		case SELFDEFENSE:
			return 10;
		case SERRATIONS:
			return 5;
		case SPOILSOFWAR:
			return 15;
		case THEGRINDR:
			return 15;
		case THEPUMMELER:
			return 5;
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
		default:
		{
			Perk[] conflicts = {};
			return Arrays.asList(conflicts);
		}
		
		}
	}

}
