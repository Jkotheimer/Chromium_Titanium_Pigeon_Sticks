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
		ReferenceMap references = new ReferenceMap("test_cases/Mixed_Up_Purses.json");
		
		references.print();
    }
}
