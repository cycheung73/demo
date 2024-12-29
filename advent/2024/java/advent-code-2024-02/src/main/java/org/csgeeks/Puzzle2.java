package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Advent of Code 2024: Day 2 Puzzle 2
 * rows of numbers
 * row is safe if:
 * numbers are all increasing or all decreasing
 * two adjacent numbers differ by at least 1 and at most 3
 * if removing one value from an unsafe row, makes it safe, it is also safe
 * find number of safe rows
 **/
public class Puzzle2 {
    public static void main(String[] args) {
	Puzzle2 puzzle2 = new Puzzle2();
	puzzle2.run(args);
    }

    public void run(String[] args) {
        System.out.println("input file is called " + args[0]);
	Scanner sc = null;

	try {
	    sc = new Scanner(new BufferedReader(new FileReader(args[0])));

	    int count = 0;
	    int safeCount = 0;
	    while (sc.hasNextLine()) {
		String line = sc.nextLine();
		Scanner lineScan = new Scanner(line);

		ArrayList row = new ArrayList<Integer>();
		while (lineScan.hasNextInt()) {
		    Integer level = lineScan.nextInt();
		    System.out.print("" + level + ", ");
		    row.add(level);
		}
		if (isSafe(row)) {
		    System.out.print("safe");
		    safeCount++;
		} else {
		    // recheck with 1 value missing
		    boolean safe = false;
		    for (int i = 0; i < row.size(); i++) {
			// shallow copy is fine since we are not altering the entries of the list, just removing entries
			List<Integer> damp = (List<Integer>)row.clone();
			Integer removed = damp.remove(i);
			if (safe = isSafe(damp)) {
			    System.out.print("safe, if remove row[" + i + "] " + removed);
			    safeCount++;
			    break;
			}
		    }
		    if (!safe) {
			System.out.print("not safe");
		    }
		}

		System.out.println();
		count++;
	    }
	    System.out.println("found " + count);
	    System.out.println("safe " + safeCount);
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    private boolean isSafe(List<Integer> row) {
	Integer prev = null;
	Boolean incr = null;
	boolean safe = true;
	for (int i = 0; i < row.size(); i++) {
	    Integer level = row.get(i);
	    if (prev != null) {
		int diff = prev - level;
		// difference is out of bounds: not (at least 1 and at most 3)
		if ((diff < -3) || (diff == 0) || (diff > 3)) {
		    System.out.print("not safe, difference is " + diff + "; ");
		    safe = false;
		    break;
		}
		
		if (incr == null) {
		    // set initial difference sign
		    incr = (diff > 0);
		} else {
		    // changed direction: current difference sign is not same as initial difference sign
		    if (incr != (diff > 0)) {
			System.out.print("not safe, direction is " + (diff > 0) + "; ");
			safe = false;
			break;
		    }
		}
	    }
	    prev = level;
	}
	return safe;
    }
}
