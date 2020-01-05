package cs2321;

/**
 * @author Siddhesh Mahadeshwar
 * CS2321, Fall 2019
 * Data last modified: 10/29/2019
 */

public class HeapPQSort<K extends Comparable<K>> extends PQSort<K> implements Sorter<K> {
	
	@TimeComplexity("O(nlog(n))")
	public void sort(K[] array) {
		PQSort<K> pq = new PQSort<K>();
		HeapPQ<K,K> ordpq = new HeapPQ<K,K>();
		pq.sort(array, ordpq);
	}
	
	public static void main (String[] args) {
		HeapPQSort<Integer> test = new HeapPQSort<Integer>();
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
