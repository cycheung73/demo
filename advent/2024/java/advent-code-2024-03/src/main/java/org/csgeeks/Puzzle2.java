package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Advent of Code 2024 #06: Day 3 Puzzle 2
 * 
 **/
public class Puzzle2 {
    public static void main(String[] args) {
	Puzzle2 puzzle2 = new Puzzle2();
	puzzle2.run(args);
    }

    public void run(String[] args) {
        System.out.println("input file is called " + args[0]);
	Scanner sc = null;

	Pattern mulPattern  = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
	Pattern doPattern   = Pattern.compile("do\\(\\)");
	Pattern dontPattern = Pattern.compile("don't\\(\\)");

	Pattern nextPattern = Pattern.compile("(" + mulPattern.pattern() + ")|(" + doPattern.pattern() + ")|(" + dontPattern.pattern() + ")");

	System.out.println(nextPattern.pattern());

	int productSum = 0;
	boolean doSum = true;
	try {
	    sc = new Scanner(new BufferedReader(new FileReader(args[0])));

	    while (sc.hasNextLine()) {
		String line = sc.nextLine();
		Scanner lineScan = new Scanner(line);

		String token = lineScan.findInLine(nextPattern);
		while (token != null) {
		    System.out.print(token + ", ");

		    Matcher doMatch = doPattern.matcher(token);
		    if (doMatch.matches()) {
			doSum = true;
		    }

		    Matcher dontMatch = dontPattern.matcher(token);
		    if (dontMatch.matches()) {
			doSum = false;
		    }

		    Matcher mulMatch = mulPattern.matcher(token);
		    if (mulMatch.matches()) {
			try {
			    int factor01 = Integer.parseInt(mulMatch.group(1));
			    int factor02 = Integer.parseInt(mulMatch.group(2));
			    int product = factor01 * factor02;
			    System.out.println("" + factor01 + "x" + factor02 + "=" + product);
			    if (doSum) {
				productSum += product;
			    }
			} catch (NumberFormatException nfe) {
			    // we know from the pattern that this will be 1 to 3 digits, so we should never hit this exception
			    // data is clean, but this is where we would handle errors
			    System.err.println(nfe.getMessage());
			}
		    }

		    token = lineScan.findInLine(nextPattern);
		}
	    }
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
	System.out.println("sum of mul(X,Y) " + productSum);
    }
}
