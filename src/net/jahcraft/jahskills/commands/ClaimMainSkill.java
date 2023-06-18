package net.jahcraft.jahskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.jahcraft.jahskills.gui.animations.SkillMenuAnim;
import net.jahcraft.jahskills.gui.listeners.SkillMenuListener;
import net.jahcraft.jahskills.gui.menus.SkillMenu;
import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.skills.SkillType;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.md_5.bungee.api.ChatColor;

public class ClaimMainSkill implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!label.equalsIgnoreCase("claimmainskill")) return false;
		
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Invalid arguments!");
			return true;
		}
		if (SkillType.valueOf(args[0]) == null) {
			sender.sendMessage("Skill not found!");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("No!");
			return true;
		}
		SkillType type = SkillType.valueOf(args[0]);
		Player player = (Player) sender;
		if (!SkillManager.canClaimSkill((Player)sender, type)) {
			sender.sendMessage(ChatColor.RED + "You can't claim that skill");
			return true;
		} else {
			SkillManager.setMainSkill(player, type);
			SkillManager.setPoints(player, SkillManager.getPoints(player)-5);
			player.sendMessage(ChatColor.GREEN + "Your main skill has been set!");
			Inventory inv = SkillMenu.getInv(player);
			SkillMenuListener.invs.add(inv);
			player.openInventory(inv);
			Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new SkillMenuAnim(inv, player));
		}
		
		return true;
	}
	
}
