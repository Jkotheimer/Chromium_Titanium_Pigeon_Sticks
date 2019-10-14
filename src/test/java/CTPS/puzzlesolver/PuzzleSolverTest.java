package CTPS.puzzlesolver;

import static org.junit.Assert.*;
import org.junit.Test;

public class PuzzleSolverTest {
	
	private PuzzleSolver fixture;
	private ClueMap clues;
	private ReferenceMap map;
	
	/**
	 * Test whether the PuzzleSolver actually solves the puzzle
	 */
	@Test
	public void test_solve() {
		map = new ReferenceMap("test_cases/Mixed_Up_Purses.json");
		fixture = new PuzzleSolver(map);
		clues = new ClueMap("test_cases/Mixed_Up_Purses.json");
		
		// First iteration through should give us testable results
		while(clues.hasNext()) fixture.solve(clues.next());
		
		assertFalse(map.has("Penny", "job", "doctor"));
		assertFalse(map.has("Penny", "job", "judge"));
		assertFalse(map.has("Penny", "job", "lawyer"));
		assertFalse(map.has("Penny", "lost", "comb"));
		
		assertFalse(map.has("Paula", "job", "doctor"));
		assertFalse(map.has("Paula", "lastname", "Smith"));
		assertFalse(map.has("Paula", "lastname", "Doe"));
		assertTrue(map.has("Paula", "lost", "compact"));
		assertEquals("Paula", map.solvedFor("lost", "compact"));
		
		assertFalse(map.has("Peggy", "lastname", "Jones"));
		assertFalse(map.has("Peggy", "lastname", "Johnson"));
		assertFalse(map.has("Peggy", "lastname", "Smith"));
		assertTrue(map.has("Peggy", "lastname", "Doe"));
		assertTrue(map.has("Peggy", "lastname", "Dixon"));
		assertFalse(map.has("Peggy", "lost", "comb"));
		assertFalse(map.has("Peggy", "lost", "lipstick"));
		
		assertFalse(map.has("Pam", "lost", "file"));
		assertFalse(map.has("Pam", "lost", "keyring"));
		
		assertFalse(map.has("Pat", "lost", "lipstick"));
		assertTrue(map.has("Pat", "lost", "keyring"));
	}
}
