package CTPS.puzzlesolver;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;


/**
 * The cluemap will be an iterator that allows repetitive iteration through the clues
 */
class PuzzleSolver {
	
	private ReferenceMap refs;
	
	public PuzzleSolver(ReferenceMap refs) {
		this.refs = refs;
	}
	
	public void solve(JsonNode clue) {
		// TODO eliminate or set the items in the reference map that this clue takes care of
	}
	
	public void autoEliminate() {
		// TODO Have this function go through the entire references data structure and cross reference different things to eliminate them
	}
	
	public boolean isDone() {
		return refs.isSolved();
	}
}
