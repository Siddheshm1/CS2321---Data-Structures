package cs2321;

import java.util.Comparator;

import cs2321.HeapPQ.DefaultComparator;
import net.datastructures.*;
/**
 * A PriorityQueue based on an ordered Doubly Linked List. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author: Siddhesh Mahadeshwar
 */

public class OrderedPQ<K,V> implements PriorityQueue<K,V> {

	private DoublyLinkedList <Entry<K,V>> list = new DoublyLinkedList<>();

	private Comparator <K> comp;

	/** Creates an empty priority queue based on the natural ordering of its keys. */
	public OrderedPQ() {
		super();
		comp = new DefaultComparator<K>();
	}

	/**
	 * Does the role of the defaultComparator for this class
	 * @param <E>: takes in a generic so this method can be implemented for multiple purposes
	 */
	public class DefaultComparator <E> implements Comparator<E> {
		public int compare (E a, E b) throws ClassCastException {
			return ((Comparable<E>)a).compareTo(b);
		}
	}

	/**
	 * Constructor for this class
	 * @param c: takes in a comparator
	 */
	public OrderedPQ(Comparator<K> c) {
		comp = c;
	}

	/** Returns the number of items in the priority queue. */
	@Override
	public int size() {
		return list.size();

	}

	/** checks if the priority queue is empty */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/** Inserts a key-value pair and returns the entry created. 
	 *  TCJ: complexity is n because the method only runs n times, so basically the number of times it is called
	 */
	@Override
	@TimeComplexity("O(n)")
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		Entry<K,V> newest = new PQEntry<>(key,value);
		Position<Entry<K,V>> walk = list.last();
		while (walk != null && compare(newest, walk.getElement()) < 0)
			walk = list.before(walk);
		if (walk == null)
			list.addFirst(newest);
		else
			list.addAfter(walk, newest);
		return newest;
	}

	/** Returns (but does not remove) an entry with minimal key. 
	 *  TCJ: constant time complexity as no loop are present and method runs constant number of times
	 */
	@Override
	@TimeComplexity("O(1)")
	public Entry<K, V> min() {
		if (list.isEmpty()) return null;
		return list.first().getElement();
	}

	/** Removes and returns an entry with minimal key. 
	 *  TCJ: constant time complexity as no loop are present and method runs constant number of times
	 */
	@Override
	@TimeComplexity("O(1)")
	public Entry<K, V> removeMin() {
		if (list.isEmpty()) return null;
		return list.remove(list.first());
	}

	/** Determines whether a key is valid. */
	protected boolean checkKey (K key) throws IllegalArgumentException {
		try {
			return (comp.compare(key,key) == 0);

		} catch (ClassCastException e) {
			throw new IllegalArgumentException ("Incompatible key");
		}
	}

	/** Method for comparing two entries according to key 
	 *  TCJ: constant time complexity as no loop are present and method runs constant number of times
	 * */
	@TimeComplexity("O(1)")
	protected int compare(Entry<K,V> a, Entry<K,V> b) {
		return comp.compare(a.getKey(),b.getKey());
	}

}
