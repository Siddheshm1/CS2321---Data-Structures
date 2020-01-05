package cs2321;

import net.datastructures.Entry;

/**
 * This is the Fractional Knapsack Problem implementing the Greedy Algorithm.
 * @author Siddhesh Mahadeshwar
 * Date Modified: 10/17/2019
 */


public class FractionalKnapsack {



	/**
	 * Goal: Choose items with maximum total benefit but with weight at most W.
	 *       You are allowed to take fractional amounts from items.
	 *       
	 * @param items items[i][0] is weight for item i
	 *              items[i][1] is benefit for item i
	 * @param knapsackWeight
	 * @return The maximum total benefit. Please use double type operation. For example 5/2 = 2.5
	 * 		 
	 */
	private static class Item {
		int weight = 0;
		int benefit = 0;
		double value = 0;

		private Item(int w, int b) {
			weight = w;
			benefit = b;
			value = (double)b / (double)w;
		}
	}
	public static double MaximumValue(int[][] items, int knapsackWeight){

		int itemWeight;
		int itemBenefit;

		HeapPQ<Double, Item> PriorityQ = new HeapPQ<Double, Item>(new MaximumComparator<Double>());
		int remainderWeight = knapsackWeight;
		double totalValue = 0;

		for(int i = 0; i < items.length; i++) { // keeps looping until it reaches the max number of items
			itemWeight = items[i][0];
			itemBenefit = items[i][1];
			Item item = new Item(itemWeight, itemBenefit);
			PriorityQ.insert(item.value, item);

		}

		while(remainderWeight > 0) { // as long as remainder weight is greater than 0, keep running
			Entry<Double,Item> e = PriorityQ.removeMin();
			if (e == null) {
				break;
			}
			Item item = e.getValue();

			if (item.weight < remainderWeight) { // as long as item weight is less than remaining weight, keep going
				totalValue = totalValue + item.benefit;
				remainderWeight = remainderWeight - item.weight;
			} else {
				totalValue = totalValue + item.value * remainderWeight;
				break;
			}
		}
		return totalValue;
	}
}
