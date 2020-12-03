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
		if (plugin.getMobDrops().containsKey(event.getEntity().getType())) {
			event.getDrops().clear();
			dropLoot(event);
		}
	}

	/**
	 * Drops items / XP of the specified mob.
	 * @param event event for entity deaths
	 */
	private void dropLoot(final EntityDeathEvent event) {
		final Location location = event.getEntity().getLocation();
		// Iterates through the potential drops of the mob.
		for (Map.Entry<String, DropCollection> mobDrops : plugin.getMobDrops().get(event.getEntity().getType()).entrySet()) {
			final int dropAmount = mobDrops.getValue().getRandomDrop();
			if (mobDrops.getKey().equals("XP"))
				event.setDroppedExp(dropAmount);
			else if (dropAmount != 0)
				location.getWorld().dropItem(location, new ItemStack(Material.getMaterial(mobDrops.getKey()), dropAmount));
		}
	}
}