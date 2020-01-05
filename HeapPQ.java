package cs2321;

import java.util.Comparator;

import net.datastructures.*;
/**
 * A Adaptable PriorityQueue based on an heap. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author: Siddhesh Mahadeshwar
 */

public class HeapPQ<K,V> implements AdaptablePriorityQueue<K,V> {

	protected static class PQEntry <K,V> implements Entry<K,V> {
		private K k;
		private V v;
		public PQEntry (K key, V value) {
			k = key;
			v = value;
		}

		public K getKey() {
			return k;
		}

		public V getValue() {
			return v;
		}

		public void setKey(K key) {
			k = key;
		}

		protected void setValue (V value) {
			v = value;
		}
	}

	/**
	 * Method for comparing two entries according to key
	 * @param a: takes in the first entry to compare
	 * @param b: takes in the second entry to compare
	 * @return: returns the comparison of the 2 compared keys
	 */
	@TimeComplexity("O(1)")
	protected int compare(Entry<K,V> a, Entry<K,V> b) {
		return comp.compare(a.getKey(),b.getKey());
	}


	/**
	 * Determines whether a key is valid
	 * @param key: take in a key parameter
	 * @return: returns the result of the check if a key is valid after comparing to self
	 * @throws IllegalArgumentException: throws this if key is not valid
	 */
	protected boolean checkKey (K key) throws IllegalArgumentException {
		try {
			return (comp.compare(key,key) == 0);

		} catch (ClassCastException e) {
			throw new IllegalArgumentException ("Incompatible key");
		}
	}

	private Comparator <K> comp;


	protected ArrayList<PQEntry<K,V>> heap = new ArrayList<>();

	public HeapPQ() {
		super();
		comp = new DefaultComparator<K>();
	}

	/**
	 * Constructor for this class
	 * @param c: takes in the comparator as a parameter
	 */
	public HeapPQ(Comparator<K> c) {
		comp = c;// add c argument in super() if needed
	}

	/**
	 * Does the role of the defaultComparator for this class
	 * @param <E>: takes in a generic so this method can be implemented for multiple purposes
	 */
	@TimeComplexity("O(1)")
	public class DefaultComparator <E> implements Comparator<E> {
		public int compare (E a, E b) throws ClassCastException {
			return ((Comparable<E>)a).compareTo(b);
		}
	}

	@TimeComplexity("O(1)")
	public class StringLengthComparator implements Comparator<String> {
		public int compare(String a, String b) {
			if (a.length() < b.length()) return 0;
			else return 1;
		}
	}

	protected int parent(int j) {
		return (j-1)/2;
	}

	protected int left(int j) {
		return 2*j + 1;
	}

	protected int right(int j) {
		return 2*j + 2;
	}

	/**
	 * Checks if the left entry exists
	 * @param j: takes in an int
	 * @return: returns if the left entry is smaller than the heap's size
	 */
	protected boolean hasLeft(int j) {
		return left(j) < heap.size();
	}
	
	/**
	 * Checks if the right entry exists
	 * @param j: takes in an int
	 * @return: returns if the right entry is smaller than the heap's size
	 */
	protected boolean hasRight(int j) {
		return right(j) < heap.size();
	}

	
	/**
	 * Exchanges the entries at indices i and j of the array list.
	 * @param i: takes in an int for location
	 * @param j: takes in an int for second location
	 */
	protected void swap (int i, int j) {
		PQEntry<K,V> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}


	/**
	 * The entry should be bubbled up to its appropriate position 
	 * @param int move the entry at index j higher if necessary, to restore the heap property
	 */
	public void upheap(int j){
		while (j>0) {
			int p = parent(j);
			if (compare(heap.get(j), heap.get(p)) >= 0) break;
			swap(j,p);
			j=p;
		}
	}

	/**
	 * The entry should be bubbled down to its appropriate position 
	 * @param int move the entry at index j lower if necessary, to restore the heap property
	 */

	public void downheap(int j){
		while (hasLeft(j)) {
			int leftIndex = left(j);
			int smallChildIndex = leftIndex;
			if (hasRight(j)) {
				int rightIndex = right(j);
				if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0)
					smallChildIndex = rightIndex;
			}
			if (compare(heap.get(smallChildIndex), heap.get(j)) >= 0) break;
			swap (j, smallChildIndex);
			j= smallChildIndex;
		}
	}


	/** returns the size of the priority queue */
	@Override
	public int size() {
		return heap.size();
	}

	/** checks if the priority queue is empty */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}


	/** Inserts a key-value pair and returns the entry created. 
	 *  TCJ: complexity is log n as there are multiple method calls and because the program must iterate 
	 *  through all entries each time
	 */
	@Override
	@TimeComplexity("O(log n)")
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		PQEntry<K,V> newest = new PQEntry<>(key, value);
		heap.addLast(newest);
		upheap(heap.size() - 1);
		return newest;
	}

	/** Returns (but does not remove) an entry with minimal key (if any). 
	 *  TCJ: constant time complexity as no loop are present and method runs constant number of times
	 */
	@Override
	@TimeComplexity("O(1)") 
	public Entry<K, V> min() {
		if (heap.isEmpty()) 
			return null;
		return heap.get(0);
	}

	/** Removes and returns an entry with minimal key (if any). 
	 *  TCJ: complexity is log n as there are multiple method calls and because the program must iterate 
	 *  through all entries each time
	 */
	@Override
	@TimeComplexity("O(log n)")
	public Entry<K, V> removeMin() {
		if (heap.isEmpty()) return null;
		Entry<K,V> answer = heap.get(0);
		swap(0, heap.size() - 1);
		heap.remove(heap.size() - 1);
		downheap(0);
		return answer;
	}

	/** Removes the given entry from the priority queue. 
	 *  TCJ: complexity is log n as there are multiple method calls and because the program must iterate 
	 *  through all entries each time
	 */
	@Override
	@TimeComplexity("O(log n)")
	public void remove(Entry<K, V> entry) throws IllegalArgumentException {
		for (int i = 0; i < size(); i++) {
			if (entry.equals(heap.get(i))) {
				swap(i, heap.size() - 1);
				heap.removeLast();
				downheap(i);
				return;
			}
		}
		throw new IllegalArgumentException();
	}

	/** Replaces the key of an entry 
	 *  TCJ: complexity is log n as there are multiple method calls and because the program must iterate 
	 *  through all entries 
	 */
	@Override
	@TimeComplexity("O(log n)")
	public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
		for (int i = 0; i < size(); i++) {
			((PQEntry<K,V>) entry).setKey(key);
			if (entry.equals(heap.get(i))) {
				if (comp.compare(heap.get(i).getKey(), heap.get(parent(i)).getKey()) < 0) {
					upheap(i);
				} else {
					downheap(i);
				}
				return;
			}
		}
		throw new IllegalArgumentException();

	}

	/** Replaces the value of an entry
	 *  TCJ: constant time complexity as no loop are present and method runs constant number of times
	 */
	@Override
	@TimeComplexity("O(1)")
	public void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
		for (int i = 0; i < size(); i++) {
			if (entry.equals(heap.get(i))) {
				((PQEntry<K,V>) entry).setValue(value);
				heap.set(i, (PQEntry<K,V>)entry);
				return;
			}
		}
		throw new IllegalArgumentException();
	}
}
