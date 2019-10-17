package CTPS.puzzlesolver;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.*;
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
	private String refName = "";
	
	public ReferenceMap(String filename) {
		ObjectMapper objectMapper = new ObjectMapper();
		people = new HashMap<>();
		
		try {
			JsonNode jsonNode = objectMapper.readTree(new File(filename));
			HashMap<String, ArrayList<String>> solvables = new HashMap<>();		// The map of lists of solvable items
			
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
				if(i > 0) solvables.put(nextName, (ArrayList)nextList.clone());
				else this.refName = nextName;

				i++;
			}
			
			// These are used to make a deep clone of the solvables map so we dont have to 
			Gson gson = new Gson();
			String jsonString = gson.toJson(solvables);
			Type type = new TypeToken<HashMap<String, ArrayList<String>>>(){}.getType();
			
			// Now fill our references map with the names pointing to a copy of the solvables
			iter = jsonNode.get(refName).elements();
			while(iter.hasNext()) {
				JsonNode next = (JsonNode) iter.next();
				
				HashMap<String, ArrayList<String>> clonedMap = gson.fromJson(jsonString, type);
				people.put(next.textValue(), clonedMap);
			}
		} catch(IOException e) { System.err.println(e); }
	}
	
	public String getRefName() {
		return this.refName;
	}
	
	/**
	 * Has
	 * - This function returns a boolean value describing whether or not the puzzle map contains the given values
	 * 
	 * Example
	 * 
	 * - referenceMap.has("Peggy", "job", "doctor");
	 * 
	 * This will return true if we haven't yet eliminated doctor from Peggy's job list
	 * It will return false if we have eliminated it
	 * 
	 * @param ref		(String) - The thing being referred to (The person in the MUP case)
	 * @param solvable	(String) - The category of thing that we are checking for (job, lastname, lost)
	 * @param item		(String) - The name of the item that is being searched for
	 */
	public boolean has(String ref, String solvable, String item) {
		return people.get(ref).get(solvable).contains(item);
	}
	
	/**
	 * Set
	 * - This function will set a specific item true for one of the 3 categories (In the case of MUP - job, last name, lost item)
	 * - This means that the rest of the items for that category will be eliminated for that reference (person)
	 * - This also means that the given item must be eliminated from all other references (people)
	 * 
	 * Example
	 * 
	 * - referenceMap.set("Pat", "lost", "comb");
	 * 
	 * This will remove every other lost item from Pat's list of lost items
	 * It will also remove comb from every other person's list of lost items
	 * 
	 * @param ref		(String) - The thing being referred to (The person in the MUP case)
	 * @param solvable	(String) - The category of thing that will be solved for (job, lastname, lost)
	 * @param item		(String) - The name of the item that is being set - all other items in the solvable category will be eliminated
	 */
	public void set(String ref, String solvable, String item) {
		// Case where it offers the first letter of the item
		if(item.length() == 1) {
			ArrayList<String> retainer = new ArrayList<>();
			for(String s : people.get(ref).get(solvable)) {
				if(s.charAt(0) == item.charAt(0)) retainer.add(s);
			}
			people.get(ref).get(solvable).retainAll(retainer);
		} else people.get(ref).get(solvable).retainAll(Arrays.asList(item));
		
		// Now go through all the other solvables and remove the item
		for(String r : people.keySet())
			if(!r.equals(ref))
				this.eliminate(r, solvable, item);
	}
	
	/**
	 * Eliminate
	 * - This function eliminates the specified item from the solvable set of the given reference
	 * - (In the case of MUP, eliminates the specified lastname from the lastname list (or whatever other item from another list) for the specified person)
	 * 
	 * Example
	 * 
	 * referenceMap.eliminate("Pam", "lastname", "Smith");
	 * 
	 * This will remove Smith from Pam's list of lastnames
	 * 
	 * @param ref		(String) - The thing being referred to (The person in the MUP case)
	 * @param solvable	(String) - The category of thing that will be eliminated (job, lastname, lost)
	 * @param item		(String) - The name of the item that is being eliminated
	 */
	public void eliminate(String ref, String solvable, String item) {
		people.get(ref).get(solvable).remove(item);
	}
	
	/**
	 * isSolved
	 * - This function is fairly straightforward > Returns whether or not the puzzle has been solved
	 * - We know the puzzle has been solved when every solvable ArrayList only has one item in it.
	 */
	public boolean isSolved() {
		// Iterate through the people, then iterate through each of their lists and check if the size is greater than 1
		for(Map<String, ArrayList<String>> map : people.values())
			for(ArrayList<String> list : map.values())
				if(list.size() > 1)
					return false;
		return true;
	}
	
	public String solvedFor(String solvable, String item) {
		System.out.print("solvedFor: " + solvable + " " + item + " - ");
		for(Map.Entry<String, Map<String, ArrayList<String>>> map : people.entrySet()) {
			if(map.getValue().get(solvable).size() == 1 && map.getValue().get(solvable).contains(item)) {
				System.out.println(map.getKey());
				return map.getKey();
			}
		}
		System.out.println();
		return null;
	}
	
	public ArrayList<String> getNotSolvedFor(String solvable, String item) {
		ArrayList <String> notClue = new ArrayList(); 
		for(String s : people.keySet()) 
			if(!has(s, solvable, item)) {
				notClue.add(s);
			}
		return notClue;
	}
	
	public void autoEliminate() {
		// TODO Have this function go through the entire references data structure and cross reference different things to eliminate them
		for (Map.Entry<String, Map<String, ArrayList<String>>> refs : people.entrySet()) {
			for(Map.Entry<String, ArrayList<String>> entry : refs.getValue().entrySet()) {
				if(entry.getValue().size() == 1) {
					System.out.println("Autoeliminate" + refs.getKey() + entry.getKey() + entry.getValue().get(0));
					this.set(refs.getKey(), entry.getKey(), entry.getValue().get(0));
				}
			}
		}
	}
	
	// I put this here for testing in case if you want to look at the structure
	public void print() {
		System.out.println(this.people + "\n");
	}
}
