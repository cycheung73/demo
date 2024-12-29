package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent of Code 2024: Day 4 Puzzle 2
 * 
 **/
public class Puzzle2 {
    public static void main(String[] args) {
	Puzzle2 puzzle2 = new Puzzle2();
	puzzle2.run(args);
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

	int totalCount = 0;
	String[][] box1 = {
	    {"M", ".", "S"},
	    {".", "A", "."},
	    {"M", ".", "S"}
	};
	totalCount += findAllBoxes(dataArray, box1);

	String[][] box2 = {
	    {"M", ".", "M"},
	    {".", "A", "."},
	    {"S", ".", "S"}
	};
	totalCount += findAllBoxes(dataArray, box2);

	String[][] box3 = {
	    {"S", ".", "M"},
	    {".", "A", "."},
	    {"S", ".", "M"}
	};
	totalCount += findAllBoxes(dataArray, box3);

	String[][] box4 = {
	    {"S", ".", "S"},
	    {".", "A", "."},
	    {"M", ".", "M"}
	};
	totalCount += findAllBoxes(dataArray, box4);

	System.out.println("total count " + totalCount);
    }

    public int findAllBoxes(String[][] grid, String[][] box) {
	int width = grid[0].length;
	int height = grid.length;

	int count = 0;
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		if (findBox(grid, box, x, y)) count++;
	    }
	}

	return count;
    }

    // given grid
    // origin is at the upper left corner.  positive x is to the right.  positive y is down.
    //
    // match box, where "." matches anything
    public boolean findBox(String[][] grid, String[][] box, int x, int y) {
	int gridWd = grid[0].length;
	int gridHt = grid.length;

	int boxWd = box[0].length;
	int boxHt = box.length;

	if ((x + boxWd) > gridWd) {
	    // out of bounds
	    return false;
	}
	if ((y + boxHt) > gridHt) {
	    // out of bounds
	    return false;
	}

	for (int j = 0; j < boxHt; j++) {
	    for (int i = 0; i < boxWd; i++) {
		if (box[j][i].equals(".")) continue;
		if (!grid[y + j][x + i].equals(box[j][i])) {
		    return false;
		}
	    }
	}
	return true;
    }
}
