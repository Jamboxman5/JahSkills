package net.jahcraft.jahskills.crafting;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import net.jahcraft.jahskills.main.Main;

public class SalvageOperations {

	public static HashSet<Recipe> recipes = new HashSet<>();
	public static HashSet<NamespacedKey> keys = new HashSet<>();
	
	public static void registerRecipes() {
		
		Recipe recipe = netherpicksalvage();
		recipes.add(recipe);
		
		for (Recipe r : recipes) {
			Bukkit.addRecipe(r);
		}
	}
	private static ShapelessRecipe netherpicksalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "netheritepick_salvage");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 3));
		
		recipe.addIngredient(1, Material.NETHERITE_PICKAXE);
		
		keys.add(key);
		return recipe;
	}
}
