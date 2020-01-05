package cs2321;

/**
 * @author Siddhesh Mahadeshwar
 * CS2321, Fall 2019
 * Data last modified: 10/29/2019
 */

import cs2321.HeapPQ.PQEntry;

public class InPlaceHeapSort<K extends Comparable<K>> implements Sorter<K> {
	int heapsize = 0;
	/**
	 * sort - Perform an in-place heap sort
	 * @param array - Array to sort
	 */
		
	@TimeComplexity("O(nlog(n))")
	public void sort(K[] array) {
		int n = array.length;
		heapsize = array.length;
		
		for (int i = (n/2) - 1; i >= 0; i--) {
			maxHeapify(i, array);
		}
		
		for (int i = n-1; i>= 1; i--) {
				K temp = array[0];
				array[0] = array[i];
				array[i] = temp;
				heapsize--;
				maxHeapify(0, array);
		}
	}
	
	private int left(int i) {
		return 2*i + 1;
	}

	public int right(int i) {
		return 2*i + 2;
	}
	
	
	public void maxHeapify(int i, K[] array) {
		int largest = i;
		int left = left(i);
		int right = right(i);
		
		if ((left < heapsize) && array[left].compareTo(array[largest]) > 0) {
			largest = left;
		}
		if ((right < heapsize) && array[right].compareTo(array[largest]) > 0) {
			largest = right;
		}
		if ( largest != i) {
			K temp = array[i];
			array[i] = array[largest];
			array[largest] = temp;
			maxHeapify(largest, array);
		}
	}
	
	public static void main (String[] args) {
		InPlaceHeapSort<Integer> test = new InPlaceHeapSort<Integer>();
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
