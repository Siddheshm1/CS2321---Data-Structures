package cs2321;

import net.datastructures.*;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/11/2019
 */

public class HashMap<K, V> extends AbstractHashMap<K,V>{

	/* Use Array of UnorderedMap<K,V> for the Underlying storage for the map of entries.
	 * 
	 */
	private UnorderedMap<K,V>[] table;
	int 	size;  // number of mappings(entries) 
	int 	capacity; // The size of the hash table. 
	int     DefaultCapacity = 17; //The default hash table size

	/* Maintain the load factor <= 0.75.
	 * If the load factor is greater than 0.75, 
	 * then double the table, rehash the entries, and put then into new places. 
	 */
	double  loadfactor = 0.75;  

	/**
	 * Constructor that takes a hash size
	 * @param hashtable size: the number of buckets to initialize 
	 */
	@SuppressWarnings("unchecked")
	public HashMap(int hashtablesize) {
		capacity = hashtablesize;
		table = new UnorderedMap[capacity];
	}

	/**
	 * Constructor that takes no argument
	 * Initialize the hash table with default hash table size: 17
	 */
	@SuppressWarnings("unchecked")
	public HashMap() {
		capacity = DefaultCapacity;
		table = new UnorderedMap[capacity];
	}

	@SuppressWarnings("unchecked")
	protected void createTable() {
		table = (UnorderedMap<K,V>[]) new UnorderedMap[capacity];
	}
	/* This method should be called by map an integer to the index range of the hash table 
	 */
	private int hashValue(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}

	/*
	 * The purpose of this method is for testing if the table was doubled when rehashing is needed. 
	 * Return the the size of the hash table. 
	 * It should be 17 initially, after the load factor is more than 0.75, it should be doubled.
	 */
	public int tableSize() {
		return table.length;
	}


	@Override
	public int size() {
		return n;
	}

	@Override
	public boolean isEmpty() {
		return n == 0;
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

	/** Returns value associated with key k in bucket with hash value h, or else null. */
	@Override
	@TimeComplexityExpected("O(1)")
	public V get(K key) {
		/**
		 * TCJ
		 * 
		 * expected complexity is constant time as this method executes its function with extreme efficiency
		 */
		UnorderedMap<K,V> bucket = table[hashValue(key)];
		if (bucket == null) {
			return null;
		}
		return bucket.get(key);
	}

	/** Associates key k with value v in bucket with hash value h; returns old value. */
	@Override
	@TimeComplexityExpected("O(1)")
	public V put(K key, V value) {
		/**
		 * TCJ
		 * 
		 * expected complexity is constant time as this method executes its function with extreme efficiency
		 */
		V old = bucketPut(key,value);
		if ((double)size > ((double)capacity * loadfactor)) {
			resize(2 *capacity - 1);
		}
		return old;
	}

	/** Associates key k with value v in bucket with hash value h; returns old value. */
	public V bucketPut(K key, V value) {
		UnorderedMap<K,V> bucket = table[hashValue(key)];
		if(bucket == null) {
			bucket = table[hashValue(key)] = new UnorderedMap<>();
		}
		int oldSize = bucket.size();
		V answer = bucket.put(key, value);
		n += (bucket.size() - oldSize);
		return answer;
	}

	/** Removes entry having key k from bucket with hash value h (if any). */
	@Override
	@TimeComplexityExpected("O(1)")
	public V remove(K key) {
		/**
		 * TCJ
		 * 
		 * expected complexity is constant time as this method executes its function with extreme efficiency
		 */
		UnorderedMap<K,V> bucket = table[hashValue(key)];
		if (bucket == null) {
			return null;
		}
		int oldSize = bucket.size();
		V answer = bucket.remove(key);
		n -= (oldSize - bucket.size());
		return answer;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>();
		for(int h = 0; h < capacity; h++) {
			if (table[h] != null) {
				for (Entry<K,V> entry : table[h].entrySet()) {
					buffer.addLast(entry);
				}
			}
		}
		return buffer;
	}


}
