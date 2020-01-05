package cs2321;

/**
 * @author Siddhesh Mahadeshwar
 * CS2321, Fall 2019
 * Data last modified: 10/29/2019
 */

public class InPlaceSelectionSort<K extends Comparable<K>> implements Sorter<K> {

	/**
	 * sort - Perform an in-place selection sort
	 * @param array - Array to sort
	 */
	
	@TimeComplexity("O(n^2)")
	public void sort(K[] array) {
		int lowIndex;
		K temp;
		for (int i = 0; i < array.length - 1; i++) {
			lowIndex = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[j].compareTo(array[lowIndex]) < 0) {
					lowIndex = j;
				}
			}
			
			temp = array[lowIndex];
			array[lowIndex] = array[i];
			array[i] = temp;
		}
	}
	
	public static void main (String[] args) {
		InPlaceSelectionSort<Integer> test = new InPlaceSelectionSort<Integer>();
		Integer array[] = new Integer[100000];
		for (int i = 0; i < 100000; i++) {
			array[i] = 20 + (int) (Math.random() * ((90 - 30) + 1));
		}
		long beginTime = System.nanoTime();
		test.sort(array);
		long endTime = System.nanoTime();
		long time = endTime - beginTime;
		System.out.println(time/1000000);
	}

}
