package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.datastructures.Entry;
import net.datastructures.PriorityQueue;

public class OrderedPQTest {
	PriorityQueue<String, Integer> ordpq;

	@Before
	public void setUp() throws Exception {
		ordpq = new OrderedPQ<String, Integer>();
		ordpq.insert("Bulbous Bouffant", 16);
		ordpq.insert("Gazebo", 6);
		ordpq.insert("Balooga", 7);
		ordpq.insert("Galoshes", 8);
		ordpq.insert("Eskimo", 6);
		ordpq.insert("Mukluks", 7);
		ordpq.insert("Macadamia", 9);
	}

	@Test
	public void testSize() {
		assertEquals(7, ordpq.size());
	}

	@Test
	public void testIsEmpty() {
		assert(!ordpq.isEmpty());
	}

	@Test
	public void testInsert() {
		ordpq.insert("Apple", 6);
		assertEquals(8, ordpq.size());
	}

	@Test
	public void testMin() {
		assertEquals("Balooga", ordpq.min().getKey());
	}

	@Test
	public void testRemoveMin() {
		Entry<String, Integer> e;
		String[] expected= {
				"Balooga", 
				"Bulbous Bouffant",
				"Eskimo", 
				"Galoshes", 
				"Gazebo", 
				"Macadamia",
				"Mukluks"
		};

		int i=0;
		while(!ordpq.isEmpty()){
			e = ordpq.removeMin();
			assertEquals(expected[i],  e.getKey());
			i++;
		}
	}

}
