package CTPS.puzzlesolver;

import java.util.Scanner;

/**
 * Hello Chromium Titanium Pigeon Sticks!
 *
 */
public class App {
	public static void main( String[] args ) {
		Scanner in = new Scanner(System.in);
		String file;
		if(args.length == 0) {
			System.out.print("No input file specified!\nEnter a valid puzzle JSON file: ");
			file = in.nextLine();
		} else file = args[0];
		ClueMap clues = new ClueMap(file);
		PuzzleSolver solver = new PuzzleSolver(new ReferenceMap(file));
		
		// Iterate through the clues and pass clues to the reference map for solving
		// Then call the autoEliminate function to make the reference map cross reference solvable items
		// Do this until the map is solved
		System.out.println("Solving the puzzle located at" + file );
		int counter = 0;
		while(!solver.isSolved()) {
			while(clues.hasNext())
				solver.solve(clues.next());
			counter++;
			if (counter <=1 ) {
				System.out.println(" ");
				System.out.println("After " + counter + " iteration the results are:" );
				solver.printResult();
			}
			else if (counter > 1 ) {
				System.out.println(" ");
				System.out.println("After " + counter + " iterations the results are:" );
				solver.printResult();
			}
		
		}
		System.out.println(" ");
		System.out.println("After " + counter + " iterations, the final results are: ");

		solver.printResult();
	}
}
