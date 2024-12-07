package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent of Code 2024 #11: Day 6 Puzzle 1
 *
 * given a map:
 * [^][>][v][<] - guard facing up [0], right [1], down [2], left [3]
 * [.] - empty space
 * [#] - obstacle
 * [X] - part of guard's path
 *
 * from the starting situation, using the rule for pathing:
 * - if there is something directly in front of you, turn right 90 degrees
 * - otherwise, take a step forward
 *
 * calculate:
 * how many distinct positions will the guard visit before leaving the mapped area?
 **/
public class App {
    public record Position(int x, int y, int face) {}
    public static final String[] FACE = {"^", ">", "v", "<"};
    public static final String OBSTACLE = "#";
    public static final String WALKED = "X";

    public static void main(String[] args) {
        System.out.println("input file is called " + args[0]);
	List<String[]> rows = new ArrayList<String[]>();

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));

	    while (scan.hasNextLine() ) {
		String line = scan.nextLine();
		String[] values = line.split("");
		rows.add(values);
	    }
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}

	String[][] guardGrid = rows.toArray(new String[0][]);

	// for (String[] row : guardGrid) {
	//     for (String pos : row) {
	// 	System.out.print(pos + ":");
	//     }
	//     System.out.println();
	// }

	int height = guardGrid.length;
	int width  = guardGrid[0].length;

	System.out.println("width x height is " + width + " x " + height);

	// find guard in grid (one of: "^", ">", "v", "<")
	Position guardPos = findGuard(guardGrid);

	if (guardPos == null) {
	    // data is clean, but this is where we would handle errors
	    System.err.println("guard is not on the map");
	} else {
	    System.out.println("found guard at position (" + guardPos.x() + ", " + guardPos.y() + ") facing " + guardPos.face());
	}

	while (guardPos != null) {
	    guardPos = moveGuard(guardGrid, guardPos);
	    // for (String[] row : guardGrid) {
	    // 	for (String pos : row) {
	    // 	    System.out.print(pos + ":");
	    // 	}
	    // 	System.out.println();
	    // }
	    if (guardPos != null) {
		System.out.println("new guard position (" + guardPos.x() + ", " + guardPos.y() + ") facing " + guardPos.face());
	    } else {
		System.out.println("guard walked off the map");
	    }
	}

	int numGridWalked = findAllWalked(guardGrid);

	System.out.println("total grids walked by guard " + numGridWalked);
    }

    public static Position findGuard(String[][] grid) {
	Position pos = null;
	for (int y = 0; y < grid.length; y++) {
	    for (int x = 0; x < grid[0].length; x++) {
		if (grid[y][x].equals("^")) {
		    pos = new Position(x, y, 0);
		    break;
		}
		if (grid[y][x].equals(">")) {
		    pos = new Position(x, y, 1);
		    break;
		}
		if (grid[y][x].equals("v")) {
		    pos = new Position(x, y, 2);
		    break;
		}
		if (grid[y][x].equals("<")) {
		    pos = new Position(x, y, 3);
		    break;
		}
	    }
	}

	return pos;
    }

    // move guard
    // ^ - move up, neg y (-1 y)
    // > - move right, pos x (+1 x)
    // v - move down, pos y (+1 y)
    // < - move left, neg x (-1 x)
    // if obstacle, turn right
    // ^ - turn right => >
    // > - turn right => v
    // v - turn right => <
    // < - turn right => ^
    public static Position moveGuard(String[][] grid, Position guardPos) {
	int height = grid.length;
	int width  = grid[0].length;

	int x0 = guardPos.x();
	int y0 = guardPos.y();
	int face0 = guardPos.face();

	String guard = FACE[face0];
	if (!grid[y0][x0].equals(guard)) {
	    // data is clean, but this is where we would handle errors
	    System.err.println("guardPos is wrong");
	    return null;
	}
	int x1 = -1;
	int y1 = -1;
	int face1 = -1;
	if (face0 == 0) { // move up, -1 y
	    x1 = x0;
	    y1 = y0 - 1;
	    face1 = face0;
	}
	if (face0 == 1) { // move right, +1 x
	    x1 = x0 + 1;
	    y1 = y0;
	    face1 = face0;
	}
	if (face0 == 2) { // move down, +1 y
	    x1 = x0;
	    y1 = y0 + 1;
	    face1 = face0;
	}
	if (face0 == 3) { // move left, -1 x
	    x1 = x0 - 1;
	    y1 = y0;
	    face1 = face0;
	}
	if (((x1 < 0) || (x1 >= width)) || ((y1 < 0) || (y1 >= height))) {
	    // walking off the grid
	    grid[y0][x0] = WALKED;
	    return null;
	}
	if (grid[y1][x1].equals(OBSTACLE)) {
	    x1 = x0;
	    y1 = y0;
	    face1 = (face0 + 1) % 4;
	} else {
	    grid[y0][x0] = WALKED;
	}
	grid[y1][x1] = FACE[face1];

	return new Position(x1, y1, face1);
    }

    public static int findAllWalked(String[][] grid) {
	int count = 0;
	for (int y = 0; y < grid.length; y++) {
	    for (int x = 0; x < grid[0].length; x++) {
		if (grid[y][x].equals(WALKED)) count++;
	    }
	}

	return count;
    }
}
