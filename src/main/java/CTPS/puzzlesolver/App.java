package CTPS.puzzlesolver;

/**
 * Hello Chromium Titanium Pigeon Sticks!
 *
 */
public class App {
	public static void main( String[] args ) {
		ClueMap clues = new ClueMap(args[0]);
		PuzzleSolver solver = new PuzzleSolver(new ReferenceMap(args[0]));
		
		// Iterate through the clues and pass clues to the reference map for solving
		// Then call the autoEliminate function to make the reference map cross reference solvable items
		// Do this until the map is solved
		System.out.println("Solving the puzzle located at " + args[0]);
		int i = 0;
		while(!solver.isSolved()) {
			while(clues.hasNext())
				solver.solve(clues.next());
			++i;
		}
		System.out.println("Iterations: " + i);
		solver.printResult();
	}
}
