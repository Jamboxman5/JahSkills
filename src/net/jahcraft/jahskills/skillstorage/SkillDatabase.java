package net.jahcraft.jahskills.skillstorage;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;

public class SkillDatabase {
	
	private static final String url = "jdbc:mysql://localhost:3306/jahskills";
	private static final String login = "jahcraft";
	private static Connection con = getConnection();
	
	protected static HashMap<Player, List<Perk>> ownedPerks;
	protected static HashMap<Player, List<Perk>> activePerks;
	protected static HashMap<Player, Integer> skillLevel;
	protected static HashMap<Player, Integer> butcherLevel;
	protected static HashMap<Player, Integer> cavemanLevel;
	protected static HashMap<Player, Integer> naturalistLevel;
	protected static HashMap<Player, Integer> huntsmanLevel;
	protected static HashMap<Player, Integer> harvesterLevel;
	protected static HashMap<Player, Integer> intellectualLevel;
	protected static HashMap<Player, Integer> explorerLevel;
	protected static HashMap<Player, Integer> survivalistLevel;
	protected static HashMap<Player, BigDecimal> skillProgress;
	protected static HashMap<Player, Integer> skillPoints;
	
	private static Connection getConnection() {
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url, login, login);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}

	public static void sendQuery(String query) {
		try {
			con.createStatement().execute(query);
		} catch (SQLException e) {
			warn(e, query);
		}
	}
	
	public static void sendUnsafeQuery(String query) throws SQLException {
		con.createStatement().execute(query);
	}
	
	public static ResultSet sendResultQuery(String query) {
		ResultSet result;
		try {
			result = con.createStatement().executeQuery(query);
		} catch (SQLException e) {
			warn(e, query);
			return null;
		}
		return result;
	}
	
	private static void warn(SQLException e, String query) {
		Bukkit.getLogger().warning("Error sending SQL query: " + query);
		e.printStackTrace();
	}
	
	public static void setupDatabase() {
		Bukkit.getLogger().info("Setting up database...");
		try {
			sendUnsafeQuery("create table ownedPerks(uuid varchar(36), perk varchar(36))");
			sendUnsafeQuery("create table activePerks(uuid varchar(36), perk varchar(36))");
			sendUnsafeQuery("create table butcherlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table cavemanlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table naturalistlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table hunstmanlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table harvesterlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table intellectuallevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table explorerlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table survivalistlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table locations (world varchar(36), x int, y int, z int)");
			sendUnsafeQuery("create table userskills (uuid varchar(36), primary key (uuid))");
			sendUnsafeQuery("create table userpoints (uuid varchar(36), value smallint, primary key (uuid))");
			sendUnsafeQuery("create table userlevels (uuid varchar(36), value smallint, primary key (uuid))");
			sendUnsafeQuery("create table userprogress (uuid varchar(36), value double(5,4), primary key (uuid))");
		} catch (SQLException e) {
//			e.printStackTrace();
			Bukkit.getLogger().info("Database Detected! Skipping...");
			return;
		}
		Bukkit.getLogger().info("Database Created!");
	}
	
	public static void clearDatabase() {
		Bukkit.getLogger().info("Removing database...");
		try {
			sendUnsafeQuery("drop table ownedPerks");
			sendUnsafeQuery("drop table activePerks");
			sendUnsafeQuery("drop table userskills");
			sendUnsafeQuery("drop table userpoints");
			sendUnsafeQuery("drop table userlevels");
			sendUnsafeQuery("drop table userprogress");
			sendUnsafeQuery("drop table butcherlevel");
			sendUnsafeQuery("drop table cavemanlevel");
			sendUnsafeQuery("drop table naturalistlevel");
			sendUnsafeQuery("drop table hunstmanlevel");
			sendUnsafeQuery("drop table harvesterlevel");
			sendUnsafeQuery("drop table intellectuallevel");
			sendUnsafeQuery("drop table explorerlevel");
			sendUnsafeQuery("drop table survivalistlevel");
			sendUnsafeQuery("drop table locations");
		} catch (SQLException e) {
			Bukkit.getLogger().info("Error Detected! Reporting...");
			e.printStackTrace();
			return;
		}
		Bukkit.getLogger().info("Database Cleared!");
	}
	
	public static void flushDatabase() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p != null) save(p);
		}
	}
	
	public static void load(Player p) {
		
		skillLevel.put(p, Integer.parseInt(getData(p, "userlevels")));
		skillProgress.put(p, BigDecimal.valueOf(Double.parseDouble(getData(p, "userprogress"))));
		skillPoints.put(p, Integer.parseInt(getData(p, "userpoints")));
		butcherLevel.put(p, Integer.parseInt(getLevel(p, "butcherlevel")));
		cavemanLevel.put(p, 1);
		explorerLevel.put(p, 1);
		harvesterLevel.put(p, 1);
		huntsmanLevel.put(p, 1);
		intellectualLevel.put(p, 1);
		naturalistLevel.put(p, 1);
		survivalistLevel.put(p, 1);
		activePerks.put(p, getActivePerks(p));
		ownedPerks.put(p, getOwnedPerks(p));
		
		
	}
	
	private static List<Perk> getActivePerks(Player p) {
		List<Perk> perks = new ArrayList<>();
		ResultSet result = null;
		try {
			result = sendResultQuery("select perk from activePerks where uuid = '" + p.getUniqueId() + "'");
		} catch (Exception e) {
			e.printStackTrace();
			return perks;
		}
		
		try {
			while (result.next()) {
				perks.add(Perk.valueOf(result.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return perks;
	}
	
	private static List<Perk> getOwnedPerks(Player p) {
		List<Perk> perks = new ArrayList<>();
		ResultSet result = null;
		try {
			result = sendResultQuery("select perk from ownedPerks where uuid = '" + p.getUniqueId() + "'");
		} catch (Exception e) {
			return perks;
		}
		try {
			while (result.next()) {
				perks.add(Perk.valueOf(result.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return perks;
	}

	private static String getData(Player p, String table) {
		try {
			ResultSet result = sendResultQuery("select value from " + table + " where uuid = '" + p.getUniqueId() + "'");
			if (result.next()) {
				return result.getString(1);
			} else {
				if (table.equals("userpoints") || table.equals("userprogress")) {
					return "0";
				} else {
					return "1";
				}
			}
		} catch (SQLException e) {
			Bukkit.getLogger().warning("Error loading " + table + "data for: " + p.getName());
			e.printStackTrace();
			if (table.equals("userpoints") || table.equals("userprogress")) {
				return "0";
			} else {
				return "1";

			}
		} 
	}
	
	private static String getLevel(Player p, String table) {
		try {
			ResultSet result = sendResultQuery("select level from " + table + " where uuid = '" + p.getUniqueId() + "'");
			if (result.next()) {
				return result.getString(1);
			} else {
				return "0";
			}
		} catch (SQLException e) {
			Bukkit.getLogger().warning("Error loading " + table + "data for: " + p.getName());
			e.printStackTrace();
			return "0";
		} 
	}
	
	private static void clearData(Player p) {
		sendQuery("delete from ownedPerks where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from activePerks where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from userskills where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from userpoints where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from userlevels where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from userprogress where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from butcherlevel where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from cavemanlevel where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from explorerlevel where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from harvesterlevel where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from huntsmanlevel where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from survivalistlevel where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from naturalistlevel where uuid = '" + p.getUniqueId() + "'");
		sendQuery("delete from intellectuallevel where uuid = '" + p.getUniqueId() + "'");
		
	}

	public static void save(Player player) {
		clearData(player);
		for (Perk p : activePerks.get(player)) {
			sendQuery("insert into activePerks values ('" + player.getUniqueId() + "'" + ",'" + p.toString() + "')");
		}
		for (Perk p : ownedPerks.get(player)) {
			sendQuery("insert into ownedPerks values ('" + player.getUniqueId() + "'" + ",'" + p.toString() + "')");
		}
		sendQuery("insert into userskills values ('" + player.getUniqueId() + "'" + ")");
		sendQuery("insert into userpoints values ('" + player.getUniqueId() + "', " + SkillManager.getPoints(player) + ")");
		sendQuery("insert into userlevels values ('" + player.getUniqueId() + "', " + SkillManager.getLevel(player) + ")");
		sendQuery("insert into userprogress values ('" + player.getUniqueId() + "', " + SkillManager.getProgress(player) + ")");
		sendQuery("insert into butcherlevel values ('" + player.getUniqueId() + "', " + SkillManager.getLevel(player, SkillType.BUTCHER) + ")");
		sendQuery("insert into cavemanlevel values ('" + player.getUniqueId() + "', " + SkillManager.getLevel(player, SkillType.CAVEMAN) + ")");
		sendQuery("insert into explorerlevel values ('" + player.getUniqueId() + "', " + SkillManager.getLevel(player, SkillType.EXPLORER) + ")");
		sendQuery("insert into harvesterlevel values ('" + player.getUniqueId() + "', " + SkillManager.getLevel(player, SkillType.HARVESTER) + ")");
		sendQuery("insert into huntsmanlevel values ('" + player.getUniqueId() + "', " + SkillManager.getLevel(player, SkillType.HUNTSMAN) + ")");
		sendQuery("insert into survivalistlevel values ('" + player.getUniqueId() + "', " + SkillManager.getLevel(player, SkillType.SURVIVALIST) + ")");
		sendQuery("insert into naturalistlevel values ('" + player.getUniqueId() + "', " + SkillManager.getLevel(player, SkillType.NATURALIST) + ")");
		sendQuery("insert into intellectuallevel values ('" + player.getUniqueId() + "', " + SkillManager.getLevel(player, SkillType.INTELLECTUAL) + ")");

	}

	public static boolean isNatural(Location location) {
		try {
			if (sendResultQuery("select * from locations where world = '" + location.getWorld().getName() + "' and "
								+ "x = " + location.getBlockX() + " and "
								+ "y = " + location.getBlockY() + " and "
								+ "z = " + location.getBlockZ()).next()) {
				return false;
			} else return true;
		} catch (SQLException e) {
			return true;
		}

	}

	public static void placedBlock(Location location) {
		sendQuery("insert ignore into locations values ('" + location.getWorld().getName() + "'," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + ")");
	}

	public static HashMap<Player, Integer> getSkill(SkillType skill) {
		switch(skill) {
		case BUTCHER:
			return butcherLevel;
		case CAVEMAN:
			return cavemanLevel;
		case EXPLORER:
			return explorerLevel;
		case HARVESTER:
			return harvesterLevel;
		case HUNTSMAN:
			return huntsmanLevel;
		case INTELLECTUAL:
			return intellectualLevel;
		case NATURALIST:
			return naturalistLevel;
		case SURVIVALIST:
			return survivalistLevel;
		
		}
		return null;
	}
	
	
	
}
