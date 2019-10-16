package CTPS.puzzlesolver;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;


/**
 * The cluemap will be an iterator that allows repetitive iteration through the clues
 */
class ClueMap {

	private ArrayList<JsonNode> clues;
	private int currentPosition;

	/**
	 * CONSTRUCTOR
	 * - Opens the file into a JsonNode
	 * - Parses all elements past the 4th element into a map of clues
	 */
	public ClueMap(String filename) {
		ObjectMapper objectMapper = new ObjectMapper();
		clues = new ArrayList<>();
		currentPosition = 0;

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
	
	public boolean hasNext() {
		boolean hasNext = (currentPosition != clues.size());
		// If we've reached the end of the clues, reset to prep for the next iteration
		if(!hasNext) currentPosition = 0;
		return hasNext;
	}
	
	public JsonNode next() {
		return clues.get(currentPosition++);
	}
	
	public void print() {
		System.out.println(clues);
	}
}
