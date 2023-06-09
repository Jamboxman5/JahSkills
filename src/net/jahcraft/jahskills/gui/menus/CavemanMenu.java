package net.jahcraft.jahskills.gui.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.jahcraft.jahskills.gui.animations.SubMenuAnim;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.perks.Perks;
import net.jahcraft.jahskills.skills.Caveman;

public class CavemanMenu {
	
	public static Inventory getInv(Player player) {

		Inventory inv = Bukkit.createInventory(null, 54, "Skills - Caveman");
		setFillers(inv);
		setButtons(inv, player);
		inv.setItem(49, SkillMenu.getInfoButton(player));

		return inv;
	}
	
	private static void setButtons(Inventory inv, Player p) {
		inv.setItem(4, SkillMenu.getBackButton());
		inv.setItem(22, Caveman.getSkillButton(p));
		
		int i = 36;
		for (Perk perk : Caveman.getPerks()) {
			inv.setItem(i, Perks.getButton(p, perk));
			i++;
		}
		
	}

	private static void setFillers(Inventory inv) {
		ItemStack filler = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		ItemMeta meta = filler.getItemMeta();
		meta.setDisplayName(" ");
		meta.setLore(null);
		filler.setItemMeta(meta);

		filler.setType(Material.BLUE_STAINED_GLASS_PANE);
		
		for (int i : SubMenuAnim.blueleft1) {
			inv.setItem(i, filler);
		}
		for (int i : SubMenuAnim.blueright1) {
			inv.setItem(i, filler);
		}
		for (int i : SubMenuAnim.blueleft2) {
			inv.setItem(i, filler);
		}
		for (int i : SubMenuAnim.blueright2) {
			inv.setItem(i, filler);
		}
		
		filler.setType(Material.PURPLE_STAINED_GLASS_PANE);
		
		for (int i : SubMenuAnim.purpleleft) {
			inv.setItem(i, filler);
		}
		for (int i : SubMenuAnim.purpleright) {
			inv.setItem(i, filler);
		}
		for (int i : SubMenuAnim.purplemiddle) {
			inv.setItem(i, filler);
		}
		
		filler.setType(Material.GRAY_STAINED_GLASS_PANE);
		
		for (int i = 45; i < 54; i++) {
			inv.setItem(i, filler);
		}

	}

}
