package net.jahcraft.jahskills.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import net.jahcraft.jahskills.commands.Afflict;
import net.jahcraft.jahskills.commands.ClaimMainSkill;
import net.jahcraft.jahskills.commands.SkillDB;
import net.jahcraft.jahskills.commands.SkillQuery;
import net.jahcraft.jahskills.commands.Skills;
import net.jahcraft.jahskills.config.DataManager;
import net.jahcraft.jahskills.crafting.RecipeUtil;
import net.jahcraft.jahskills.effects.ButcherEffects;
import net.jahcraft.jahskills.effects.CavemanEffects;
import net.jahcraft.jahskills.effects.ExplorerEffects;
import net.jahcraft.jahskills.effects.HarvesterEffects;
import net.jahcraft.jahskills.effects.HuntsmanEffects;
import net.jahcraft.jahskills.effects.IntellectualEffects;
import net.jahcraft.jahskills.effects.NaturalistEffects;
import net.jahcraft.jahskills.effects.SurvivalistEffects;
import net.jahcraft.jahskills.gui.listeners.ButcherMenuListener;
import net.jahcraft.jahskills.gui.listeners.CavemanMenuListener;
import net.jahcraft.jahskills.gui.listeners.ExplorerMenuListener;
import net.jahcraft.jahskills.gui.listeners.HarvesterMenuListener;
import net.jahcraft.jahskills.gui.listeners.HuntsmanMenuListener;
import net.jahcraft.jahskills.gui.listeners.IntellectualMenuListener;
import net.jahcraft.jahskills.gui.listeners.NaturalistMenuListener;
import net.jahcraft.jahskills.gui.listeners.SkillMenuListener;
import net.jahcraft.jahskills.gui.listeners.SurvivalistMenuListener;
import net.jahcraft.jahskills.papi.PAPIExpansion;
import net.jahcraft.jahskills.skillstorage.LoadSave;
import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.skilltracking.BlockTracker;
import net.jahcraft.jahskills.skilltracking.ExpEvents;
import net.jahcraft.jahskills.skilltracking.ProgressBar;
import net.jahcraft.jahskills.util.BiomeFinder;
import net.jahcraft.jahskills.util.IndustrialRevolution;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	public static Economy eco;
	public static Chat chat;
	public static DataManager data;
	ArrayList<BukkitTask> pluginTasks = new ArrayList<>();

	@Override
	public void onEnable() {
		
		Main.data = new DataManager(this);
		
		data.reloadConfig();
		
		if (!SkillDatabase.setupConnection()) {
			
			Bukkit.getLogger().severe("[JahSkills] Couldn't reach the database! Disabling JahSkills!");
			Bukkit.getLogger().severe("[JahSkills] Check your config for database errors!");
			getServer().getPluginManager().disablePlugin(this);
			return;
			
		}
		
		if (!setupVaultHooks()) {
			
			Bukkit.getLogger().info("Vault not detected! Disabling JahSkills!");
			getServer().getPluginManager().disablePlugin(this);
			return;
			
		}
		
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPIExpansion(this).register();
		}
		
		plugin = this;
		
		//JAHSKILLS
		try {
			
			RecipeUtil.registerRecipes();
			
			BiomeFinder.initBiomeMenu();
			IndustrialRevolution.initRedstoneMenu();

			//SUBCOMMANDS
			SkillDatabase.setupDatabase();
			SkillManager.loadSkills();
			
			
			initializePerkEffects();
			initializeDataTrackers();
			initializeGUI();
			initializeCommands();
			initializeRepeatingTasks();

		} catch (Exception e) {

			Bukkit.getLogger().warning("Failed to load JahSkills!");
			e.printStackTrace();

		}	
	}
	
	private void initializeRepeatingTasks() {
		
		try {
			pluginTasks.add(CavemanEffects.getCaveVisionTask().runTaskTimer(plugin, 0, 5));
			pluginTasks.add(NaturalistEffects.getHippyHealingTask().runTaskTimer(plugin, 0, 60));
		} catch (Exception e) {
			
		}
		
		
	}
	
	private void initializeCommands() {
		
		//EXPLICIT COMMANDS (INTENDED FOR NORMAL USE)
		getCommand("skilldb").setExecutor((CommandExecutor)new SkillDB());
		getCommand("skillquery").setExecutor((CommandExecutor)new SkillQuery());
		getCommand("skills").setExecutor((CommandExecutor)new Skills());
		
		//IMPLICIT AND DEBUG COMMANDS
		getCommand("claimmainskill").setExecutor((CommandExecutor)new ClaimMainSkill());
		getCommand("afflict").setExecutor((CommandExecutor)new Afflict());
		
		//SET TAB EXECUTORS
		getCommand("skilldb").setTabCompleter((TabCompleter)new SkillDB());
		getCommand("afflict").setTabCompleter((TabCompleter)new Afflict());

	}

	private void initializeGUI() {
		getServer().getPluginManager().registerEvents(new SkillMenuListener(), this);
		getServer().getPluginManager().registerEvents(new ButcherMenuListener(), this);
		getServer().getPluginManager().registerEvents(new CavemanMenuListener(), this);
		getServer().getPluginManager().registerEvents(new NaturalistMenuListener(), this);
		getServer().getPluginManager().registerEvents(new HuntsmanMenuListener(), this);
		getServer().getPluginManager().registerEvents(new HarvesterMenuListener(), this);
		getServer().getPluginManager().registerEvents(new IntellectualMenuListener(), this);
		getServer().getPluginManager().registerEvents(new ExplorerMenuListener(), this);
		getServer().getPluginManager().registerEvents(new SurvivalistMenuListener(), this);		
	}

	private void initializeDataTrackers() {
		getServer().getPluginManager().registerEvents(new BlockTracker(), this);
		getServer().getPluginManager().registerEvents(new ExpEvents(), this);
		getServer().getPluginManager().registerEvents(new LoadSave(), this);		
	}

	private void initializePerkEffects() {
		getServer().getPluginManager().registerEvents(new ButcherEffects(), this);
		getServer().getPluginManager().registerEvents(new CavemanEffects(), this);
		getServer().getPluginManager().registerEvents(new NaturalistEffects(), this);
		getServer().getPluginManager().registerEvents(new HuntsmanEffects(), this);
		getServer().getPluginManager().registerEvents(new HarvesterEffects(), this);
		getServer().getPluginManager().registerEvents(new IntellectualEffects(), this);
		getServer().getPluginManager().registerEvents(new ExplorerEffects(), this);
		getServer().getPluginManager().registerEvents(new SurvivalistEffects(), this);
	}

	@Override 
	public void onDisable() {

		try {
			RecipeUtil.unregisterRecipes();
			SkillDatabase.flushDatabase();
			ProgressBar.disposeBars();
			SurvivalistEffects.clearClones();
		} catch (NullPointerException e) {
			
		}
		
		
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.closeInventory();
		}
		
		for (Horse h : ExplorerEffects.wildHorses) {
			ExplorerEffects.removeHorse(h);
		}
		
		for (Horse horse : ExplorerEffects.boostedHorses) {
			horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() / 2);
			ExplorerEffects.boostedHorses.remove(horse);
		}
		
//		SkillDatabase.clearDatabase();
		
		for (BukkitTask task : pluginTasks) {
			task.cancel();
		}
		
		Bukkit.getLogger().info("JahSkills Unloaded and Disabled!");
		
	}
	
	private boolean setupVaultHooks() {
		
		RegisteredServiceProvider<Chat> chatservice = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		RegisteredServiceProvider<Economy> economy = getServer().
				getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		
		if (economy != null)
			eco = economy.getProvider();
		if (chatservice != null)
			chat = chatservice.getProvider();
		return (eco != null) && (chat != null);
		
	}

}
