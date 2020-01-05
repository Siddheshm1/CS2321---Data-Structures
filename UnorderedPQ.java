package cs2321;

import java.util.Comparator;

import cs2321.OrderedPQ.DefaultComparator;
import net.datastructures.*;
/**
 * A PriorityQueue based on an Unordered Doubly Linked List. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author: Siddhesh Mahadeshwar
 */

public class UnorderedPQ<K,V> implements PriorityQueue<K,V> {

	private DoublyLinkedList <Entry<K,V>> list = new DoublyLinkedList<>();

	/** Creates an empty priority queue based on the natural ordering of its keys */
	public UnorderedPQ() {
		super();
		comp = new DefaultComparator<K>();
	}

	private Comparator <K> comp;

	
	/**
	 * Does the role of the defaultComparator for this class
	 * @param <E>: takes in a generic so this method can be implemented for multiple purposes
	 */
	public class DefaultComparator <E> implements Comparator<E> {
		public int compare (E a, E b) throws ClassCastException {
			return ((Comparable<E>)a).compareTo(b);
		}
	}

	public UnorderedPQ(Comparator<K> c) {
		comp = c;
	}
	/** Returns the number of items in the priority queue. */
	@Override
	public int size() {
		return list.size();
	}

	/** checks whether the queue is empty */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/** Method for comparing two entries according to key */
	protected int compare(Entry<K,V> a, Entry<K,V> b) {
		return comp.compare(a.getKey(),b.getKey());
	}

	/** Returns the Position of an entry having minimal key. */
	private Position<Entry<K,V>> findMin() {
		Position<Entry<K,V>> small = list.first();
		for (Position<Entry<K,V>>walk : list.positions()) {
			if (compare(walk.getElement(), small.getElement()) < 0) {
				small = walk;
			}
		}
		return small;

	}

	/** Inserts a key-value pair and returns the entry created. 
	 *  TCJ: constant time complexity as no loop are present and method runs constant number of times
	 * */
	@Override
	@TimeComplexity("O(1)")
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		Entry<K,V> newest = new PQEntry<>(key, value);
		list.addLast(newest);

		return newest;
	}

	/** Returns (but does not remove) an entry with minimal key. 
	 *  TCJ: complexity is n because the method only runs n times, so basically the number of times it is called
	 */
	@Override
	@TimeComplexity("O(n)")
	public Entry<K, V> min() {
		if (list.isEmpty()) return null;
		return findMin().getElement();
	}

	/** Removes and returns an entry with minimal key. 
	 *  TCJ: complexity is n because the method only runs n times, so basically the number of times it is called
	 */
	@Override
	@TimeComplexity("O(n)")
	public Entry<K, V> removeMin() {
		if (list.isEmpty()) return null;
		return list.remove(findMin());
	}

	/** Determines whether a key is valid. */
	protected boolean checkKey (K key) throws IllegalArgumentException {
		try {
			return (comp.compare(key,key) == 0);

		} catch (ClassCastException e) {
			throw new IllegalArgumentException ("Incompatible key");
		}
	}



}
