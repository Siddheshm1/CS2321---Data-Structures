package cs2321;


import java.util.Iterator;
import java.util.NoSuchElementException;
import net.datastructures.Entry;
import net.datastructures.Map;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/11/2019
 */

public class UnorderedMap<K,V> extends AbstractMap<K,V> {

	/* Use ArrayList or DoublyLinked list for the Underlying storage for the map of entries.
	 * Uncomment one of these two lines;
	 * private ArrayList<Entry<K,V>> table; 
	 * private DoublyLinkedList<Entry<K,V>> table;
	 */

	private ArrayList<mapEntry<K,V>> table = new ArrayList<>();

	public UnorderedMap() {
	}

	private int findIndex(K key) {
		int n = table.size();
		for (int j = 0; j < n; j++) {
			if (table.get(j).getKey().equals(key)) {
				return j;
			}
		}
		return -1;
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
	@TimeComplexity("O(n)")
	public V get(K key) {
		/**
		 * TCJ
		 * 
		 * findIndex method call is of complexity O(n) and that is the only major method call which 
		 * effectively means that this method also has the complexity of O(n) 
		 */
		int j = findIndex(key);
		if (j == -1) {
			return null;
		}
		return table.get(j).getValue();
	}

	/** Associates given value with given key, replacing a previous value (if any). */
	@Override
	@TimeComplexity("O(n)")
	public V put(K key, V value) {
		/**
		 * TCJ
		 * 
		 * all method calls in this method are of complexity O(1) except for the findIndex method which means 
		 * that this method is of O(n) complexity.
		 */
		int j = findIndex(key);
		if (j == -1) {
			table.addLast(new mapEntry<>(key,value));
			return null;
		} else {
			return table.get(j).setValue(value);
		}
	}
	
	/** Removes the entry with the specified key (if any) and returns its value. */
	@Override
	@TimeComplexity("O(n)")
	public V remove(K key) {
		/**
		 * TCJ
		 * 
		 * Similar to the other methods, this method only calls the findIndex method which has complexity O(n).
		 * As a result, this method also has O(n) complexity as this is the highest complexity.
		 */
		int j = findIndex(key);
		int n = size();
		if (j == -1) {
			return null;
		}
		V answer = table.get(j).getValue();
		if (j != n - 1) {
			table.set(j,  table.get(n-1));
		}
		table.remove(n-1);
		return answer;
	}
	
	private class EntryIterator implements Iterator<Entry<K,V>> {
		private int j=0;
		public boolean hasNext() {
			return j < table.size();
		}
		public Entry<K,V> next() {
			if (j == table.size()) throw new NoSuchElementException();
			return table.get(j++);
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	private class EntryIterable implements Iterable<Entry<K,V>>{
		public Iterator<Entry<K,V>> iterator() {
			return new EntryIterator();
		}
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return new EntryIterable();
	}

}
