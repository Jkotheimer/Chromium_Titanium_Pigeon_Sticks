# Chromium_Titanium_Pigeon_Sticks

## Contributors
- Anissa Cristerna
- Jack Kotheimer
- Matt Hempe
- Miguel Gonzalez

## Description

This repository holds a puzzle solving program that was completed for COMP 330: Software Engineering.

The goal was to structure the input from a word problem such as this one:

![Image of puzzle problem](https://github.com/Jkotheimer/Chromium_Titanium_Pigeon_Sticks/blob/master/test_cases/MUP-1.png)

and write a program to automate the solving of the puzzle.
We structured the input from this example into a json file found under [test_cases/Mixed_Up_Purses.json](https://github.com/Jkotheimer/Chromium_Titanium_Pigeon_Sticks/blob/master/test_cases/Mixed_Up_Purses.json).
The program simply takes the json file's location as a command line parameter, reads the file into a Java object using the Jackson ObjectMapper, and does a ton of parsing and logic to solve the puzzle.

## Software Requirements Report

1.	Read the JSON file: the program must be able to read the given JSON file

2.	Organize the contents into appropriate data structures: the program must organize the clues into ClueMap and puzzle contents into PuzzleSolver

3.	Create Java HashMap from JSON file through Jackson ObjectMapper: the program should take file contents and create a Java HashMap which is mapped to an Arraylist that contains each different category per puzzle and their attributes.

4.	Read individual clues: the program must be able to read through individual clues given through the ClueMap by opening the file into a JSONNode and parsing through.

5.	Iterate: the program must be able to iterate through clues until the HashMap contains 1 node with 1 object in its corresponding mapped ArrayList, per each category. I.e. Paula (node) lost her key (1 item in the category Lost), her last name is Johnson (1 item in the category LastName), and she is a pilot (1 item in the category Job). Categories are subject to change per each puzzle.

6.	Indicate solution: the program must finish iteration and indicate the final contents of each mapped ArrayList. These contents will indicate the solution of the puzzle by printing the previously mapped node with its 1 remaining object per category. (As noted above).

7.	Keep track of iterations: the program will have a counter that will keep track of the iterations through the set of clues

8.	Operate with any puzzle: the program will work with any added JSON file that contains a similar puzzle with 5 people and given attributes and clues

9.	 Indicate state: the program will print update to user when it has removed a certain number of items from the array. This would indicate the state of the program and how much longer it needs to iterate. 

## Usage

1. After cloning this repository onto your local machine, open a terminal and cd into the master directory.

2. If you are not using Windows, ensure you have jre 1.8.0 or higher installed on your machine.

3. Run either `./puzzle_solver` or `puzzle_solver.exe` (depending on your operating system) followed by the path to one of the test case json files (or your own json file if it is configured properly)

    Example: `./puzzle_solver test_cases/Mixed_Up_Purses.json`

- If you wish to generate your own json file from a puzzle to solve, follow the general outline of one of the given test cases.
