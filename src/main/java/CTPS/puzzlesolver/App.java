package CTPS.puzzlesolver;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Hello Chromium Titanium Pigeon Sticks!
 *
 */
public class App {
	public static void main( String[] args ) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Read the entire JSON file of data into a single json node to be sorted later
			JsonNode jsonNode = objectMapper.readTree(new File("test_cases/Mixed_Up_Purses.json"));
			String names = jsonNode.get("people").get(0).asText();
			System.out.println("Success: " + names);
		} catch(IOException e) { System.err.println(e); }
    }
}
