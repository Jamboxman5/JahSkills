package net.jahcraft.jahskills.main;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.jahcraft.jahskills.commands.KnockOut;
import net.jahcraft.jahskills.commands.SkillDB;
import net.jahcraft.jahskills.commands.SkillQuery;
import net.jahcraft.jahskills.commands.Skills;
import net.jahcraft.jahskills.effects.ButcherEffects;
import net.jahcraft.jahskills.gui.listeners.ButcherMenuListener;
import net.jahcraft.jahskills.gui.listeners.CavemanMenuListener;
import net.jahcraft.jahskills.gui.listeners.SkillMenuListener;
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
					
					getServer().getPluginManager().registerEvents(new BlockTracker(), this);
					getServer().getPluginManager().registerEvents(new ExpEvents(), this);
					getServer().getPluginManager().registerEvents(new LoadSave(), this);
					getServer().getPluginManager().registerEvents(new SkillMenuListener(), this);
					getServer().getPluginManager().registerEvents(new ButcherMenuListener(), this);
					getServer().getPluginManager().registerEvents(new CavemanMenuListener(), this);
					getServer().getPluginManager().registerEvents(new ButcherEffects(), this);
					
					getCommand("skilldb").setExecutor((CommandExecutor)new SkillDB());
					getCommand("skillquery").setExecutor((CommandExecutor)new SkillQuery());
					getCommand("skills").setExecutor((CommandExecutor)new Skills());
					getCommand("knockout").setExecutor((CommandExecutor)new KnockOut());

				} catch (Exception e) {

					Bukkit.getLogger().warning("Failed to load JahSkills!");
					e.printStackTrace();

				}	
	}
	
	@Override 
	public void onDisable() {

		SkillDatabase.flushDatabase();
		ProgressBar.disposeBars();
//		SkillDatabase.clearDatabase();
		
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
