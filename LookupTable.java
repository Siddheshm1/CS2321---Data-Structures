package cs2321;

import java.util.Comparator;

import net.datastructures.*;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/11/2019
 */

public class LookupTable<K,V> extends AbstractSortedMap<K,V> {

	/* Use Sorted ArrayList for the Underlying storage for the map of entries.
	 * 
	 */
	private ArrayList<Entry<K,V>> table = new ArrayList<>(); 
	public LookupTable() {
		super();
	}

	public LookupTable(Comparator<K> comp){
		super(comp);
	}

	private int findIndex(K key, int low, int high) {
		if (high < low) {
			return high + 1;
		}
		int mid = (low + high) / 2;
		int comp = compare(key, table.get(mid));
		if (comp == 0) {
			return mid;
		} else if (comp < 0) {
			return findIndex(key, low, mid - 1);
		} else {
			return findIndex(key, mid + 1, high);
		}
	}
	private int findIndex(K key) {
		return findIndex(key, 0, table.size() - 1);
	}
	@Override
	public int size() {
		return table.size();
	}

	@Override
	public boolean isEmpty() {
		return table.size() == 0;
	}

	/** Returns the value associated with the specified key (or else null). */
	@Override
	@TimeComplexity("O(log n)")
	public V get(K key) {
		/**
		 * TCJ
		 * 
		 * the method has a log n complexity as in this case, the findIndex uses a table in an increasing 
		 * rate. for other classes, findIndex may have different complexities
		 */
		int j = findIndex(key);
		if (j == size() || compare(key, table.get(j)) != 0) {
			return null;
		}
		return table.get(j).getValue();
	}

	protected mapEntry<K, V> validate(Entry<K,V> entry) throws IllegalArgumentException {
		if (!(entry instanceof mapEntry)) {
			throw new IllegalArgumentException("Invalid entry");
		}
		mapEntry<K, V> locator = (mapEntry<K, V>) entry;
		return locator;
	}

	/** Associates the given value with the given key, returning any overridden value.*/
	@Override
	@TimeComplexity("O(n)")
	public V put(K key, V value) {
		/**
		 * TCJ
		 * 
		 * when putting in elements into the table, the complexity of findIndex does not have to be
		 * log n as it does not need to search through the table, it needs to simply add.
		 */
		int j = findIndex(key);
		if (j < size() && compare(key, table.get(j)) == 0) {
			Entry<K, V> entry = table.get(j);
			return validate(entry).setValue(value);
		}
		table.add(j, new mapEntry<K, V>(key,value));
		return null;
	}

	/** Removes the entry having key k (if any) and returns its associated value. */
	@Override
	@TimeComplexity("O(n)")
	public V remove(K key) {
		/**
		 * TCJ
		 * 
		 * the remove method has a n complexity as it does not need to make any other changes to the table
		 * apart from simply removing the desired key that needs to be removed.
		 */
		int j = findIndex(key);
		if(j == size() || compare(key, table.get(j)) != 0) {
			return null;
		}
		return table.remove(j).getValue();
	}

	private Iterable<Entry<K,V>> snapshot(int startIndex, K stop){
		ArrayList<Entry<K,V>> buffer = new ArrayList<>();
		int j = startIndex;
		while (j < table.size() && (stop == null || compare(stop, table.get(j)) > 0)) {
			buffer.addLast(table.get(j++));
		}
		return buffer;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return snapshot(0, null);
	}

	private Entry<K,V> safeEntry(int j) {
		if (j < 0 || j >= table.size()) {
			return null;
		}
		return table.get(j);
	}

	@Override
	public Entry<K, V> firstEntry() {
		return safeEntry(0);
	}

	@Override
	public Entry<K, V> lastEntry() {
		return safeEntry(table.size() -1);
	}

	/** Returns the entry with least key greater than or equal to given key (if any). */
	@Override
	@TimeComplexity("O(log n)")
	public Entry<K, V> ceilingEntry(K key)  {
		/**
		 * TCJ
		 * 
		 * log n because the method needs to find a specific key while comparing with other keys to obtain
		 * the ceiling entry
		 */
		return safeEntry(findIndex(key));
	}

	/** Returns the entry with greatest key less than or equal to given key (if any). */
	@Override
	@TimeComplexity("O(log n)")
	public Entry<K, V> floorEntry(K key)  {
		/**
		 * TCJ 
		 * 
		 * similar to ceiling entry, this method needs to search through the table and find the element
		 * that fits the criteria of what is being searched, which is the floor entry
		 */
		int j = findIndex(key);
		if (j == size() || ! key.equals(table.get(j).getKey())) {
			j--;
		}
		return safeEntry(j);
	}

	@Override
	public Entry<K, V> lowerEntry(K key) {
		return safeEntry(findIndex(key) - 1);
	}

	@Override
	public Entry<K, V> higherEntry(K key) {
		int j = findIndex(key);
		if (j < size() && key.equals(table.get(j).getKey())) {
			j++;
		}
		return safeEntry(j);
	}

	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey){
		return snapshot(findIndex(fromKey), toKey);
	}


}
