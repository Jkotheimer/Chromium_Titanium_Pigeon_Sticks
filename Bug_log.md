1) Using `toString()` on the JsonNode objects resulted in each string in the map being held with double quotes attached ** ("\"Peggy\"")**

	- FIX: Used JsonNode typecasting to allow usage of the textValue() function, which omits the double quotes.

2) 
