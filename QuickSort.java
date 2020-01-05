package cs2321;

/**
 * @author Siddhesh Mahadeshwar
 * CS2321, Fall 2019
 * Data last modified: 10/29/2019
 */

public class QuickSort<E extends Comparable<E>> implements Sorter<E> {

	@TimeComplexity("O(n^2)")
	@TimeComplexityExpected("O(nlog(n))")
	public void sort(E[] array) {
		quickSort(array, 0, array.length - 1);
	}
	
	public void quickSort (E[] array, int p, int q) {
		if (p<q) {
			int r = partition(array, p, q);
			quickSort(array, p, r-1);
			quickSort(array, r+1, q);
		}
	}
	
	public int partition (E[] array, int p, int q) {
		int l = p;
		int r = q-1;
		E pivot = array[q];
		while(l <= r) {
			while ((l <= r) && (array[l].compareTo(pivot) <= 0)) {
				l++;
			}
			
			while ((l <= r) && (array[r].compareTo(pivot) >= 0)) {
				r--;
			}
			
			if (l <= r) {
				E temp = array[r];
				array[r] = array[l];
				array[l] = temp;
				l++;
				r--;
			}
		}
		
		E temp = array[q];
		array[q] = array[l];
		array[l] = temp;
		return l;
	}
	
	public static void main (String[] args) {
		QuickSort<Integer> test = new QuickSort<Integer>();
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
