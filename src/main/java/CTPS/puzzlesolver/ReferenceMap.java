package CTPS.puzzlesolver;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;


/**
 * This class parses the Json file and sticks the references (people in the case of Mixed up purses) into a data structure to make the puzzle solving easier.
 * The structure will essentially resemble that of the table we used to solve the puzzle initially.
 * __________________________________________
 * |				Paula	| Pat	| Pam	| Penny	| Peggy	|
 * |----------------------------------------|...
 * |			| Lipstick	|		|		|...
 * | Lost		| Com		|		|		|...
 * |			| ...		|		|		|...
 * |-----------------------------------------...
 * |			| Jones		|		|		|...
 * | Lastname	| Smith		|		|		|...
 * |			| ...		|		|		|...
 * |-----------------------------------------...
 * |			| Doctor	|		|		|...
 * | Job		| Lawyer	|		|		|...
 * |			| ... 		|		|		|...
 * |________________________________________|...
 * 
 * Essentially, there is a node for every person, and that node is mapped to 3 other nodes (lost, lastname, and job)
 * These nodes are mapped to arraylists containing all of the solvables for each given category.
 * As we solve, we remove items from each given arraylist until each node has an arraylist of size 1
 * 
 * 
 * 			  /	Paula
 * 			 /-	Pat			Lost			[...]
 * 	people	---	Pam			LastName		[...]
 * 			 \-	Peggy		Job				[...]
 * 			  \	Penny
 * 
 */
class ReferenceMap {
	
	private HashMap<String, Map<String, ArrayList<String>>> people;
	
	public ReferenceMap(String filename) {
		ObjectMapper objectMapper = new ObjectMapper();
		people = new HashMap<>();
		
		try {
			JsonNode jsonNode = objectMapper.readTree(new File(filename));
			HashMap<String, ArrayList<String>> solvables = new HashMap<>();		// The map of lists of solvable items
			String refName = "";												// The the name of the references ("people for Mixed_Up_Purses)
			
			// Fill solvables with the 3 lists of things to be solved for for each thing being referenced to.
			// I wish there was a better way to get the 2nd, 3rd, and 4th elements of the JsonNode without 
			// Having to make an iterator and iterate through the first four elements
			Iterator iter = jsonNode.fields();
			int i = 0;
			while(iter.hasNext() && i < 4) {
				Map.Entry<String, JsonNode> temp = (Map.Entry) iter.next();
				String nextName = temp.getKey();
				
				// Fill a new arraylist with the next list of items to be solved for
				ArrayList<String> nextList = new ArrayList<>();
				Iterator<JsonNode> it = temp.getValue().elements();
				while(it.hasNext()) nextList.add(it.next().textValue()	);

				// The 0th item will be the reference names, so we take note of those first
				// Then move on with adding the solvable lists to the solvables map
				if(i > 0) solvables.put(nextName, nextList);
				else refName = nextName;

				i++;
			}

			// Now fill our references map with the names pointing to a copy of the solvables
			iter = jsonNode.get(refName).elements();
			while(iter.hasNext()) {
				JsonNode next = (JsonNode) iter.next();
				people.put(next.textValue(), solvables);
			}
		} catch(IOException e) { System.err.println(e); }
	}
	
	public boolean has(String ref, String solvable, String item) {
		return people.get(ref).get(solvable).contains(item);
	}
	
	public void set(String ref, String solvable, String item) {
		// TODO This function will set a specific item true for one of the 3 categories (job, last name, lost item)
		// This means that the rest of the items for that category will be eliminated for that reference (person)
		// This also means that the given item must be eliminated from all other references (people)
	}
	
	public void eliminate(String ref, String solvable, String item) {
		people.get(ref).get(solvable).remove(item);
	}
	
	public boolean isSolved() {
		// TODO Return true if every person has one of each thing. Else return false
		return false;
	}
}
