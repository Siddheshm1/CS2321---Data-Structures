package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.datastructures.AdaptablePriorityQueue;
import net.datastructures.Entry;

public class HeapPQTest {

	AdaptablePriorityQueue<String, Integer> heappq;

	@Before
	public void setUp() throws Exception {
		heappq = new HeapPQ<String, Integer>();
		heappq.insert("Bulbous Bouffant", 16);
		heappq.insert("Gazebo", 6);
		heappq.insert("Balooga", 7);
		heappq.insert("Galoshes", 8);
		heappq.insert("Eskimo", 6);
		heappq.insert("Mukluks", 7);
		heappq.insert("Macadamia", 9);
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
		while(!heappq.isEmpty()){
			e = heappq.removeMin();
			assertEquals(expected[i],  e.getKey());
			i++;
		}
	}


	@Test
	public void testSize() {
		assertEquals(7, heappq.size());
	}


	@Test
	public void testIsEmpty() {
		assert(!heappq.isEmpty());
	}

	@Test
	public void testInsert() {
		heappq.insert("Apple", 6);
		assertEquals(8, heappq.size());
	}

	@Test
	public void testMin() {
		assertEquals("Balooga", heappq.min().getKey());
	}


	@Test
	public void testRemove() {
		heappq.removeMin();
		assertEquals(6, heappq.size());		
	}

	@Test
	public void testReplaceKey() {
		heappq.replaceKey(heappq.min(), "Android");
		assertEquals(heappq.min().getKey(), "Android");
	}

	@Test
	public void testReplaceValue() {
		heappq.replaceValue(heappq.min(), 9);
		assertEquals((Integer) 9 ,heappq.min().getValue());
	}

}
