package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Advent of Code 2024 #12: Day 6 Puzzle 2
 *
 * given a map:
 * [^][>][v][<] - guard facing up [0], right [1], down [2], left [3]
 * [.] - empty space
 * [#] - obstruction
 *
 * from the starting situation, using the rule for pathing:
 * - if there is something directly in front of you, turn right 90 degrees
 * - otherwise, take a step forward
 *
 * calculate:
 * - how many potential loops can be made if a new obstruction is introduced
 **/
public class Puzzle2 {
    public record Position(int x, int y, int face) {}
    public static final String[] FACE = {"^", ">", "v", "<"};
    public static final String OBSTRUCTION = "#";
    public static final String NEW_OBSTRUCTION = "O";

    public static void main(String[] args) {
	Puzzle2 puzzle2 = new Puzzle2();
	puzzle2.run(args);
    }

    public void run(String[] args) {
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

	// printGrid(guardGrid);

	int height = guardGrid.length;
	int width  = guardGrid[0].length;

	int[][] moveGrid = new int[height][width];
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		moveGrid[y][x] = 0;
	    }
	}

	System.out.println("width x height is " + width + " x " + height);

	// find guard in grid (one of: "^", ">", "v", "<")
	Position guardPos = findGuard(guardGrid);
	moveGrid[guardPos.y()][guardPos.x()] |= (1 << guardPos.face());

	if (guardPos == null) {
	    // data is clean, but this is where we would handle errors
	    System.err.println("guard is not on the map");
	} else {
	    System.out.println("found guard at position (" + guardPos.x() + ", " + guardPos.y() + ") facing " + guardPos.face());
	    // printGrid(guardGrid);
	}


	String[][] obstGrid = deepCopyGrid(guardGrid);
	Position obstPos = new Position(guardPos.x(), guardPos.y(), guardPos.face());
	List<Position> obstList = new ArrayList<Position>();

	int loops = 0;
	int count = 0;
	while (obstPos != null) {
	    System.out.println("outside loop " + count);

	    Position nextPos = moveObst(obstGrid, obstPos);
	    // printGrid(guardGrid);
	    // printMove(moveGrid);

	    if (nextPos == null) {
		// we have stepped outside of the grid
		break;
	    }
	    if ((nextPos.x() == obstPos.x()) && (nextPos.y() == obstPos.y()) && (nextPos.face() != obstPos.face())) {
		// we have turned due to an obstruction, so skip "new" obstruction check
		System.out.println("skip");
		obstPos = nextPos;
		continue;
	    }
	    Position obstPosNoFace = new Position(nextPos.x(), nextPos.y(), 0);
	    if (obstList.contains(obstPosNoFace)) {
		// we have already placed an obstruction here, so skip
		System.out.println("skip");
		obstPos = nextPos;
		continue;
	    }

	    // start guard walk
	    String[][] newGrid = deepCopyGrid(guardGrid);
	    int[][]    newMove = deepCopyMove(moveGrid);
	    Position    newPos = new Position(guardPos.x(), guardPos.y(), guardPos.face());
	    System.out.println("obstruction position at (" + newPos.x() + ", " + newPos.y() + ") facing " + newPos.face());

	    // add new obstruction
	    newGrid[nextPos.y()][nextPos.x()] = NEW_OBSTRUCTION;
	    // printGrid(newGrid);

	    // will it loop
	    boolean willLoop = willGuardLoop(newGrid, newMove, newPos);

	    System.out.println("will guard loop " + willLoop);
	    if (willLoop) {
		obstList.add(obstPosNoFace);
		System.out.println("new obstruction at (" + nextPos.x() + ", " + nextPos.y() + ")");
		System.out.println("obstruction facing (" + nextPos.face() + ")");
		loops++;
		// printGrid(newGrid);
	    }

	    // update obstruction posision
	    obstPos = nextPos;
	    count++;
	}
	// printGrid(guardGrid);

	System.out.println("loops " + loops);
    }

    public String[][] deepCopyGrid(String[][] grid) {
	String[][] clonedGrid = new String[grid.length][grid[0].length];
	for (int j = 0; j < grid.length; j++) {
	    clonedGrid[j] = grid[j].clone();
	}
	return clonedGrid;
    }

    public int[][] deepCopyMove(int[][] grid) {
	int[][] clonedGrid = new int[grid.length][grid[0].length];
	for (int j = 0; j < grid.length; j++) {
	    clonedGrid[j] = grid[j].clone();
	}
	return clonedGrid;
    }

    public void printGrid(String[][] grid) {
	for (String[] row : grid) {
	    for (String pos : row) {
		System.out.print(pos + "");
	    }
	    System.out.println();
	}
    }

    public void printMove(int[][] grid) {
	for (int[] row : grid) {
	    for (int move : row) {
		System.out.print("" + move + ":");
	    }
	    System.out.println();
	}
    }

    public Position findGuard(String[][] grid) {
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


    public Position moveGuard(String[][] grid, Position pos, int[][] move) {
	return moveMeta(grid, pos, move, true);
    }


    public Position moveObst(String[][] grid, Position pos) {
	return moveMeta(grid, pos, null, false);
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
    public Position moveMeta(String[][] grid, Position pos, int[][] move, boolean track) {
	int height = grid.length;
	int width  = grid[0].length;

	int x0 = pos.x();
	int y0 = pos.y();
	int face0 = pos.face();
	// int bit0 = (1 << face0);

	String face = FACE[face0];
	if (!grid[y0][x0].equals(face)) {
	    // data is clean, but this is where we would handle errors
	    System.out.println("pos is wrong (" + x0 + ", " + y0 + ") facing " + face0 + " face " + face + " grid[][] " + grid[y0][x0]);
	    return null;
	}

	// take a step
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
	    return null;
	}
	// if (grid[y1][x1].equals(FACE[face1])) {
	//     // stop: we have already walked this way in this direction
	//     return pos;
	// }
	if (grid[y1][x1].equals(OBSTRUCTION) || grid[y1][x1].equals(NEW_OBSTRUCTION)) {
	    // turn
	    x1 = x0;
	    y1 = y0;
	    face1 = (face0 + 1) % 4;
	}

	if (track) {
	    int bit1 = (1 << face1);
	    // System.out.println("grid[" + y1 + "][" + x1 + "] " + grid[y1][x1] + ":" + FACE[face1]);
	    // System.out.println("move[" + y1 + "][" + x1 + "] " + move[y1][x1] + ":" + bit1 + " move[][] & bit = " + (move[y1][x1] & bit1));
	    if ((move[y1][x1] & bit1) != 0) {
		// stop: we have already faced this way previously
		System.out.println("move[" + y1 + "][" + x1 + "] " + move[y1][x1] + ":" + bit1 + " move[][] & bit = " + (move[y1][x1] & bit1));
		// grid[y1][x1] = "X"; // guard stops
		// printGrid(grid);
		return pos; // loop
	    }
	    move[y1][x1] |= bit1;
	    // System.out.println("move[" + y1 + "][" + x1 + "] " + move[y1][x1] + ":" + bit1);
	}
	grid[y1][x1] = FACE[face1];
	// System.out.println("grid[" + y1 + "][" + x1 + "] " + grid[y1][x1] + ":" + FACE[face1]);

	return new Position(x1, y1, face1);
    }

    public boolean willGuardLoop(String[][] guardGrid, int[][] guardMove, Position guardPos) {
	// System.out.println("inside loop");
	Position newGuardPos = moveGuard(guardGrid, guardPos, guardMove);
	while ((newGuardPos != null) && (!newGuardPos.equals(guardPos))) {
	    // System.out.println("old guard position (" + guardPos.x() + ", " + guardPos.y() + ") facing " + guardPos.face());
	    guardPos = newGuardPos;
	    // printGrid(guardGrid);
	    // System.out.println("new guard position (" + guardPos.x() + ", " + guardPos.y() + ") facing " + guardPos.face());
	    // System.out.println("inside loop");
	    newGuardPos = moveGuard(guardGrid, guardPos, guardMove);
	}

	return (newGuardPos != null);
    }
}
