package me.scill.mobdrops;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

public class ItemDrops {

	@Getter
	private final Map<EntityType, Map<String, DropCollection>> allMobDrops = new HashMap<>();

	public ItemDrops(final FileConfiguration config) {
		// If the entity is in the config, set its mob drops.
		Arrays.stream(EntityType.values())
				.filter(entityType -> config.contains(entityType.toString().toLowerCase()))
				.forEach(entityType -> addMobDrops(config, entityType));
	}

	private void addMobDrops(final FileConfiguration config, final EntityType entityType) {
		final Map<String, DropCollection> mobDrops = new HashMap<>();
		final String path = entityType.toString().toLowerCase();

		// If item is XP or a material, add its Drop Collection.
		config.getConfigurationSection(path).getKeys(true).stream()
				.filter(item -> item.equalsIgnoreCase("xp")
						|| Material.matchMaterial(item.toUpperCase()) != null)
				.forEach(item -> mobDrops.put(item.toUpperCase(), getDropCollection(config.getStringList(path + "." + item))));

		allMobDrops.put(entityType, mobDrops);
	}

	private DropCollection getDropCollection(final List<String> itemChances) {
		final DropCollection dropCollection = new DropCollection();

		// Adds drops to the Drop Collection.
		for (String itemChance : itemChances) {
			try {
				int itemAmount = Integer.parseInt(itemChance.split(" ")[0]);
				double itemPercent = Double.parseDouble(itemChance.split(" ")[1]);
				dropCollection.addDrop(itemAmount, itemPercent);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		}

		return dropCollection;
	}
}
