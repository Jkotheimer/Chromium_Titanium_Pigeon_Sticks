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
		
		// Check if penny has Doe as a last name before and after setting it to johnson
		assertTrue(fixture.has("Penny", "lastname", "Doe"));
		assertTrue(fixture.has("Penny", "lastname", "Johnson"));
		assertTrue(fixture.has("Pam", "lastname", "Johnson"));
		
		// It should be able to handle multiple requests since we will be iterating through the clues continuously
		fixture.set("Penny", "lastname", "Johnson");
		fixture.set("Penny", "lastname", "Johnson");
		fixture.set("Penny", "lastname", "Johnson");
		
		assertTrue(fixture.has("Penny", "lastname", "Johnson"));
		assertFalse(fixture.has("Penny", "lastname", "Doe"));
		assertFalse(fixture.has("Penny", "lastname", "Dixon"));
		assertFalse(fixture.has("Penny", "lastname", "Jones"));
		assertFalse(fixture.has("Penny", "lastname", "Smith"));
		
		assertFalse(fixture.has("Pam", "lastname", "Johnson"));
		assertFalse(fixture.has("Peggy", "lastname", "Johnson"));
		assertFalse(fixture.has("Pat", "lastname", "Johnson"));
		assertFalse(fixture.has("Paula", "lastname", "Johnson"));
	}
}
