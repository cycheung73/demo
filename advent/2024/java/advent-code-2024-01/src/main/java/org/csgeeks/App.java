package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

/**
 * Advent of Code 2024: Day 1 Puzzle 1
 * 2 lists
 * sort lists in increasing order
 * pair entries by position
 * for each pair, calculate the distance between the two (positive value)
 * total the distances
 **/
public class App {
    public static void main(String[] args) {
	System.out.println("input file is called " + args[0]);
	Scanner sc = null;
	ArrayList list1 = new ArrayList<Integer>();
	ArrayList list2 = new ArrayList<Integer>();

	try {
	    sc = new Scanner(new BufferedReader(new FileReader(args[0])));

	    int count = 0;
	    while (sc.hasNextInt()) {
		Integer first = sc.nextInt();
		list1.add(first);
		if (sc.hasNextInt()) {
		    Integer second = sc.nextInt();
		    System.out.println("line is: " + first + ", " + second);
		    list2.add(second);
		} else {
		    // data is clean, but this is where we would handle errors
		    System.out.println("line is: " + first);
		    System.err.println("missing pair");
		}
		count++;
	    }
	    System.out.println("found " + count);

	    // data is clean, but this is where we would handle errors
	    if (list1.size() != list2.size()) {
		System.err.println("mismatched sizes");
	    }

	    Collections.sort(list1);
	    Collections.sort(list2);

	    Integer sumDist = new Integer(0);
	    for (int i = 0; i < list1.size(); i++) {
		Integer dist = Math.abs(((Integer)list1.get(i)) - ((Integer)list2.get(i)));
		System.out.println("" + list1.get(i) + ", " + list2.get(i) + ", " + dist);
		sumDist += dist;
	    }

	    System.out.println("total distance is " + sumDist);
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }
}
