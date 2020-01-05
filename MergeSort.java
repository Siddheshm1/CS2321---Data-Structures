package cs2321;

/**
 * @author Siddhesh Mahadeshwar
 * CS2321, Fall 2019
 * Data last modified: 10/29/2019
 */

import java.util.Arrays;

public class MergeSort<E extends Comparable<E>> implements Sorter<E> {

	@TimeComplexity("O(nlog(n))")
	public void sort(E[] array) {
		int n = array.length;
		mergeSort(array, n);
	}
	
	public void mergeSort (E[] array, int n) {
		if (n == 1) {
			return;
		}
		int mid = n/2;
		E left[] = Arrays.copyOfRange(array, 0, mid);
		E right[] = Arrays.copyOfRange(array, mid, n);
		mergeSort(left, left.length);
		mergeSort(right, right.length);
		merge(array, left, right);
	}
	
	public void merge (E[] array, E[] left, E[] right) {
		int i = 0;
		int j = 0;
		int k = 0;
		
		int n1 = left.length;
		int n2 = right.length;
		
		while (i < n1 && j < n2) {
			if (left[i].compareTo(right[j]) < 0) {
				array[k] = left[i];
				i++;
			} else {
				array[k] = right[j];
				j++;
			}
			 k++;
		}
		
		while (i < n1) {
			array[k] = left[i];
			i++;
			k++;
		}
		
		while (j < n2) {
			array[k] = right[j];
			j++;
			k++;
		}
		
	}
	
	public static void main (String[] args) {
		MergeSort<Integer> test = new MergeSort<Integer>();
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

