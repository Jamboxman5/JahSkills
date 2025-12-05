package net.jahcraft.jahskills.gui.animations;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SubMenuAnim implements Runnable {
	
	public static int[] blueleft1 = {0,9};
	public static int[] blueright1 = {8,17};
	public static int[] purpleleft = {1,10,19,18};
	public static int[] purpleright = {7,16,25,26};
	public static int[] blueleft2 = {2,11,20,29,28,27};
	public static int[] blueright2 = {6,15,24,33,34,35};
	public static int[] purplemiddle = {3,12,21,30,31,32,23,14,13,12,21,30,31,32,23,14,5};
	
	Inventory inv;
	Player player;
	
	public SubMenuAnim(Inventory i, Player p) {
		inv = i;
		player = p;
	}
	
	@Override
	public void run() {

		while (player.getOpenInventory().getTitle().contains("Skills")) {
		
			try {
								
				inv.getItem(blueleft1[0]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright1[0]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(blueleft1[0]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright1[0]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueleft1[1]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright1[1]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(blueleft1[1]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright1[1]).setType(Material.BLUE_STAINED_GLASS_PANE);
				Thread.sleep(600);
				
				////
				
				inv.getItem(purpleleft[0]).setType(Material.MAGENTA_STAINED_GLASS_PANE);
				inv.getItem(purpleright[0]).setType(Material.MAGENTA_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(purpleleft[0]).setType(Material.PURPLE_STAINED_GLASS_PANE);
				inv.getItem(purpleright[0]).setType(Material.PURPLE_STAINED_GLASS_PANE);
				inv.getItem(purpleleft[1]).setType(Material.MAGENTA_STAINED_GLASS_PANE);
				inv.getItem(purpleright[1]).setType(Material.MAGENTA_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(purpleleft[1]).setType(Material.PURPLE_STAINED_GLASS_PANE);
				inv.getItem(purpleright[1]).setType(Material.PURPLE_STAINED_GLASS_PANE);
				inv.getItem(purpleleft[2]).setType(Material.MAGENTA_STAINED_GLASS_PANE);
				inv.getItem(purpleright[2]).setType(Material.MAGENTA_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(purpleleft[2]).setType(Material.PURPLE_STAINED_GLASS_PANE);
				inv.getItem(purpleright[2]).setType(Material.PURPLE_STAINED_GLASS_PANE);
				inv.getItem(purpleleft[3]).setType(Material.MAGENTA_STAINED_GLASS_PANE);
				inv.getItem(purpleright[3]).setType(Material.MAGENTA_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(purpleleft[3]).setType(Material.PURPLE_STAINED_GLASS_PANE);
				inv.getItem(purpleright[3]).setType(Material.PURPLE_STAINED_GLASS_PANE);
				Thread.sleep(600);
				
				////
				
				inv.getItem(blueleft2[0]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[0]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(blueleft2[0]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[0]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueleft2[1]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[1]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(blueleft2[1]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[1]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueleft2[2]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[2]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(blueleft2[2]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[2]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueleft2[3]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[3]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(blueleft2[3]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[3]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueleft2[4]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[4]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(blueleft2[4]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[4]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueleft2[5]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[5]).setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
				Thread.sleep(150);
				
				inv.getItem(blueleft2[5]).setType(Material.BLUE_STAINED_GLASS_PANE);
				inv.getItem(blueright2[5]).setType(Material.BLUE_STAINED_GLASS_PANE);
				Thread.sleep(600);
				
				for (int i = 0; i < purplemiddle.length; i++) {
					inv.getItem(purplemiddle[i]).setType(Material.MAGENTA_STAINED_GLASS_PANE);
					if (i-1 >= 0) inv.getItem(purplemiddle[i-1]).setType(Material.PURPLE_STAINED_GLASS_PANE);
					Thread.sleep(150);

				}
				
				inv.getItem(purplemiddle[purplemiddle.length-1]).setType(Material.PURPLE_STAINED_GLASS_PANE);
				Thread.sleep(450);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	

}
