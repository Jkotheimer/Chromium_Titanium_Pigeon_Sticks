package CTPS.puzzlesolver;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Hello Chromium Titanium Pigeon Sticks!
 *
 */
public class App {
	public static void main( String[] args ) {
		ClueMap clues = new ClueMap("test_cases/Mixed_Up_Purses.json");
		PuzzleSolver solver = new PuzzleSolver(new ReferenceMap("test_cases/Mixed_Up_Purses.json"));
		
		// Iterate through the clues and pass clues to the reference map for solving
		// Then call the autoEliminate function to make the reference map cross reference solvable items
		// Do this until the map is solved
		while(!solver.isDone()) {
			while(clues.hasNext()) solver.solve(clues.next());
			solver.autoEliminate();
		}
    }
}
