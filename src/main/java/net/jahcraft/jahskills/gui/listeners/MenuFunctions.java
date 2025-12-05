package net.jahcraft.jahskills.gui.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.jahcraft.jahskills.gui.animations.SkillMenuAnim;
import net.jahcraft.jahskills.gui.menus.SkillMenu;
import net.jahcraft.jahskills.main.Main;
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
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.md_5.bungee.api.ChatColor;

public class MenuFunctions {
	
	public static void skillLevelUpButton(Player p, SkillType type, InventoryClickEvent e) {
		if (SkillManager.canLevelUp(p, type)) {
			if (e.getClick() == ClickType.SHIFT_LEFT) {
				while (SkillManager.canLevelUp(p, type)) {
					SkillManager.levelUp(p, type);
				}
			} else {
				SkillManager.levelUp(p, type);
			}
			e.getInventory().setItem(22, getSkillButton(p, type));
			e.getInventory().setItem(49, SkillMenu.getInfoButton(p));
			p.sendMessage(ChatColor.RED + "You have " + SkillManager.getPoints(p) + " points remaining.");
		}
	}
	
	public static ItemStack getSkillButton(Player p, SkillType type) {
		switch(type) {
		case BUTCHER:
			return Butcher.getSkillButton(p);
		case CAVEMAN:
			return Caveman.getSkillButton(p);
		case EXPLORER:
			return Explorer.getSkillButton(p);
		case HARVESTER:
			return Harvester.getSkillButton(p);
		case HUNTSMAN:
			return Huntsman.getSkillButton(p);
		case INTELLECTUAL:
			return Intellectual.getSkillButton(p);
		case NATURALIST:
			return Naturalist.getSkillButton(p);
		case SURVIVALIST:
			return Survivalist.getSkillButton(p);
		default:
			break;
		}
		return null;
	}
	
	static void clickPerk(InventoryClickEvent e, SkillType type, Player p, Perk perk) {
		if (SkillManager.hasPerk(p, perk)) {
			if (SkillManager.activePerk(p, perk)) {
				SkillManager.deactivatePerk(p, perk);
			} else {
				for (Perk conflict : Perks.getConflicts(perk)) {
//					p.sendMessage(conflict.toString());
					SkillManager.deactivatePerk(p, conflict);
					e.getInventory().setItem(getSlot(conflict), Perks.getButton(p, conflict));
				}
				SkillManager.activatePerk(p, perk);
			}
		}
		else if (Perks.canBuy(p, type, perk)) {
			SkillManager.buyPerk(p, perk);
			p.sendMessage(ChatColor.RED + "You have " + SkillManager.getPoints(p) + " points remaining.");
		}
		e.getInventory().setItem(e.getSlot(), Perks.getButton(p, perk));
		e.getInventory().setItem(49, SkillMenu.getInfoButton(p));

	}
	
	public static int getSlot(Perk p) {
		switch(p) {
		case BLOODMONEY:
			return 36;
		case BLUNTFORCETRAUMA:
			return 43;
		case HITMAN:
			return 40;
		case KILLINGBLOW:
			return 39;
		case SELFDEFENSE:
			return 38;
		case SERRATIONS:
			return 42;
		case SPOILSOFWAR:
			return 37;
		case THEGRINDR:
			return 44;
		case THEPUMMELER:
			return 41;
		case MOTHERLODE:
			return 36;
		case SUPERIORSMELTING:
			return 43;
		case CLIMBINGGEAR:
			return 40;
		case CAVEVISION:
			return 39;
		case EFFICIENTDIGGER:
			return 38;
		case DIVININGROD:
			return 42;
		case OREWHISPERER:
			return 37;
		case MANICMINING:
			return 44;
		case THERMALINSULATION:
			return 41;
		case AGRICULTURALSTUDIES:
			return 43;
		case ARTFULDODGER:
			return 37;
		case ARTOFTHEDEAL:
			return 38;
		case ATTACKOFTHECLONES:
			return 39;
		case BAREHANDEDARCHERY:
			return 42;
		case BETTERMENDER:
			return 41;
		case BIGGERSHOVEL:
			return 40;
		case BOMBTHROWER:
			return 43;
		case CALLOFTHEWILD:
			return 41;
		case CHRISKYLE:
			return 39;
		case EMTCERTIFIED:
			return 41;
		case EXPLOSIVESHOTS:
			return 40;
		case FAMILYRECIPES:
			return 37;
		case FARMINGFRENZY:
			return 39;
		case FERTILITYTREATMENT:
			return 40;
		case FERTILIZATION:
			return 42;
		case FREAKISHFARMING:
			return 38;
		case GORDONRAMSAY:
			return 40;
		case GREENTHUMB:
			return 36;
		case INCENDIARYROUNDS:
			return 41;
		case INDUSTRIALREVOLUTION:
			return 44;
		case ISPEAKFORTHETREES:
			return 42;
		case KENTUCKYDERBY:
			return 40;
		case LUCKYLOOTER:
			return 36;
		case LUMBERJACK:
			return 37;
		case ENDERINFUSION:
			return 42;
		case HIGHDEMAND:
			return 39;
		case MANVSWILD:
			return 44;
		case MEGAEXCAVATOR:
			return 39;
		case MUSHROOMMAN:
			return 38;
		case NATURESTOUCH:
			return 41;
		case NAVIGATIONALSKILLS:
			return 44;
		case NEPTUNEFLIGHT:
			return 42;
		case HARSHPARENTING:
			return 44;
		case PIGWHISPERER:
			return 38;
		case QUICKLEARNER:
			return 40;
		case REPLANTER:
			return 37;
		case RESURRECTION:
			return 38;
		case SALVAGEOPERATION:
			return 39;
		case SLAUGHTERHOUSE:
			return 36;
		case SNIPERSENSE:
			return 44;
		case SUPERFOODS:
			return 42;
		case SUPERSAIYAN:
			return 44;
		case SWEETMEATS:
			return 37;
		case SWIFTSTEPS:
			return 37;
		case THEBIRDSANDTHEBEES:
			return 41;
		case HIPPYHEALING:
			return 43;
		case THEYFLYNOW:
			return 38;
		case TREASURESNIFFER:
			return 43;
		case TREECAPITATOR:
			return 36;
		case UNMATCHEDWILLPOWER:
			return 43;
		case USETHEFORCE:
			return 43;
		case XPREBATES:
			return 36;
		case YOUMISSEDME:
			return 36;
		default:
			return -1;

		
		}
	}
	
	public static void clickBackButton(List<Inventory> invs, InventoryClickEvent e, Player p) {
		invs.remove(e.getClickedInventory());
		Inventory inv = SkillMenu.getInv(p);
		SkillMenuListener.invs.add(inv);
		e.getWhoClicked().openInventory(inv);
		Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SkillMenuAnim(inv, p));
	}

}
