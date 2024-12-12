package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Advent of Code 2024 #21: Day 11 Puzzle 1
 * 
 **/
public class App {
    public static void main(String[] args) {
        System.out.println("input file is called " + args[0]);
	List<Long> stonesList = new ArrayList<Long>();

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));

	    while (scan.hasNextLine() ) {
		String line = scan.nextLine();
		Scanner lineScan = new Scanner(line);

		while (lineScan.hasNextLong()) {
		    Long stone = lineScan.nextLong();
		    stonesList.add(stone);
		}
	    }

	    System.out.println("Initial arrangement:");
	    printStones(stonesList);
	    System.out.println();

	    // System.out.println("After 1 blink:");
	    // stonesList = blink(stonesList);
	    // printStones(stonesList);
	    // System.out.println();

	    // System.out.println("After 2 blink:");
	    // stonesList = blink(stonesList);
	    // printStones(stonesList);
	    // System.out.println();

	    // System.out.println("After 3 blink:");
	    // stonesList = blink(stonesList);
	    // printStones(stonesList);
	    // System.out.println();

	    // System.out.println("After 4 blink:");
	    // stonesList = blink(stonesList);
	    // printStones(stonesList);
	    // System.out.println();

	    // System.out.println("After 5 blink:");
	    // stonesList = blink(stonesList);
	    // printStones(stonesList);
	    // System.out.println();

	    // System.out.println("After 6 blink:");
	    // stonesList = blink(stonesList);
	    // printStones(stonesList);
	    // System.out.println();

	    // System.out.println("stones list has " + stonesList.size() + " stones");

	    int blinks = Integer.parseInt(args[1]);
	    for (int i = 0; i < blinks; i++) {
		stonesList = blink(stonesList);
		// printStones(stonesList);
		// System.out.println();
	    }

	    // printStones(stonesList);
	    System.out.println("After " + blinks + " blinks, there are " + stonesList.size() + " stones");
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    public static void printStones(List<Long> stonesList) {
	for (Long stone : stonesList) {
	    System.out.print(stone + " ");
	}
	System.out.println();
    }

    public static List<Long> blink(List<Long> stonesList) {
	List<Long> returnList = new ArrayList<Long>();

	if ((stonesList == null) || (stonesList.size() == 0)) {
	    return returnList;
	}

	for (Long stone : stonesList) {
	    String stoneString = stone.toString();
	    int stoneStringLength = stoneString.length();

	    if (stone == 0) {
		returnList.add(new Long(1));
	    } else if ((stoneStringLength % 2) == 0) {
		returnList.add(Long.parseLong(stoneString.substring(0, stoneStringLength/2)));
		returnList.add(Long.parseLong(stoneString.substring(stoneStringLength/2, stoneStringLength)));
	    } else {
		returnList.add(stone * 2024);
	    }
	}

	return returnList;
    }
}
