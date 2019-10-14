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
	
	public void solve(JsonNode clue) {
		// TODO eliminate or set the items in the reference map that this clue takes care of
		// If the clue has the refName ("people" for MUP), then we want to straight up eliminate or set the following items
		if(clue.has(rName)) {
			solveForRef(clue);
			System.out.println(clue);
		} else {
			// TODO Read through the map for people who already have one of the things solved for. From there, eliminate or set
			solveForItem(clue);
		}
		System.out.println();
	}
	
	private void solveForRef(JsonNode clue) {
		Iterator<Map.Entry<String, JsonNode>> fields = clue.fields();
		while(fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();
			
			String key = field.getKey();
			if(key == rName) continue;
			JsonNode val = field.getValue();
			
			if(clue.get(rName).isArray()) {
				Iterator<JsonNode> clueSpecificRefs = clue.get(rName).iterator();
				while(clueSpecificRefs.hasNext()) {
					String ref = clueSpecificRefs.next().textValue();
					SetOrEliminate(ref, key, val);
				}
			} else {
				SetOrEliminate(clue.get(rName).textValue(), key, val);
			}
		}
	}
	
	private void SetOrEliminate(String ref, String solvable, JsonNode item) {
		System.out.println(ref + " " + solvable + " " + item.toString());
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
	
	private void solveForItem(JsonNode clue) {
		// TODO Read through the map for people who already have one of the things solved for. From there, eliminate or set
		String opt_ref = null;
		
		Iterator<Map.Entry<String, JsonNode>> iter = clue.fields();
		while(iter.hasNext()) {
			Map.Entry<String, JsonNode> entry = iter.next();
			if(entry.getValue().isArray()) {
				Iterator<JsonNode> elements = entry.getValue().elements();
				while(elements.hasNext()) opt_ref = refs.solvedFor(entry.getKey(), elements.next().textValue());
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
	
	public void autoEliminate() {
		// TODO Have this function go through the entire references data structure and cross reference different things to eliminate them
	}
	
	public boolean isDone() {
		return refs.isSolved();
	}
}
