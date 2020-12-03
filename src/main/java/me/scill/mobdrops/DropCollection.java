package me.scill.mobdrops;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class DropCollection {

	private final NavigableMap<Double, Integer> drops = new TreeMap<>();
	private final Random random = new Random();
	private double totalPercentages = 0;

	/**
	 * Adds a new drop to the Drop Collection.
	 * @param itemAmount amount of items
	 * @param itemPercent chance that the specified amount of items will drop
	 */
	public void addDrop(final Integer itemAmount, final double itemPercent) {
		if (itemPercent <= 0)
			return;
		totalPercentages += itemPercent;
		drops.put(totalPercentages, itemAmount);
	}

	/**
	 * Retrieves a random drop amount in the  Drop Collection,
	 * or 0 if the collection is empty.
	 * @return a random drop
	 */
	public int getRandomDrop() {
		if (drops.isEmpty())
			return 0;
		return drops.higherEntry(random.nextDouble() * totalPercentages).getValue();
	}
}