package CTPS.puzzlesolver;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;


/**
 * The cluemap will be an iterator that allows repetitive iteration through the clues
 */
class PuzzleSolver {
	
	private ReferenceMap refs;
	private String rName;
	
	public PuzzleSolver(ReferenceMap refs) {
		this.refs = refs;
		this.rName = refs.getRefName();
	}
	
	/**
	 * Solve
	 * - This generic function checks if the clue contains the reference name (people in the case of MUP)
	 * - It then correspondingly delegates the clue to separate functions which handle the two cases differently
	 */
	public void solve(JsonNode clue) {
		if(clue.has(rName)) solveForRef(clue);
		else {
			solveForItem(clue);
			solveForItemNegation(clue);
		}
		refs.autoEliminate();
	}

	//ADDED 
	private void solveForItemNegation (JsonNode clue) {
		ArrayList<String> notClues = new ArrayList<>();
		
		Iterator<Map.Entry<String, JsonNode>> iter = clue.fields();
		while(iter.hasNext()) {
			Map.Entry<String, JsonNode> entry = iter.next();
			if(entry.getValue().textValue().contains("!")) return;
			notClues.addAll(refs.getNotSolvedFor(entry.getKey(), entry.getValue().textValue()));
		}
		
		iter = clue.fields();
		while(iter.hasNext()) {
			Map.Entry<String, JsonNode> entry = iter.next();
			for(String s : notClues) refs.eliminate(s, entry.getKey(), entry.getValue().textValue());
		}
	}
	
	/**
	 * solveForRef
	 * - This function boils down the given clue knowing that it contains a reference to eliminate from
	 * - It essentially iterates through each part of the clue that isn't a reference (person) and creates 3 variables from it
	 * 1) The reference (found with clue.get(rName))
	 * 2) The solvable category (job, lastname, lostitem)
	 * 3) The item to either be eliminated or set to the given reference
	 * 
	 * These variables are delegated to the SetOrEliminate function to determine how to handle them
	 */
	private void solveForRef(JsonNode clue) {
		Iterator<Map.Entry<String, JsonNode>> fields = clue.fields();
		while(fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();
			
			String key = field.getKey();
			if(key == rName) continue;
			JsonNode val = field.getValue();
			
			if(clue.get(rName).isArray()) {
				Iterator<JsonNode> clueSpecificRefs = clue.get(rName).iterator();
				while(clueSpecificRefs.hasNext()) SetOrEliminate(clueSpecificRefs.next().textValue(), key, val);
			} else SetOrEliminate(clue.get(rName).textValue(), key, val);
		}
	}
	
	/**
	 * solveForItem
	 * - This function boils down the given clue knowing that it will have to search the map for matching items
	 * - It does a similar function as solveForRef, except everything is optional because this type of clue may not be usable yet
	 * 
	 * 1) The reference (determined by querying the map for a certain item to see if it's been solved for yet)
	 * 		(if not, the option is off and the function does nothing)
	 * 2) The solvable category (job, lastname, lostitem)
	 * 3) The item to either be eliminated or set to the given reference
	 * 
	 * These variables are delegated to the SetOrEliminate function to determine how to handle them
	 */
	private void solveForItem(JsonNode clue) {
		String opt_ref = null;
		
		Iterator<Map.Entry<String, JsonNode>> iter = clue.fields();
		while(iter.hasNext() && opt_ref == null) {
			Map.Entry<String, JsonNode> entry = iter.next();
			if(entry.getValue().isArray()) {
				Iterator<JsonNode> elements = entry.getValue().elements();
				while(elements.hasNext()) {
					opt_ref = refs.solvedFor(entry.getKey(), elements.next().textValue());
					if(opt_ref != null) break;
				}
			} else opt_ref = refs.solvedFor(entry.getKey(), entry.getValue().textValue());
		}
		
		if(opt_ref != null) {
			iter = clue.fields();
			while(iter.hasNext()) {
				Map.Entry<String, JsonNode> entry = iter.next();
				SetOrEliminate(opt_ref, entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * SetOrEliminate
	 * - This function allows passing of a JsonNode for the item because it could be an array or an individual element
	 * - We determine if it's an array or not, then from there, use a tad bit recursion to boil down to singular elements
	 * - If the element contains a "!", we eliminate it. Else, we set it
	 */
	private void SetOrEliminate(String ref, String solvable, JsonNode item) {
		if(item.isArray()) {
			Iterator<JsonNode> items = item.elements();
			while(items.hasNext()) SetOrEliminate(ref, solvable, items.next());
		} else {
			// If this clue has a '!' in it, then we want to eliminate that item
			if(item.textValue().contains("!")) refs.eliminate(ref, solvable, item.textValue().replaceAll("!", ""));
			// Else, we want to set it to that value
			else refs.set(ref, solvable, item.textValue());
		}
	}
	
	// Facade for ReferenceMap.print
	public void printResult() {
		if(isSolved()) {
			System.out.println("Results:");
			refs.print();
		}
		else System.out.println("The puzzle hasn't been solved!");
	}
	
	// Facade for ReferenceMap.isSolved
	public boolean isSolved() {
		return refs.isSolved();
	}
}
