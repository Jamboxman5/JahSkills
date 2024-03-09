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
		
		recipes.add(netherpicksalvage());
		recipes.add(netherswordsalvage());
		recipes.add(nethershovelsalvage());
		recipes.add(netheraxesalvage());
		recipes.add(netherhoesalvage());
		recipes.add(netherhelmetsalvage());
		recipes.add(netherchestplatesalvage());
		recipes.add(netherleggingssalvage());
		recipes.add(netherbootssalvage());
		
		recipes.add(diamondpicksalvage());
		recipes.add(diamondswordsalvage());
		recipes.add(diamondshovelsalvage());
		recipes.add(diamondaxesalvage());
		recipes.add(diamondhoesalvage());
		recipes.add(diamondhelmetsalvage());
		recipes.add(diamondchestplatesalvage());
		recipes.add(diamondleggingssalvage());
		recipes.add(diamondbootssalvage());
		
		recipes.add(ironpicksalvage());
		recipes.add(ironswordsalvage());
		recipes.add(ironshovelsalvage());
		recipes.add(ironaxesalvage());
		recipes.add(ironhoesalvage());
		recipes.add(ironhelmetsalvage());
		recipes.add(ironchestplatesalvage());
		recipes.add(ironleggingssalvage());
		recipes.add(ironbootssalvage());
		
		recipes.add(goldpicksalvage());
		recipes.add(goldswordsalvage());
		recipes.add(goldshovelsalvage());
		recipes.add(goldaxesalvage());
		recipes.add(goldhoesalvage());
		recipes.add(goldhelmetsalvage());
		recipes.add(goldchestplatesalvage());
		recipes.add(goldleggingssalvage());
		recipes.add(goldbootssalvage());
		
		for (Recipe r : recipes) {
			Bukkit.addRecipe(r);
		}
	}
	
	//////////////////////////////////////
	
	private static ShapelessRecipe netherpicksalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_netheritepick");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 1));
		
		recipe.addIngredient(1, Material.NETHERITE_PICKAXE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe netherswordsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_netheritesword");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 1));
		
		recipe.addIngredient(1, Material.NETHERITE_SWORD);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe nethershovelsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_netheriteshovel");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 1));
		
		recipe.addIngredient(1, Material.NETHERITE_SHOVEL);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe netheraxesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_netheriteaxe");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 1));
		
		recipe.addIngredient(1, Material.NETHERITE_AXE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe netherhoesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_netheritehoe");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 1));
		
		recipe.addIngredient(1, Material.NETHERITE_HOE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe netherhelmetsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_netheritehelmet");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 1));
		
		recipe.addIngredient(1, Material.NETHERITE_HELMET);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe netherchestplatesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_netheritechestplate");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 1));
		
		recipe.addIngredient(1, Material.NETHERITE_CHESTPLATE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe netherleggingssalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_netheriteleggings");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 1));
		
		recipe.addIngredient(1, Material.NETHERITE_LEGGINGS);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe netherbootssalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_netheriteboots");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.NETHERITE_INGOT, 1));
		
		recipe.addIngredient(1, Material.NETHERITE_BOOTS);
		
		keys.add(key);
		return recipe;
	}
	
	////////////////////////////////////////////
	
	private static ShapelessRecipe diamondpicksalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_diamondpick");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DIAMOND, 2));
		
		recipe.addIngredient(1, Material.DIAMOND_PICKAXE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe diamondswordsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_diamondsword");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DIAMOND, 1));
		
		recipe.addIngredient(1, Material.DIAMOND_SWORD);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe diamondshovelsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_diamondshovel");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DIAMOND, 1));
		
		recipe.addIngredient(1, Material.DIAMOND_SHOVEL);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe diamondaxesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_diamondaxe");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DIAMOND, 2));
		
		recipe.addIngredient(1, Material.DIAMOND_AXE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe diamondhoesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_diamondhoe");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DIAMOND, 1));
		
		recipe.addIngredient(1, Material.DIAMOND_HOE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe diamondhelmetsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_diamondhelmet");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DIAMOND, 4));
		
		recipe.addIngredient(1, Material.DIAMOND_HELMET);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe diamondchestplatesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_diamondchestplate");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DIAMOND, 7));
		
		recipe.addIngredient(1, Material.DIAMOND_CHESTPLATE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe diamondleggingssalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_diamondleggings");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DIAMOND, 6));
		
		recipe.addIngredient(1, Material.DIAMOND_LEGGINGS);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe diamondbootssalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_diamondboots");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DIAMOND, 3));
		
		recipe.addIngredient(1, Material.DIAMOND_BOOTS);
		
		keys.add(key);
		return recipe;
	}
	
	////////////////////////////////////////////
	
	private static ShapelessRecipe ironpicksalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_ironpick");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.IRON_INGOT, 2));

		recipe.addIngredient(1, Material.IRON_PICKAXE);

		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe ironswordsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_ironsword");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.IRON_INGOT, 1));
		
		recipe.addIngredient(1, Material.IRON_SWORD);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe ironshovelsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_ironshovel");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.IRON_INGOT, 1));
		
		recipe.addIngredient(1, Material.IRON_SHOVEL);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe ironaxesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_ironaxe");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.IRON_INGOT, 2));
		
		recipe.addIngredient(1, Material.IRON_AXE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe ironhoesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_ironhoe");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.IRON_INGOT, 1));
		
		recipe.addIngredient(1, Material.IRON_HOE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe ironhelmetsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_ironhelmet");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.IRON_INGOT, 4));
		
		recipe.addIngredient(1, Material.IRON_HELMET);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe ironchestplatesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_ironchestplate");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.IRON_INGOT, 7));
		
		recipe.addIngredient(1, Material.IRON_CHESTPLATE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe ironleggingssalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_ironleggings");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.IRON_INGOT, 6));
		
		recipe.addIngredient(1, Material.IRON_LEGGINGS);
		
		keys.add(key);
		return recipe;
	}
		private static ShapelessRecipe ironbootssalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_ironboots");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.IRON_INGOT, 3));
		
		recipe.addIngredient(1, Material.IRON_BOOTS);
		
		keys.add(key);
		return recipe;
	}
		
	////////////////////////////////////////////
			
	private static ShapelessRecipe goldpicksalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_goldpick");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.GOLD_INGOT, 2));
		
		recipe.addIngredient(1, Material.GOLDEN_PICKAXE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe goldswordsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_goldsword");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.GOLD_INGOT, 1));
		
		recipe.addIngredient(1, Material.GOLDEN_SWORD);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe goldshovelsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_goldshovel");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.GOLD_INGOT, 1));
		
		recipe.addIngredient(1, Material.GOLDEN_SHOVEL);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe goldaxesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_goldaxe");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.GOLD_INGOT, 2));
		
		recipe.addIngredient(1, Material.GOLDEN_AXE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe goldhoesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_goldhoe");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.GOLD_INGOT, 1));
		
		recipe.addIngredient(1, Material.GOLDEN_HOE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe goldhelmetsalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_goldhelmet");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.GOLD_INGOT, 4));
		
		recipe.addIngredient(1, Material.GOLDEN_HELMET);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe goldchestplatesalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_goldchestplate");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.GOLD_INGOT, 7));
		
		recipe.addIngredient(1, Material.GOLDEN_CHESTPLATE);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe goldleggingssalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_goldleggings");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.GOLD_INGOT, 6));
		
		recipe.addIngredient(1, Material.GOLDEN_LEGGINGS);
		
		keys.add(key);
		return recipe;
	}
	private static ShapelessRecipe goldbootssalvage() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "salvageoperation_goldboots");
		ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.GOLD_INGOT, 3));
		
		recipe.addIngredient(1, Material.GOLDEN_BOOTS);
		
		keys.add(key);
		return recipe;
	}
	
	
}
