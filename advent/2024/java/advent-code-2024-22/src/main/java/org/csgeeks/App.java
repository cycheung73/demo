package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Advent of Code 2024 #22: Day 11 Puzzle 2
 * 
 **/
public class App {
    public static void main(String[] args) {
        System.out.println("input file is called " + args[0]);
	// List<Point> stonesList = new ArrayList<Point>();
	Map<Long, Long> stonesMap = new HashMap<Long, Long>();

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));

	    while (scan.hasNextLine() ) {
		String line = scan.nextLine();
		Scanner lineScan = new Scanner(line);

		while (lineScan.hasNextLong()) {
		    Long stone = lineScan.nextLong();
		    stonesMap.put(stone, 1L);
		}
	    }

	    System.out.println("Initial arrangement:");
	    printStones(stonesMap);
	    System.out.println();

	    long blinks = Long.parseLong(args[1]);
	    long totalStones = 0;

	    for (int i = 0; i < blinks; i++) {
		stonesMap = blink(stonesMap);
		// printStones(stonesMap);
		// System.out.println();
	    }

	    for (long key : stonesMap.keySet()) {
		totalStones += stonesMap.get(key);
	    }

	    // printStones(stonesMap);
	    System.out.println("After " + blinks + " blinks, there are " + totalStones + " stones");

	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    public static void printStones(Map<Long, Long> stonesMap) {
	for (Long stone : stonesMap.keySet()) {
	    System.out.print("(" + stone + " x " + stonesMap.get(stone) + ") ");
	}
	System.out.println();
    }

    public static Map<Long, Long> blink(Map<Long, Long> stonesMap) {
	Map<Long, Long> returnMap = new HashMap<Long, Long>();
	if ((stonesMap == null) || (stonesMap.size() == 0)) {
	    return stonesMap;
	}

	for (Long stone : stonesMap.keySet()) {
	    Long           stoneValue = stonesMap.get(stone);
	    String        stoneString = stone.toString();
	    int     stoneStringLength = stoneString.length();

	    if (stone == 0L) {
		Long one = 1L;

		Long oneCount = returnMap.get(1);
		returnMap.put(one, (oneCount == null ? 0 : oneCount) + stoneValue);
	    } else if ((stoneStringLength % 2) == 0) {
		Long upperSplit = Long.parseLong(stoneString.substring(0, stoneStringLength/2));
		Long lowerSplit = Long.parseLong(stoneString.substring(stoneStringLength/2, stoneStringLength));

		Long upperCount = returnMap.get(upperSplit);
		returnMap.put(upperSplit, (upperCount == null ? 0 : upperCount) + stoneValue);

		Long lowerCount = returnMap.get(lowerSplit);
		returnMap.put(lowerSplit, (lowerCount == null ? 0 : lowerCount) + stoneValue);
	    } else {
		Long newStone = (stone * 2024L);

		Long newCount = returnMap.get(newStone);
		returnMap.put(newStone, (newCount == null ? 0 : newCount) + stoneValue);
	    }
	}

	return returnMap;
    }
}
