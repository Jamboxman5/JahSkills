package net.jahcraft.jahskills.crafting;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import net.jahcraft.jahskills.main.Main;

public class FamilyRecipes {

	public static HashSet<Recipe> recipes = new HashSet<>();
	public static HashSet<NamespacedKey> keys = new HashSet<>();
	
	public static void registerRecipes() {
		
		Recipe recipe = generateSaddleRecipe();
		recipes.add(recipe);
		recipe = generateNameTagRecipe();
		recipes.add(recipe);
		recipe = generateChainmailHelmetRecipe();
		recipes.add(recipe);
		recipe = generateChainmailChestplateRecipe();
		recipes.add(recipe);
		recipe = generateChainmailLeggingsRecipe();
		recipes.add(recipe);
		recipe = generateChainmailBootsRecipe();
		recipes.add(recipe);
		recipe = generateTridentRecipe();
		recipes.add(recipe);
		
		for (Recipe r : recipes) {
			Bukkit.addRecipe(r);
		}
	}
	private static ShapedRecipe generateSaddleRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "saddle_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.SADDLE));
		recipe.shape(" L ","LLL", "I I");
		recipe.setIngredient('L', Material.LEATHER);
		recipe.setIngredient('I', Material.IRON_INGOT);
		keys.add(key);
		return recipe;
	}
	private static ShapedRecipe generateNameTagRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "nametag_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.NAME_TAG));
		recipe.shape("S  "," PP");
		recipe.setIngredient('S', Material.STRING);
		recipe.setIngredient('P', Material.PAPER);
		keys.add(key);
		return recipe;
	}
	private static ShapedRecipe generateChainmailBootsRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "chainboots_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.CHAINMAIL_BOOTS));
		recipe.shape("C C","C C");
		recipe.setIngredient('C', Material.CHAIN);
		keys.add(key);
		return recipe;
	}
	private static ShapedRecipe generateChainmailHelmetRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "chainhelmet_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.CHAINMAIL_HELMET));
		recipe.shape("CCC","C C");
		recipe.setIngredient('C', Material.CHAIN);
		keys.add(key);
		return recipe;
	}
	private static ShapedRecipe generateChainmailChestplateRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "chainchest_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		recipe.shape("C C","CCC", "CCC");
		recipe.setIngredient('C', Material.CHAIN);
		keys.add(key);
		return recipe;
	}
	private static ShapedRecipe generateChainmailLeggingsRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "chainlegs_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.CHAINMAIL_LEGGINGS));
		recipe.shape("CCC","C C","C C");
		recipe.setIngredient('C', Material.CHAIN);
		keys.add(key);
		return recipe;
	}
	private static ShapedRecipe generateTridentRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "trident_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.TRIDENT));
		recipe.shape(" QQ"," DQ","D  ");
		recipe.setIngredient('Q', Material.QUARTZ);
		recipe.setIngredient('D', Material.DIAMOND);
		keys.add(key);
		return recipe;
	}
	
}
