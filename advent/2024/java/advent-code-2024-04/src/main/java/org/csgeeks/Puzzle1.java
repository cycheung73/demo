package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent of Code 2024 #07: Day 4 Puzzle 1
 * 
 **/
public class Puzzle1 {
    public static void main(String[] args) {
	Puzzle1 puzzle1 = new Puzzle1();
	puzzle1.run(args);
    }

    public void run(String[] args) {
        System.out.println("input file is called " + args[0]);
	List<String[]> rows = new ArrayList<String[]>();
	
	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));

	    while (scan.hasNextLine()) {
		String line = scan.nextLine();
		String[] values = line.split("");
		rows.add(values);
	    }
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}

	String[][] dataArray = rows.toArray(new String[0][]);

	for (String[] row : dataArray) {
	    for (String value : row) {
		System.out.print(value + ",");
	    }
	    System.out.println();
	}

	int width = dataArray[0].length;
	int height = dataArray.length;

	System.out.println("width x height is " + width + " x " + height);

	String[] word = {"X", "M", "A", "S"};
	for (int i = 0; i < word.length; i++) {
	    System.out.print(word[i]);
	}
	System.out.println();
	int totalCount = 0;
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		totalCount += findAllDirections(dataArray, word, x, y);
	    }
	}

	System.out.println("total count " + totalCount);
    }

    // given grid
    // origin is at the upper left corner.  positive x is to the right.  positive y is down.
    //
    // find word at location (x,y) in all 8 directions
    //
    // 1 - horizontally from (x,y) going in the positive x direction
    // 2 - diagonally from (x,y) going in the positive x and negative y direction
    // 3 - vertically from (x,y) going in the negative y direction
    // 4 - diagonally from (x,y) going in the negative x and negative y direction
    // 5 - horizontally from (x,y) going in the negative x direction
    // 6 - diagonally from (x,y) going in the negative x and  positive y direction
    // 7 - vertically from (x,y) going in the positive y direction
    // 8 - diagonally from (x,y) going in the positive x and positive y direction
    public int findAllDirections(String[][] grid, String[] word, int x, int y) {
	int count = 0;

	if (findDirection1(grid, word, x, y)) count++;
	if (findDirection2(grid, word, x, y)) count++;
	if (findDirection3(grid, word, x, y)) count++;
	if (findDirection4(grid, word, x, y)) count++;
	if (findDirection5(grid, word, x, y)) count++;
	if (findDirection6(grid, word, x, y)) count++;
	if (findDirection7(grid, word, x, y)) count++;
	if (findDirection8(grid, word, x, y)) count++;

	return count;
    }

    // 1 - horizontally from (x,y) going in the positive x direction
    public boolean findDirection1(String[][] grid, String[] word, int x, int y) {
	// System.out.println("findDirection1(" + x + "," + y + ")");
	int width = grid[0].length;
	int height = grid.length;
	int size = word.length;

	if ((x + size) > width) {
	    // System.out.println("false size");
	    return false;
	}
	for (int i = 0; i < size; i++) {
	    // System.out.println("grid(" + (x + i) + "," + y + ") is " + grid[y][x + i]);
	    // System.out.println("word(" + i + ") is " + word[i]);
	    if (!grid[y][x + i].equals(word[i])) {
		// System.out.println("false at " + i);
		return false;
	    }
	}
	// System.out.println("true");
	return true;
    }

    // 2 - diagonally from (x,y) going in the positive x and negative y direction
    public boolean findDirection2(String[][] grid, String[] word, int x, int y) {
	int width = grid[0].length;
	int height = grid.length;
	int size = word.length;

	if ((x + size) > width) {
	    // System.out.println("false size");
	    return false;
	}
	if ((y - (size-1)) < 0) {
	    // System.out.println("false size");
	    return false;
	}

	for (int i = 0; i < size; i++) {
	    // System.out.println("grid(" + (x + i) + "," + (y - i) + ") is " + grid[y - i][x + i]);
	    // System.out.println("word(" + i + ") is " + word[i]);
	    if (!grid[y - i][x + i].equals(word[i])) {
		// System.out.println("false at " + i);
		return false;
	    }
	}
	// System.out.println("true");
	return true;
    }

    // 3 - vertically from (x,y) going in the negative y direction
    public boolean findDirection3(String[][] grid, String[] word, int x, int y) {
	int width = grid[0].length;
	int height = grid.length;
	int size = word.length;

	if ((y - (size-1)) < 0) {
	    // System.out.println("false size");
	    return false;
	}
	for (int i = 0; i < size; i++) {
	    // System.out.println("grid(" + x + "," + (y - i) + ") is " + grid[y - i][x]);
	    // System.out.println("word(" + i + ") is " + word[i]);
	    if (!grid[y - i][x].equals(word[i])) {
		// System.out.println("false at " + i);
		return false;
	    }
	}
	// System.out.println("true");
	return true;
    }

    // 4 - diagonally from (x,y) going in the negative x and negative y direction
    public boolean findDirection4(String[][] grid, String[] word, int x, int y) {
	int width = grid[0].length;
	int height = grid.length;
	int size = word.length;

	if ((x - (size-1)) < 0) {
	    // System.out.println("false size");
	    return false;
	}
	if ((y - (size-1)) < 0) {
	    // System.out.println("false size");
	    return false;
	}

	for (int i = 0; i < size; i++) {
	    // System.out.println("grid(" + (x - i) + "," + (y - i) + ") is " + grid[y - i][x - i]);
	    // System.out.println("word(" + i + ") is " + word[i]);
	    if (!grid[y - i][x - i].equals(word[i])) {
		// System.out.println("false at " + i);
		return false;
	    }
	}
	// System.out.println("true");
	return true;
    }

    // 5 - horizontally from (x,y) going in the negative x direction
    public boolean findDirection5(String[][] grid, String[] word, int x, int y) {
	int width = grid[0].length;
	int height = grid.length;
	int size = word.length;

	if ((x - (size-1)) < 0) {
	    // System.out.println("false size");
	    return false;
	}
	for (int i = 0; i < size; i++) {
	    // System.out.println("grid(" + (x - i) + "," + y + ") is " + grid[y][x - i]);
	    // System.out.println("word(" + i + ") is " + word[i]);
	    if (!grid[y][x - i].equals(word[i])) {
		// System.out.println("false at " + i);
		return false;
	    }
	}
	// System.out.println("true");
	return true;
    }

    // 6 - diagonally from (x,y) going in the negative x and  positive y direction
    public boolean findDirection6(String[][] grid, String[] word, int x, int y) {
	int width = grid[0].length;
	int height = grid.length;
	int size = word.length;

	if ((x - (size-1)) < 0) {
	    // System.out.println("false size");
	    return false;
	}
	if ((y + size) > height) {
	    // System.out.println("false size");
	    return false;
	}

	for (int i = 0; i < size; i++) {
	    // System.out.println("grid(" + (x - i) + "," + (y + i) + ") is " + grid[y + i][x - i]);
	    // System.out.println("word(" + i + ") is " + word[i]);
	    if (!grid[y + i][x - i].equals(word[i])) {
		// System.out.println("false at " + i);
		return false;
	    }
	}
	// System.out.println("true");
	return true;
    }

    // 7 - vertically from (x,y) going in the positive y direction
    public boolean findDirection7(String[][] grid, String[] word, int x, int y) {
	int width = grid[0].length;
	int height = grid.length;
	int size = word.length;

	if ((y + size) > height) {
	    // System.out.println("false size");
	    return false;
	}
	for (int i = 0; i < size; i++) {
	    // System.out.println("grid(" + x + "," + (y + i) + ") is " + grid[y + i][x]);
	    // System.out.println("word(" + i + ") is " + word[i]);
	    if (!grid[y + i][x].equals(word[i])) {
		// System.out.println("false at " + i);
		return false;
	    }
	}
	// System.out.println("true");
	return true;
    }

    // 8 - diagonally from (x,y) going in the positive x and positive y direction
    public boolean findDirection8(String[][] grid, String[] word, int x, int y) {
	int width = grid[0].length;
	int height = grid.length;
	int size = word.length;

	if ((x + size) > width) {
	    // System.out.println("false size");
	    return false;
	}
	if ((y + size) > height) {
	    // System.out.println("false size");
	    return false;
	}

	for (int i = 0; i < size; i++) {
	    // System.out.println("grid(" + (x + i) + "," + (y + i) + ") is " + grid[y + i][x + i]);
	    // System.out.println("word(" + i + ") is " + word[i]);
	    if (!grid[y + i][x + i].equals(word[i])) {
		// System.out.println("false at " + i);
		return false;
	    }
	}
	// System.out.println("true");
	return true;
    }
}
