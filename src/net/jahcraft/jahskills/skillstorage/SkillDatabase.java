package net.jahcraft.jahskills.skillstorage;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.jahcraft.jahskills.main.Main;
import net.jahcraft.jahskills.perks.Perk;
import net.jahcraft.jahskills.skills.SkillType;

public class SkillDatabase {
	
	private static String url = "jdbc:mysql://localhost:3306/jahskills";
	private static String login = "jahcraft";
	private static String pwd = "jahcraft";
	private static String port = "3306";
	private static boolean mySQL = false;
	private static Connection con;
	
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
	protected static HashMap<Player, SkillType> mainSkills;
	
	public static boolean setupConnection() {
		String dbtype = Main.data.getConfig().getString("database.type");
		if (dbtype.equalsIgnoreCase("mysql")) {
			login = Main.data.getConfig().getString("database.mysql.username");
			pwd = Main.data.getConfig().getString("database.mysql.password");
			port = Main.data.getConfig().getString("database.mysql.port");
			url = "jdbc:mysql://localhost:" + port + "/jahskills";
			mySQL = true;

			con = getConnection();
			if (con == null) {
				Bukkit.getLogger().severe("[JahSkills] Unable to connect to MySQL database! Credentials are likely invalid!");
				return false;
			}
			return true;
		} else if (dbtype.equalsIgnoreCase("sqlite")) {
			Bukkit.getLogger().info("PATH: " + Main.getPlugin(Main.class).getDataFolder().getAbsolutePath());
            url = "jdbc:sqlite:" + Main.getPlugin(Main.class).getDataFolder().getAbsolutePath() + System.getProperty("file.separator") + "jahskills.db";
            mySQL = false;
			con = getConnection();
			return true;
		} else {
			return false;
		}
	}
	
	private static Connection getConnection() {
		Connection con = null;
		
		try {
			if (mySQL) {
				con = DriverManager.getConnection(url, login, pwd);
			} else {
				//SQLite
				con = DriverManager.getConnection(url);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}

	public static void sendQuery(String query) {
		try {
			con.createStatement().execute(query);
		} catch (SQLNonTransientConnectionException e) {
			con = getConnection();
			sendQuery(query);
		} catch (SQLException e) {
			warn(e, query);
		} 
	}
	
	public static void sendUnsafeQuery(String query) throws SQLException {
		try {
			con.createStatement().execute(query);
		} catch (SQLNonTransientConnectionException e) {
			con = getConnection();
			sendUnsafeQuery(query);
		}
	}
	
	public static ResultSet sendResultQuery(String query) {
		ResultSet result = null;
		try {
			result = con.createStatement().executeQuery(query);
		} catch (SQLNonTransientConnectionException e) {
			con = getConnection();
			sendQuery(query);
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
	
	public static String getUsername(String uuid) {
		try {
			ResultSet result = sendResultQuery("select name from usernames where uuid = '" + uuid + "'");
			if (result.next()) {
				return result.getString(1);
			} else {
				return uuid;
			}
		} catch (SQLException e) {
			Bukkit.getLogger().warning("Error loading username data for: " + uuid);
			e.printStackTrace();
			return uuid;
		} 	
	}
	
	public static String getUUID(String username) {
		try {
			ResultSet result = sendResultQuery("select uuid from usernames where name = '" + username + "'");
			if (result.next()) {
				return result.getString(1);
			} else {
				return username;
			}
		} catch (SQLException e) {
			Bukkit.getLogger().warning("Error loading uuid data for: " + username);
			e.printStackTrace();
			return username;
		} 	
	}
	
	public static void setupDatabase() {
		Bukkit.getLogger().info("Setting up database...");
		try {
			sendUnsafeQuery("create table mainskills(uuid varchar(36), skill varchar(36), primary key ( uuid ) )");
			sendUnsafeQuery("create table ownedPerks(uuid varchar(36), perk varchar(36))");
			sendUnsafeQuery("create table activePerks(uuid varchar(36), perk varchar(36))");
			sendUnsafeQuery("create table butcherlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table cavemanlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table naturalistlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table huntsmanlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table harvesterlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table intellectuallevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table explorerlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table survivalistlevel(uuid varchar(36), level int, primary key ( uuid ) )");
			sendUnsafeQuery("create table locations (world varchar(36), x int, y int, z int)");
			sendUnsafeQuery("create table userskills (uuid varchar(36), primary key (uuid))");
			sendUnsafeQuery("create table usernames (uuid varchar(36), name varchar(36), primary key (uuid))");
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
			sendUnsafeQuery("drop table mainskills");
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
			sendUnsafeQuery("drop table usernames");
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
		
		con = getConnection();
		skillLevel.put(p, Integer.parseInt(getData(p, "userlevels")));
		skillProgress.put(p, BigDecimal.valueOf(Double.parseDouble(getData(p, "userprogress"))));
		skillPoints.put(p, Integer.parseInt(getData(p, "userpoints")));
		butcherLevel.put(p, Integer.parseInt(getLevel(p, "butcherlevel")));
		cavemanLevel.put(p, Integer.parseInt(getLevel(p, "cavemanlevel")));
		explorerLevel.put(p, Integer.parseInt(getLevel(p, "explorerlevel")));
		harvesterLevel.put(p, Integer.parseInt(getLevel(p, "harvesterlevel")));
		huntsmanLevel.put(p, Integer.parseInt(getLevel(p, "huntsmanlevel")));
		intellectualLevel.put(p, Integer.parseInt(getLevel(p, "intellectuallevel")));
		naturalistLevel.put(p, Integer.parseInt(getLevel(p, "naturalistlevel")));
		survivalistLevel.put(p, Integer.parseInt(getLevel(p, "survivalistlevel")));
		activePerks.put(p, getActivePerks(p));
		ownedPerks.put(p, getOwnedPerks(p));
		mainSkills.put(p, getMainSkill(p));
		
		
	}
	
	public static SkillType getMainSkill(Player p) {
		try {
			return SkillType.valueOf(getMainSkillData(p));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static SkillType getMainSkill(String uuid) {
		try {
			return SkillType.valueOf(getMainSkillData(uuid));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static List<String> getSkillTop() {
		List<String> players = new ArrayList<>();
		ResultSet result = null;
		try {
			result = sendResultQuery("select * from userlevels order by value desc");
		} catch (Exception e) {
			e.printStackTrace();
			return players;
		}
		
		try {
			int i = 0;
			while (result.next() && i < 10) {
				players.add(getUsername(result.getString(1)) + " - " + result.getString(2));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;
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
	
	public static List<Perk> getActivePerks(String uuid) {
		List<Perk> perks = new ArrayList<>();
		ResultSet result = null;
		try {
			result = sendResultQuery("select perk from activePerks where uuid = '" + uuid + "'");
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
	
	public static String[] getActivePerks(String uuid, SkillType type) {
		List<Perk> activePerks = getActivePerks(uuid);
		String[] perks = new String[9];
		int count = 0;
		for (Perk perk : SkillManager.getPerks(type)) {
			if (activePerks.contains(perk)) {
				perks[count] = SkillManager.getFormattedName(perk);
				count++; 
			}
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
				try {
					perks.add(Perk.valueOf(result.getString(1)));
				} catch(Exception e) {
					
				}
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
	
	public static String getData(String uuid, String table) {
		try {
			ResultSet result = sendResultQuery("select value from " + table + " where uuid = '" + uuid + "'");
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
			Bukkit.getLogger().warning("Error loading " + table + "data for: " + uuid);
			e.printStackTrace();
			if (table.equals("userpoints") || table.equals("userprogress")) {
				return "0";
			} else {
				return "1";

			}
		} 
	}
	
	private static String getMainSkillData(Player p) {
		try {
			ResultSet result = sendResultQuery("select skill from " + "mainskills" + " where uuid = '" + p.getUniqueId() + "'");
			if (result.next()) {
				return result.getString(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			return null;
		} 
	}
	
	public static String getMainSkillData(String uuid) {
		try {
			ResultSet result = sendResultQuery("select skill from " + "mainskills" + " where uuid = '" + uuid + "'");
			if (result.next()) {
				return result.getString(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			return null;
		} 
	}
	
	public static String getLevel(Player p, String table) {
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
	
	public static String getLevel(String uuid, String table) {
		try {
			ResultSet result = sendResultQuery("select level from " + table + " where uuid = '" + uuid + "'");
			if (result.next()) {
				return result.getString(1);
			} else {
				return "0";
			}
		} catch (SQLException e) {
			Bukkit.getLogger().warning("Error loading " + table + "data for: " + uuid);
			e.printStackTrace();
			return "0";
		} 
	}
	
	private static void clearData(Player p) {
		sendQuery("delete from mainskills where uuid = '" + p.getUniqueId() + "'");
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
		sendQuery("delete from usernames where uuid = '" + p.getUniqueId() + "'");
		
	}
	
	public static void clearData(String uuid) {
		sendQuery("delete from mainskills where uuid = '" + uuid + "'");
		sendQuery("delete from ownedPerks where uuid = '" + uuid + "'");
		sendQuery("delete from activePerks where uuid = '" + uuid + "'");
		sendQuery("delete from userskills where uuid = '" + uuid + "'");
		sendQuery("delete from userpoints where uuid = '" + uuid + "'");
		sendQuery("delete from userlevels where uuid = '" + uuid + "'");
		sendQuery("delete from userprogress where uuid = '" + uuid + "'");
		sendQuery("delete from butcherlevel where uuid = '" + uuid + "'");
		sendQuery("delete from cavemanlevel where uuid = '" + uuid + "'");
		sendQuery("delete from explorerlevel where uuid = '" + uuid + "'");
		sendQuery("delete from harvesterlevel where uuid = '" + uuid + "'");
		sendQuery("delete from huntsmanlevel where uuid = '" + uuid + "'");
		sendQuery("delete from survivalistlevel where uuid = '" + uuid + "'");
		sendQuery("delete from naturalistlevel where uuid = '" + uuid + "'");
		sendQuery("delete from intellectuallevel where uuid = '" + uuid + "'");
//		sendQuery("delete from usernames where uuid = '" + uuid + "'");
		
	}

	public static void save(Player player) {
		if (!activePerks.containsKey(player) || !ownedPerks.containsKey(player)) {
			Bukkit.getLogger().warning("Couldn't save skill data for " + player.getName() + "! Skipping player...");
			return;
		}
		
		con = getConnection();
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
		sendQuery("insert into usernames values ('" + player.getUniqueId() + "', '" + player.getName() + "')");
		if (SkillManager.getMainSkill(player) != null) {
			sendQuery("insert into mainskills values ('" + player.getUniqueId() + "', '" + SkillManager.getMainSkill(player).toString() + "')");
		}

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
	public static void grownBlock(Location location) {
		sendQuery("delete from locations where world = '" + location.getWorld().getName() + "' and "
				+ "x = " + location.getBlockX() + " and "
				+ "y = " + location.getBlockY() + " and "
				+ "z = " + location.getBlockZ());
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
