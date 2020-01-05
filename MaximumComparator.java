package cs2321;

import java.util.Comparator;

public class MaximumComparator<T> implements Comparator<Double> {

	@Override
	public int compare(Double arg0, Double arg1) {
		int temp = 0;
		if (arg0 < arg1) {
			return 1;
		} else if (arg1 == arg0) {
			return 0;
		} else {
			return -1;
		}
	}
}
