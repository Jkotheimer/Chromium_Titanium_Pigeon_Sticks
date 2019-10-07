package CTPS.puzzlesolver;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;

class ClueMap {

	private ArrayList<JsonNode> clues;

	/**
	 * CONSTRUCTOR
	 * - Opens the file into a JsonNode
	 * - Parses all elements past the 4th element into a map of clues
	 */
	public ClueMap(String filename) {
		ObjectMapper objectMapper = new ObjectMapper();
		clues = new ArrayList<>();

		try {
			JsonNode jsonNode = objectMapper.readTree(new File(filename));
			Iterator iter = jsonNode.elements();

			int i = 0;
			while(iter.hasNext()) {
				if(i > 3) clues.add((JsonNode) iter.next());
				else iter.next();
				i++;
			}
		} catch(IOException e) { System.err.println(e); }
	}

	public ArrayList<String> convertToArrayList(JsonNode n) {
		ArrayList<String> arr = new ArrayList<>();
		Iterator<JsonNode> it = n.elements();
		while(it.hasNext()) arr.add(it.next().textValue());
		return arr;
	}
	
	public void print() {
		System.out.println(clues);
	}
}
