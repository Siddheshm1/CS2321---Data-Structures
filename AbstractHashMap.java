package cs2321;

import net.datastructures.Entry;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/11/2019
 */

public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {
	protected int n = 0;
	protected int capacity;
	protected static int DefaultCapacity = 17;
	protected double loadfactor = 0.75;
	public AbstractHashMap(int cap) {
		capacity = cap;
		createTable();
	}
	public AbstractHashMap() {
		this(DefaultCapacity);
	}
	public int size() {
		return n;
	}
	public V bucketGet(K key) {
		return get(key);
	}
	public V bucketRemove(K key) {
		return remove(key);
	}
	public V bucketPut(K key, V value) {
		V answer = put(key, value);
		if (n > loadfactor) {
			resize(2 * capacity - 1);
		}
		return answer;
	}
	private void resize(int newCap) {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
		for (Entry<K,V> e : entrySet()) {
			buffer.addLast(e);
		}
		capacity = newCap;
		createTable();
		n = 0;
		for (Entry<K,V> e : buffer) {
			put(e.getKey(), e.getValue());
		}
	}
	protected abstract void createTable();
	public abstract V get(K key);
	public abstract V put(K key, V value);
	public abstract V remove(K key);
	
}
