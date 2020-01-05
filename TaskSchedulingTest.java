package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TaskSchedulingTest {

	public TaskScheduling test;

	@Before
	public void setUp() throws Exception {
		test = new TaskScheduling();
	}

	@Test
	public void testNumOfMachines() {
		int[][] test1 = {{1,4}, {6,8}, {10,15}, {15,20}};
		assertEquals(1, TaskScheduling.NumOfMachines(test1));
	}

	@Test
	public void testNumOfMachines1() {
		int[][] test2 = {{10,15}, {15,20}, {1,4}, {6,8}};
		assertEquals(1, TaskScheduling.NumOfMachines(test2));
	}

	@Test
	public void testNumOfMachines2() {
		int[][] test3 = {{1,4}, {1,4}, {2,5}, {2,5}};
		assertEquals(4, TaskScheduling.NumOfMachines(test3));
	}

	@Test
	public void testNumOfMachines3() {
		int[][] test4 = {{2,5}, {2,5}, {1,4}, {1,4}};
		assertEquals(4, TaskScheduling.NumOfMachines(test4));
	}

	@Test
	public void testNumOfMachines4() {
		int[][] test5 = {{1,4}, {1,3}, {2,5}, {3,7}, {4,7}, {6,9}, {7,8}};
		assertEquals(3, TaskScheduling.NumOfMachines(test5));
	}

	@Test
	public void testNumOfMachines5() {
		int[][] test6 = {{3,7}, {4,7}, {6,9}, {1,4}, {1,3}, {2,5}, {7,8}};
		assertEquals(3, TaskScheduling.NumOfMachines(test6));
	}

	@Test
	public void testNumOfMachines6() {
		int[][] test6 = {};
		assertEquals(0, TaskScheduling.NumOfMachines(test6));
	}
}
