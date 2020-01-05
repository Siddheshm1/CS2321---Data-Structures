package cs2321;

/**
 * @author Siddhesh Mahadeshwar
 * CS2321, Fall 2019
 * Data last modified: 10/29/2019
 */

public class InPlaceInsertionSort<K extends Comparable<K>> implements Sorter<K> {

	/**
	 * sort - Perform an in-place insertion sort
	 * @param array - Array to sort
	 */
	
	@TimeComplexity("O(n^2)")
	public void sort(K[] array) {
		for (int i = 1; i < array.length; i++) {
			K value;
			value = array[i];
			int j =  i -1;
			while ( j >= 0 && (array[j].compareTo(value) > 0)) {
				array[j+1] = array[j];
				j = j-1;
			}
			array[j+1] = value;
		}
		
	}
	
	public static void main (String[] args) {
		InPlaceInsertionSort<Integer> test = new InPlaceInsertionSort<Integer>();
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
