package net.jahcraft.jahskills.papi;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.skillstorage.SkillDatabase;

public class PAPIExpansion extends PlaceholderExpansion {

//	private final Main plugin;
//	
	public PAPIExpansion(Main plugin) {
//		this.plugin = plugin;
	}
	
	@Override
	public @NotNull String getIdentifier() {
		// TODO Auto-generated method stub
		return "jahskills";
	}

	@Override
	public @NotNull String getAuthor() {
		// TODO Auto-generated method stub
		return "Jamboxman5";
	}

	@Override
	public @NotNull String getVersion() {
		// TODO Auto-generated method stub
		return "1.0.0";
	}
	
	@Override
	public @Nullable String onRequest(OfflinePlayer player, String params) {
		
		//NAMES
		
		if (params.equalsIgnoreCase("skilltop1name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 0) return SkillDatabase.getSkillTop().get(0).split(" - ")[0];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop2name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 1) return SkillDatabase.getSkillTop().get(1).split(" - ")[0];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop3name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 2) return SkillDatabase.getSkillTop().get(2).split(" - ")[0];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop4name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 3) return SkillDatabase.getSkillTop().get(3).split(" - ")[0];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop5name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 4) return SkillDatabase.getSkillTop().get(4).split(" - ")[0];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop6name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 5) return SkillDatabase.getSkillTop().get(5).split(" - ")[0];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop7name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 6) return SkillDatabase.getSkillTop().get(6).split(" - ")[0];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop8name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 7) return SkillDatabase.getSkillTop().get(7).split(" - ")[0];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop9name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 8) return SkillDatabase.getSkillTop().get(8).split(" - ")[0];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop10name")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 9) return SkillDatabase.getSkillTop().get(9).split(" - ")[0];
			return "-";
		}
		
		//VALUES
		if (params.equalsIgnoreCase("skilltop1value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 0) return SkillDatabase.getSkillTop().get(0).split(" - ")[1];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop2value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 1) return SkillDatabase.getSkillTop().get(1).split(" - ")[1];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop3value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 2) return SkillDatabase.getSkillTop().get(2).split(" - ")[1];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop4value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 3) return SkillDatabase.getSkillTop().get(3).split(" - ")[1];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop5value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 4) return SkillDatabase.getSkillTop().get(4).split(" - ")[1];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop6value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 5) return SkillDatabase.getSkillTop().get(5).split(" - ")[1];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop7value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 6) return SkillDatabase.getSkillTop().get(6).split(" - ")[1];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop8value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 7) return SkillDatabase.getSkillTop().get(7).split(" - ")[1];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop9value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 8) return SkillDatabase.getSkillTop().get(8).split(" - ")[1];
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop10value")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 9) return SkillDatabase.getSkillTop().get(9).split(" - ")[1];
			return "-";
		}
		
		//FULL
		if (params.equalsIgnoreCase("skilltop1")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 0) return SkillDatabase.getSkillTop().get(0);
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop2")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 1) return SkillDatabase.getSkillTop().get(1);
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop3")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 2) return SkillDatabase.getSkillTop().get(2);
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop4")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 3) return SkillDatabase.getSkillTop().get(3);
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop5")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 4) return SkillDatabase.getSkillTop().get(4);
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop6")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 5) return SkillDatabase.getSkillTop().get(5);
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop7")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 6) return SkillDatabase.getSkillTop().get(6);
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop8")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 7) return SkillDatabase.getSkillTop().get(7);
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop9")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 8) return SkillDatabase.getSkillTop().get(8);
			return "-";
		}
		if (params.equalsIgnoreCase("skilltop10")) {
			List<String> skillTop = SkillDatabase.getSkillTop();
			if (skillTop.size() > 9) return SkillDatabase.getSkillTop().get(9);
			return "-";
		}
		return null;
	}
	
	@Override
    public boolean persist() {
        return false; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

}
