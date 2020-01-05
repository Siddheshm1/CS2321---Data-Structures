package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PostfixExpressionTest {
	PostfixExpression test;

	@Before
	public void setUp() throws Exception {
		test = new PostfixExpression();
	}

	@Test
	public void testEvaluate1() {
		String input = "5 3 4 6 + * *";
		int expected = 150;
		assertEquals("test1", expected, test.evaluate(input) );
		
	}
	
	@Test
	public void testEvaluate2() {
		String input = "5 3 7 * +";
		int expected = 26;
		assertEquals("test1", expected, test.evaluate(input) );
	}
	
	@Test
	public void testEvaluate3() {
		String input = "8 4 2 + +";
		int expected = 14;
		assertEquals("test1", expected, test.evaluate(input) );
	}
	
	@Test
	public void testEvaluate4() {
		String input = "9 3 2 * *";
		int expected = 54;
		assertEquals("test1", expected, test.evaluate(input) );
	}
	
	@Test
	public void testEvaluate5() {
		String input = "10 2 /";
		int expected = 5;
		assertEquals("test1", expected, test.evaluate(input) );
	}
	
	@Test
	public void testEvaluate6() {
		String input = "5 2 -";
		int expected = 3;
		assertEquals("test1", expected, test.evaluate(input) );
	}
	
	@Test
	public void testEvaluate7() {
		String input = "5 2 3 * +";
		int expected = 11;
		assertEquals("test1", expected, test.evaluate(input) );
	}
	
	@Test
	public void testEvaluate8() {
		String input = "6 3 / 7 + 3 +";
		int expected = 12;
		assertEquals("test1", expected, test.evaluate(input) );
	}

}
