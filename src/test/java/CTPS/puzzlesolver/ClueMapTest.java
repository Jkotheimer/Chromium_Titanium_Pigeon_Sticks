package CTPS.puzzlesolver;

import static org.junit.Assert.*;
import org.junit.Test;

public class ClueMapTest {
	
	private ClueMap fixture;
	
	/**
	 * Test the clue map when gathered from the json file
	 */
	@Test
	public void test_structure() {
		
		fixture = new ClueMap("test_cases/Mixed_Up_Purses.json");
		
		for(int i = 0; i < 10; i++) {
			assertTrue(fixture.hasNext());
			fixture.next();
		}
		assertFalse(fixture.hasNext());
	}
}
