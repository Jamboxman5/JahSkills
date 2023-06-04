package net.jahcraft.jahskills.gui.animations;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SkillMenuAnim implements Runnable {

	Inventory inv;
	Player player;
	
	public SkillMenuAnim(Inventory i, Player p) {
		inv = i;
		player = p;
	}
	
	@Override
	public void run() {
				
		int slot1 = 4;
		int slot2 = 49;
		
		int[] right = {8,17,26,35,44,53};
		int[] top = {0,1,2,3,4,5,6,7,8};
		int[] bottom = {45,46,47,48,49,50,51,52,53};
		int[] left = {0,9,18,27,36,45};

		
		while (player.getOpenInventory().getTitle().contains("Skill")) {
			
			if (inv.getItem(slot1).getType() == Material.PURPLE_STAINED_GLASS_PANE) {
				inv.getItem(slot1).setType(Material.BLUE_STAINED_GLASS_PANE);
			}
			if (inv.getItem(slot2).getType() == Material.PURPLE_STAINED_GLASS_PANE) {
				inv.getItem(slot2).setType(Material.BLUE_STAINED_GLASS_PANE);
			}

			
			if (getList(top).contains(slot1) && !getList(right).contains(slot1)) {
				slot1++;
			}
			else if (getList(right).contains(slot1) && !getList(bottom).contains(slot1)) {
				slot1+= 9;
			}
			else if (getList(bottom).contains(slot1) && !getList(left).contains(slot1)) {
				slot1--;
			}
			else if (getList(left).contains(slot1) && !getList(top).contains(slot1)) {
				slot1-=9;
			}
			
			if (getList(top).contains(slot2) && !getList(right).contains(slot2)) {
				slot2++;
			}
			else if (getList(right).contains(slot2) && !getList(bottom).contains(slot2)) {
				slot2+= 9;
			}
			else if (getList(bottom).contains(slot2) && !getList(left).contains(slot2)) {
				slot2--;
			}
			else if (getList(left).contains(slot2) && !getList(top).contains(slot2)) {
				slot2-=9;
			}
			
			if (inv.getItem(slot1).getType() == Material.BLUE_STAINED_GLASS_PANE) {
				inv.getItem(slot1).setType(Material.PURPLE_STAINED_GLASS_PANE);
			}
			if (inv.getItem(slot2).getType() == Material.BLUE_STAINED_GLASS_PANE) {
				inv.getItem(slot2).setType(Material.PURPLE_STAINED_GLASS_PANE);
			}
						
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
		
	}
	
	List<Integer> getList(int[] array) {
		List<Integer> intList = new ArrayList<Integer>(array.length);
		for (int i : array)
		{
		    intList.add(i);
		}
		return intList;
	}

}
