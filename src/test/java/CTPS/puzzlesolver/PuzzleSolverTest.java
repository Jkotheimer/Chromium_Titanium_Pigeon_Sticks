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
		
		clues.print();
		
		// First iteration through should give us testable results
		while(clues.hasNext()) fixture.solve(clues.next());
		
		map.print();
		
		assertFalse(map.has("Paula", "job", "doctor"));
		assertFalse(map.has("Peggy", "lastname", "Jones"));
		assertTrue(map.has("Paula", "lost", "compact"));
	}
}
