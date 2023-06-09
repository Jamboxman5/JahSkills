package net.jahcraft.jahskills.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import net.jahcraft.jahskills.commands.Afflict;
import net.jahcraft.jahskills.commands.ClaimMainSkill;
import net.jahcraft.jahskills.commands.SkillDB;
import net.jahcraft.jahskills.commands.SkillQuery;
import net.jahcraft.jahskills.commands.Skills;
import net.jahcraft.jahskills.effects.ButcherEffects;
import net.jahcraft.jahskills.effects.CavemanEffects;
import net.jahcraft.jahskills.effects.NaturalistEffects;
import net.jahcraft.jahskills.gui.listeners.ButcherMenuListener;
import net.jahcraft.jahskills.gui.listeners.CavemanMenuListener;
import net.jahcraft.jahskills.gui.listeners.ExplorerMenuListener;
import net.jahcraft.jahskills.gui.listeners.HarvesterMenuListener;
import net.jahcraft.jahskills.gui.listeners.HuntsmanMenuListener;
import net.jahcraft.jahskills.gui.listeners.IntellectualMenuListener;
import net.jahcraft.jahskills.gui.listeners.NaturalistMenuListener;
import net.jahcraft.jahskills.gui.listeners.SkillMenuListener;
import net.jahcraft.jahskills.gui.listeners.SurvivalistMenuListener;
import net.jahcraft.jahskills.skillstorage.LoadSave;
import net.jahcraft.jahskills.skillstorage.SkillDatabase;
import net.jahcraft.jahskills.skillstorage.SkillManager;
import net.jahcraft.jahskills.skilltracking.BlockTracker;
import net.jahcraft.jahskills.skilltracking.ExpEvents;
import net.jahcraft.jahskills.skilltracking.ProgressBar;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	public static Economy eco;
	ArrayList<BukkitTask> pluginTasks = new ArrayList<>();

	@Override
	public void onEnable() {
		
		if (!setupEconomy()) {
			
			Bukkit.getLogger().info("Economy not detected! Disabling JahCore!");
			getServer().getPluginManager().disablePlugin(this);
			return;
			
		}
		
		plugin = this;
		
		//JAHSKILLS
		try {

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
	}

	@Override 
	public void onDisable() {

		SkillDatabase.flushDatabase();
		ProgressBar.disposeBars();
//		SkillDatabase.clearDatabase();
		
		for (BukkitTask task : pluginTasks) {
			task.cancel();
		}
		
		Bukkit.getLogger().info("JahSkills Unloaded and Disabled!");
		
	}
	
	private boolean setupEconomy() {
		
		RegisteredServiceProvider<Economy> economy = getServer().
				getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		
		if (economy != null)
			eco = economy.getProvider();
		return (eco != null);
		
	}

}
