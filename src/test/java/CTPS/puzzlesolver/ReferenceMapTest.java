package CTPS.puzzlesolver;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class ReferenceMapTest {
	
	private static ReferenceMap fixture;
	
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
	
	@Test
	public void test_set() {
		fixture = new ReferenceMap("test_cases/Mixed_Up_Purses.json");
		
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
	
	@Test
	public void test_isSolved() {
		fixture = new ReferenceMap("test_cases/Mixed_Up_Purses.json");
		
		// It should be initially not solved
		assertFalse(fixture.isSolved());
		
		// Invoke the moves necessary to get to a "solved" position (even if it isn't correct in our case)
		fixture.set("Penny", "lastname", "Johnson");
		fixture.set("Peggy", "lastname", "Doe");
		fixture.set("Paula", "lastname", "Dixon");
		fixture.set("Pam", "lastname", "Smith");
		
		assertFalse(fixture.isSolved()); // Shouldn't be solved yet
		
		fixture.eliminate("Penny", "job", "lawyer");
		fixture.eliminate("Penny", "job", "pilot");
		fixture.eliminate("Penny", "job", "teacher");
		fixture.eliminate("Penny", "job", "judge");
		
		fixture.set("Peggy", "job", "lawyer");
		fixture.set("Paula", "job", "pilot");
		fixture.set("Pam", "job", "teacher");
		fixture.set("Pat", "job", "judge");
		
		assertFalse(fixture.isSolved()); // Still shouldn't be solved yet
		
		fixture.set("Penny", "lost", "compact");
		fixture.set("Peggy", "lost", "file");
		fixture.set("Paula", "lost", "keyring");
		
		assertFalse(fixture.isSolved()); // Gotta test right before the last move just to make sure
		
		fixture.set("Pam", "lost", "lipstick");
		
		assertTrue (fixture.isSolved());
	}
	
	@Test
	public void test_get_solvable_map() {
		fixture = new ReferenceMap("test_cases/Mixed_Up_Purses.json");
		
		HashMap<String, ArrayList<String>> solvables = fixture.getSolvables();
		
		List<String> lost = Arrays.asList("lipstick", "comb", "compact", "file", "keyring");
		
		for(int i = 0; i < solvables.get("lost").size(); i++) 
			assertEquals(lost.get(i), solvables.get("lost").get(i));
	}
}
