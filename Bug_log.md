1) Using `toString()` on the JsonNode objects resulted in each string in the map being held with double quotes attached ** ("\"Peggy\"")**

	- FIX: Used JsonNode typecasting to allow usage of the textValue() function, which omits the double quotes.

2) ReferenceMap.set method - Removing an item from any one of the lists resulted in all other instances being altered because they all referenced the same ArrayLists.

	- FIX: Used Google Gson and TypeToken to create deep clones of the solvable map so every reference pointed to it's own instance.

3) ReferenceMap.isSolved method - Originally just returned true as a placeholder

	- FIX: Fully implemented - requires nested loops to access the 2 level deep map of ArrayLists for size checking.

4) PuzzleSolver.solve method - nullpointer exception halfway through iteration

	- FIX: Any multiple items from one category in the same clue were being overwritten because maps can only have unique keys, so we needed to make multiple items into arrays to then be parsed.

