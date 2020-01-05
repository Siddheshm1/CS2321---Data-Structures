package cs2321;

import java.util.Comparator;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/11/2019
 */

public class DefaultComparator<K> implements Comparator<K> {
	public int compare(K a, K b) throws ClassCastException {
		return ((Comparable <K>) a).compareTo(b);
	}
}
