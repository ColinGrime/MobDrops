package me.scill.mobdrops;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class ItemLoot {

	private final Map<EntityType, Map<String, DropCollection<Integer>>> mobDrops = new HashMap<>();
	private Map<String, DropCollection<Integer>> loot = new HashMap<>();

	public ItemLoot(final MobDrops plugin) {
		// If the entity is in the config, set its loot.
		Arrays.stream(EntityType.values())
				.filter(entityType -> plugin.getConfig().contains(entityType.toString().toLowerCase()))
				.forEach(entityType -> setLoot(plugin, entityType));
	}

	private void setLoot(final MobDrops plugin, final EntityType entityType) {
		this.loot = new HashMap<>();
		final String path = entityType.toString().toLowerCase();

		// If item is XP or a material, get its chance amounts.
		plugin.getConfig().getConfigurationSection(path).getKeys(true).stream()
				.filter(item -> item.equalsIgnoreCase("xp")
						|| Material.matchMaterial(item.toUpperCase()) != null)
				.forEach(item -> itemChances(item.toUpperCase(),
						plugin.getConfig().getStringList(path + "." + item)));

		this.mobDrops.put(entityType, loot);
	}

	private void itemChances(final String material, final List<String> list) {
		final DropCollection<Integer> itemChance = new DropCollection<>();

		// Gets the item's chance amounts.
		for (String chance : list) {
			try {
				int amount = Integer.parseInt(chance.split(" ")[0]);
				double percent = Double.parseDouble(chance.split(" ")[1]);
				itemChance.add(percent, amount);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		}

		loot.put(material, itemChance);
	}

	public Map<EntityType, Map<String, DropCollection<Integer>>> getItemLoot() {
		return mobDrops;
	}
}
