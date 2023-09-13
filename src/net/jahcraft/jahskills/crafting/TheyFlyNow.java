package net.jahcraft.jahskills.crafting;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import net.jahcraft.jahskills.main.Main;

public class TheyFlyNow {
	
	public static HashSet<Recipe> recipes = new HashSet<>();
	public static HashSet<NamespacedKey> keys = new HashSet<>();
	
	public static void registerRecipes() {
		
		Recipe recipe = generateElytraRecipe();
		recipes.add(recipe);
		
		for (Recipe r : recipes) {
			Bukkit.addRecipe(r);
		}
	}
	private static ShapedRecipe generateElytraRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "elytra_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.ELYTRA));
		recipe.shape("MMM","MNM","F F");
		recipe.setIngredient('M', Material.PHANTOM_MEMBRANE);
		recipe.setIngredient('N', Material.NETHERITE_INGOT);
		recipe.setIngredient('F', Material.FEATHER);
		keys.add(key);
		return recipe;
	}

}
