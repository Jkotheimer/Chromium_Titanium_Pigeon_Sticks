package CTPS.puzzlesolver;

import static org.junit.Assert.*;
import org.junit.Test;

public class ReferenceMapTest {
	
	private ReferenceMap fixture;
	
	/**
	 * Test the references map when gathered from the json file
	 */
	@Test
	public void test_structure_mixed_up_purses() {
		fixture = new ReferenceMap("test_cases/Mixed_Up_Purses.json");
		
		assertTrue(fixture.has("Pam", "job", "doctor"));
		assertFalse(fixture.has("Pam", "job", "Johnson"));
		
		fixture.eliminate("Pam", "lost", "compact");
		assertFalse(fixture.has("Pam", "lost", "compact"));
	}
}
