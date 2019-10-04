package CTPS.puzzlesolver;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;

/**
 * Hello Chromium Titanium Pigeon Sticks!
 *
 */
public class App {
	public static void main( String[] args ) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		//create arrays for each person
		ArrayList<String> person1 = new ArrayList<String>();
		ArrayList<String> person2 = new ArrayList<String>();
		ArrayList<String> person3 = new ArrayList<String>();
		ArrayList<String> person4 = new ArrayList<String>();
		ArrayList<String> person5 = new ArrayList<String>();
		
		try {
			// Read the entire JSON file of data into a single json node to be sorted later
			JsonNode jsonNode = objectMapper.readTree(new File("test_cases/Mixed_Up_Purses.json"));
			
			//get first names of people from json file and convert to String
			String firstName1 = jsonNode.get("people").get(0).asText();
			String firstName2 = jsonNode.get("people").get(1).asText();
			String firstName3 = jsonNode.get("people").get(2).asText();
			String firstName4 = jsonNode.get("people").get(3).asText();
			String firstName5 = jsonNode.get("people").get(4).asText();
			//probably  could do with some iteration or "if statement" not sure...
			
			//get items from json file and convert to String
			String items = jsonNode.get("lost").get(0).asText();
			String items1 = jsonNode.get("lost").get(1).asText();
			String items2 = jsonNode.get("lost").get(2).asText();
			String items3 = jsonNode.get("lost").get(3).asText();
			String items4 = jsonNode.get("lost").get(4).asText();

			
			//get last names from json file and convert to String
			String ln = jsonNode.get("lastname").get(0).asText();
			String ln1 = jsonNode.get("lastname").get(1).asText();
			String ln2 = jsonNode.get("lastname").get(2).asText();
			String ln3 = jsonNode.get("lastname").get(3).asText();
			String ln4 = jsonNode.get("lastname").get(4).asText();
			
		
			//get jobs from json file and convert to String
			String job = jsonNode.get("job").get(0).asText();
			String job1 = jsonNode.get("job").get(1).asText();
			String job2 = jsonNode.get("job").get(2).asText();
			String job3 = jsonNode.get("job").get(3).asText();
			String job4 = jsonNode.get("job").get(4).asText();
			
			
			// add the first name to each array 
			person1.add(firstName1);
			person2.add(firstName2);
			person3.add(firstName3);
			person4.add(firstName4);
			person5.add(firstName5);
			
			
			//add items to Person1 array
			person1.add(items);
			person1.add(items1);
			person1.add(items2);
			person1.add(items3);
			person1.add(items4);
			// there is definitely a better way to do this, maybe?
			
			//add last names to Person1 arrayList
			person1.add(ln);
			person1.add(ln1);
			person1.add(ln2);
			person1.add(ln3);
			person1.add(ln4);
			
			//add jobs to Person1 arrayList
			person1.add(job);
			person1.add(job1);
			person1.add(job2);
			person1.add(job3);
			person1.add(job4);
			
			
			//Person 2
			//add items to Person2 arrayList
			person2.add(items);
			person2.add(items1);
			person2.add(items2);
			person2.add(items3);
			person2.add(items4);
			
			
			//add last names to Person2 arrayList
			person2.add(ln);
			person2.add(ln1);
			person2.add(ln2);
			person2.add(ln3);
			person2.add(ln4);
			
			//add jobs to Person2 arrayList
			person2.add(job);
			person2.add(job1);
			person2.add(job2);
			person2.add(job3);
			person2.add(job4);
			
			//Person 3
			//add items to Person3 arrayList
			person3.add(items);
			person3.add(items1);
			person3.add(items2);
			person3.add(items3);
			person3.add(items4);
			
			
			//add last names to Person3 arrayList
			person3.add(ln);
			person3.add(ln1);
			person3.add(ln2);
			person3.add(ln3);
			person3.add(ln4);
			
			//add jobs to Person3 arrayList
			person3.add(job);
			person3.add(job1);
			person3.add(job2);
			person3.add(job3);
			person3.add(job4);
			
			//Person 4
			//add items to Person4 arrayList
			person4.add(items);
			person4.add(items1);
			person4.add(items2);
			person4.add(items3);
			person4.add(items4);
			
			
			//add last names to Person4 arrayList
			person4.add(ln);
			person4.add(ln1);
			person4.add(ln2);
			person4.add(ln3);
			person4.add(ln4);
			
			//add jobs to Person4 arrayList
			person4.add(job);
			person4.add(job1);
			person4.add(job2);
			person4.add(job3);
			person4.add(job4);
			
			//Person 5
			//add items to Person5 arrayList
			person5.add(items);
			person5.add(items1);
			person5.add(items2);
			person5.add(items3);
			person5.add(items4);
			
			
			//add last names to Person5 arrayList
			person5.add(ln);
			person5.add(ln1);
			person5.add(ln2);
			person5.add(ln3);
			person5.add(ln4);
			
			//add jobs to Person5 arrayList
			person5.add(job);
			person5.add(job1);
			person5.add(job2);
			person5.add(job3);
			person5.add(job4);
			
			
			//print out arraylist of each person to make sure its all gravy
			System.out.println(person1);
			System.out.println(person2);
			System.out.println(person3);
			System.out.println(person4);
			System.out.println(person5);
			
			//looks pretty good to me.. we can probably clean this up a bit too. 
			
			
			//String names = jsonNode.get("people").get(3).asText();
			//System.out.println("Success: " + names);
		} catch(IOException e) { System.err.println(e); }
    }
}
