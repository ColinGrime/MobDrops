package me.scill.mobdrops;

import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import me.scill.mobdrops.commands.ReloadCommand;
import me.scill.mobdrops.listeners.DropsListener;

public class MobDrops extends JavaPlugin {
	
	private ItemDrops itemDrops;

	public void onEnable() {
		saveDefaultConfig();
		reloadMobDrops();

		// Listeners
		getServer().getPluginManager().registerEvents(new DropsListener(this), this);

		// Commands
        getCommand("mobdrops").setExecutor(new ReloadCommand(this));
	}

	public void onDisable() {
		itemDrops = null;
	}

	/**
	 * Reloads the Mob Drops configuration with new drops.
	 */
	public void reloadMobDrops() {
		itemDrops = new ItemDrops(getConfig());
	}

	/**
	 * Gets a Map of the current mob drops on the server.
	 * @return current mob drops
	 */
	public Map<EntityType, Map<String, DropCollection>> getMobDrops() {
		return itemDrops.getAllMobDrops();
	}
}