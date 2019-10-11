package CTPS.puzzlesolver;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;
 
class ReferenceMap {
	
	private HashMap<String, Map<String, ArrayList<String>>> references;
	
	/**
	 * CONSTRUCTOR
	 * - Opens the file into a JsonNode
	 * - Parses the first 4 elements of the JsonNode into a map of the reference names pointing to a map of things to be solved for
	 */
	public ReferenceMap(String filename) {
		ObjectMapper objectMapper = new ObjectMapper();
		references = new HashMap<>();
		
		try {
			JsonNode jsonNode = objectMapper.readTree(new File(filename));
			HashMap<String, ArrayList<String>> solvables = new HashMap<>();		// The map of lists of solvable items
			String refName = "";												// The the name of the references
			ArrayList<String> refNames;
			
			// Fill solvables with the 3 lists of things to be solved for for each thing being referenced to.
			// I wish there was a better way to get the 2nd, 3rd, and 4th elements of the JsonNode without 
			// Having to make an iterator and iterate through the first four elements
			Iterator iter = jsonNode.fields();
			int i = 0;
			while(iter.hasNext() && i < 4) {
				Map.Entry<String, JsonNode> temp = (Map.Entry) iter.next();
				String nextName = temp.getKey();
				ArrayList<String> nextList = convertToArrayList((JsonNode) temp.getValue());
				
				if(i > 0) solvables.put(nextName, nextList);
				else {
					refName = nextName;
					refNames = nextList;
				}
				i++;
			}

			// Now fill our references map with the names pointing to a copy of the solvables
			iter = jsonNode.get(refName).elements();
			while(iter.hasNext()) {
				JsonNode next = (JsonNode) iter.next();
				references.put(next.textValue(), solvables);
			}
		} catch(IOException e) { System.err.println(e); }
	}
	
	private ArrayList<String> convertToArrayList(JsonNode n) {
		ArrayList<String> arr = new ArrayList<>();
		Iterator<JsonNode> it = n.elements();
		while(it.hasNext()) arr.add(it.next().textValue()	);
		return arr;
	}
	
	public boolean has(String ref, String solvable, String item) {
		return references.get(ref).get(solvable).contains(item);
	}
	
	public void eliminate(String ref, String solvable, String item) {
		references.get(ref).get(solvable).remove(item);
	}
	
	public void set(String ref, String solvable, String item) {
		
	}
	
	public void autoEleminate() {
		// TODO Have this function go through the entire references data structure and cross reference different things to eliminate them
	}
}
