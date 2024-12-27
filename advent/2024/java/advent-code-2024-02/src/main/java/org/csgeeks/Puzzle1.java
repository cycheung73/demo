package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Advent of Code 2024 #03: Day 2 Puzzle 1
 * rows of numbers
 * row is safe if:
 * numbers are all increasing or all decreasing
 * two adjacent numbers differ by at least 1 and at most 3
 * find number of safe rows
 **/
public class Puzzle1 {
    public static void main(String[] args) {
	Puzzle1 puzzle1 = new Puzzle1();
	puzzle1.run(args);
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

		Integer prev = null;
		Boolean incr = null;

		boolean safe = true;

		while (lineScan.hasNextInt() && safe) {
		    Integer level = lineScan.nextInt();
		    System.out.print("" + level + ", ");
		    if (prev != null) {
			int diff = prev - level;
			// difference is out of bounds: not (at least 1 and at most 3)
			if ((diff < -3) || (diff == 0) || (diff > 3)) {
			    System.out.print("not safe, difference is " + diff);
			    safe = false;
			    break;
			}

			if (incr == null) {
			    // set initial difference sign
			    incr = (diff > 0);
			} else {
			    // changed direction: current difference sign is not same as initial difference sign
			    if (incr != (diff > 0)) {
				System.out.print("not safe, direction is " + (diff > 0));
				safe = false;
				break;
			    }
			}
		    }
		    prev = level;
		}
		if (safe) {
		    System.out.print("safe");
		    safeCount++;
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
}
