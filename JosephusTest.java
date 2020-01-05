package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JosephusTest {

	Josephus test;
	@Before
	public void setUp() throws Exception {
		test = new Josephus();
	}

	@Test
	public void testOrder1() {
		String[] expected = {"a","b","c","d"};
		String[] input = {"a","b","c","d"};
		String [] output = new String [4];
		DoublyLinkedList<String> abc = test.order(input,1);
		for ( int i = 0; abc.size() > 0; i++) {
			output[i] = abc.removeFirst();
		}
		for (int i = 0; i < input.length; i++) {
			assertEquals("easyTest", input[i], output[i]);
		}
		
	}

	@Test
	public void testOrder2() {
		String[] expected = {"a","b","c","d"};
		String[] input = {"b","d","c","a"};
		String [] output = new String [4];
		DoublyLinkedList<String> abc = test.order(input,2);
		for ( int i = 0; abc.size() > 0; i++) {
			output[i] = abc.removeFirst();
		}
		for (int i = 0; i < input.length; i++) {
			assertEquals("easyTest1", input[i], output[i]);
		}
	}

	@Test
	public void testOrder3() {
		String[] expected = {"a","b","c","d"};
		String[] input = {"c","b","d","a"};
		String [] output = new String [4];
		DoublyLinkedList<String> abc = test.order(input,3);
		for ( int i = 0; abc.size() > 0; i++) {
			output[i] = abc.removeFirst();
		}
		for (int i = 0; i < input.length; i++) {
			assertEquals("easyTest2", input[i], output[i]);
		}
	}

	@Test
	public void testOrder4() {
		String[] expected = {"a","b","c","d","e"};
		String[] input = {"d","c","e","b","a"};
		String [] output = new String [5];
		DoublyLinkedList<String> abc = test.order(input,4);
		for ( int i = 0; abc.size() > 0; i++) {
			output[i] = abc.removeFirst();
		}
		for (int i = 0; i < input.length; i++) {
			assertEquals("easyTest3", input[i], output[i]);
		}
	}

}
