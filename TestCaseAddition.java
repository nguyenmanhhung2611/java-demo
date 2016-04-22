import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestCaseAddition {

	@Test
	public void testbothZero() {
		assertEquals(0, add(0,0));
	}
	
	@Test
	public void firstZero()
	{
		assertEquals(3, add(0,3));
	}
	
	@Test
	public void secondZero()
	{
		assertEquals(2, add(0,2));
	}
	
	@Test
	public void notbothZero()
	{
		assertEquals(5, add(2,3));
	}
	
	private int add(int first, int second)
	{
		if(second == 0) return first;
		if(first != 0 && second != 0) return first + second;
		return second;
	}
}
