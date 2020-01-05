package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FractionalKnapsackTest {

	public FractionalKnapsack test;

	@Before
	public void setUp() throws Exception {
		test = new FractionalKnapsack();
	}

	@Test
	public void testMaximumValue() {
		int [][] test1 = {{4,12}};
		assertEquals(12, FractionalKnapsack.MaximumValue(test1, 10),0);
	}

	@Test
	public void testMaximumValue1() {
		int [][] test2 = {{4,12}, {8,32}, {2,30}, {6,40}, {1,50}};
		assertEquals(124, FractionalKnapsack.MaximumValue(test2, 10),0);
	}

	@Test
	public void testMaximumValue2() {
		int [][] test3 = {{4,10}};
		assertEquals(7.5, FractionalKnapsack.MaximumValue(test3, 3),0);
	}

	@Test
	public void testMaximumValue3() {
		int [][] test4 = {{4,12}, {8,32}};
		assertEquals(38, FractionalKnapsack.MaximumValue(test4, 10),0);
	}

	@Test
	public void testMaximumValue4() {
		int [][] test4 = {};
		assertEquals(0, FractionalKnapsack.MaximumValue(test4, 0),0);
	}

}
