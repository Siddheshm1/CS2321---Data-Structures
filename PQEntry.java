package cs2321;

import net.datastructures.Entry;

public class PQEntry<K, V> implements Entry<K, V> {

	K key;
	V value;
	int index;
	
	public PQEntry(K k, V v) {
		key =k;
		value = v;
	}
	
	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}
	
	public void setIndex(int i) {
		index = i;
	}

	public void setKey(K key2) {
		key = key2;
	}
	
	public void setValue(V value2) {
		value = value2;
	}
	

}
