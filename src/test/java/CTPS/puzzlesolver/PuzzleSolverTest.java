package CTPS.puzzlesolver;

import static org.junit.Assert.*;
import org.junit.Test;

public class PuzzleSolverTest {
	
	private PuzzleSolver fixture;
	
	/**
	 * Test the clue map when gathered from the json file
	 */
	@Test
	public void test_structure() {
		
		fixture = new PuzzleSolver(new ReferenceMap("test_cases/Mixed_Up_Purses.json"));
		
	}
}
