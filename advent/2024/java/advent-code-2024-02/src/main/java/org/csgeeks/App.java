package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Advent of Code 2024: Day 1 Puzzle 2
 * 2 lists
 * similarity score is the number of times an entry exists in other list
 * calculate similarity score total of left list over the right list
 */
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
		System.err.println("mistmatched sizes");
	    }

	    Integer sumSimScore = new Integer(0);
	    for (int i = 0; i < list1.size(); i++) {
		Integer simFact = Collections.frequency(list2, (Integer)list1.get(i));
		Integer simScore = ((Integer)list1.get(i)) * simFact;
		System.out.println("" + list1.get(i) + ", " + simFact + ", " + simScore);
		sumSimScore += simScore;
	    }

	    System.out.println("total similarity score for first list over second list is " + sumSimScore);
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }
}
