package me.scill.mobdrops;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class DropCollection<Integer> {

	private final NavigableMap<Double, Integer> map = new TreeMap<>();
	private final Random random = new Random();
	private double total = 0;

	public void add(final double weight, final Integer result) {
		if (weight <= 0)
			return;
		total += weight;
		map.put(total, result);
	}

	public Integer next() {
		return map.higherEntry(random.nextDouble() * total).getValue();
	}
}