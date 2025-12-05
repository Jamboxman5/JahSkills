package net.jahcraft.jahskills.crafting;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.util.Colors;

public class EMTCertified {

	public static HashSet<Recipe> recipes = new HashSet<>();
	public static HashSet<NamespacedKey> keys = new HashSet<>();
	
	public static void registerRecipes() {
		
		Recipe recipe = generateFirstAidKitRecipe();
		recipes.add(recipe);
		
		for (Recipe r : recipes) {
			Bukkit.addRecipe(r);
		}
	}
	private static ShapedRecipe generateFirstAidKitRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "first_aid_recipe");
		
		ItemStack result = new ItemStack(Material.GHAST_TEAR);
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(Colors.format("&dFirst Aid Kit"));
		meta.setCustomModelData(1);
		result.setItemMeta(meta);
		
		ShapedRecipe recipe = new ShapedRecipe(key, result);
		recipe.shape("III","MPT","III");
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('M', Material.GLISTERING_MELON_SLICE);
		recipe.setIngredient('P', Material.PAPER);
		recipe.setIngredient('T', Material.GHAST_TEAR);
		keys.add(key);
		return recipe;
	}
	
}
