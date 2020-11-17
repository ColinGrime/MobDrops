package me.scill.mobdrops.listeners;

import me.scill.mobdrops.DropCollection;
import me.scill.mobdrops.MobDrops;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class DropsListener implements Listener {

	private final MobDrops plugin;

	public DropsListener(final MobDrops plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onDeath(final EntityDeathEvent event) {
		if (plugin.getMobDrops().containsKey(event.getEntity().getType()))
			dropLoot(event);
	}

	private void dropLoot(final EntityDeathEvent event) {
		event.getDrops().clear();
		final Location location = event.getEntity().getLocation();

		for (Map.Entry<String, DropCollection<Integer>> entry : plugin.getMobDrops().get(event.getEntity().getType()).entrySet()) {
			final int amount = entry.getValue().next();
			if (amount == 0);
			else if (entry.getKey().equals("XP"))
				event.setDroppedExp(amount);
			else
				location.getWorld().dropItem(location, new ItemStack(Material.getMaterial(entry.getKey()), amount));
		}
	}
}