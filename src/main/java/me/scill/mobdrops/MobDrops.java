package me.scill.mobdrops;

import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import me.scill.mobdrops.commands.ReloadCommand;
import me.scill.mobdrops.listeners.DropsListener;

public class MobDrops extends JavaPlugin {
	
	private ItemLoot itemLoot;

	public void onEnable() {
		saveDefaultConfig();
		reloadMobDrops();

		// Listeners
		getServer().getPluginManager().registerEvents(new DropsListener(this), this);

		// Commands
        getCommand("mobdrops").setExecutor(new ReloadCommand(this));

    	getServer().getConsoleSender().sendMessage("MobDrops has been enabled!");
	}

	public void onDisable() {
		itemLoot = null;
    	getServer().getConsoleSender().sendMessage("MobDrops has been disabled!!");
	}
	
	public void reloadMobDrops() {
		itemLoot = new ItemLoot(this);
	}
	
	public Map<EntityType, Map<String, DropCollection<Integer>>> getMobDrops() {
		return itemLoot.getItemLoot();
	}
}