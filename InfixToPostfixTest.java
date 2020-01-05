package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InfixToPostfixTest {

	InfixToPostfix abc;
	
	@Before
	public void setUp() throws Exception {
		abc = new InfixToPostfix();
	}

	@Test
	public void testConvert1() {
		String input = ("1 + 2");
		String output = "1 2 +";
		String outOfProg = abc.convert(input);
		assertEquals("test 1", output, outOfProg);
	}
	
	@Test
	public void testConvert2() {
		String input = ("1 + 3 + 2");
		String output = ("1 3 + 2 +");
		String outOfProg = abc.convert(input);
		assertEquals("test 1", output, outOfProg);
	}

	@Test
	public void testConvert3() {
		String input = ("2 * 3");
		String output = ("2 3 *");
		String outOfProg = abc.convert(input);
		assertEquals("test 1", output, outOfProg);
	}
	
	@Test
	public void testConvert4() {
		String input = ("18 / 2");
		String output = ("18 2 /");
		String outOfProg = abc.convert(input);
		assertEquals("test 1", output, outOfProg);
	}
	
	@Test
	public void testConvert5() {
		String input = ("3 - 1");
		String output = ("3 1 -");
		String outOfProg = abc.convert(input);
		assertEquals("test 1", output, outOfProg);
	}
	
	@Test
	public void testConvert6() {
		String input = ("8 * 3");
		String output = ("8 3 *");
		String outOfProg = abc.convert(input);
		assertEquals("test 1", output, outOfProg);
	}
	
	@Test
	public void testConvert7() {
		String input = ("4 / 2");
		String output = ("4 2 /");
		String outOfProg = abc.convert(input);
		assertEquals("test 1", output, outOfProg);
	}
	
	@Test
	public void testConvert8() {
		String input = ("9 * 3");
		String output = ("9 3 *");
		String outOfProg = abc.convert(input);
		assertEquals("test 1", output, outOfProg);
	}
}
