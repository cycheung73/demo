package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Advent of Code 2024: Day 3 Puzzle 1
 * 
 **/
public class Puzzle1 {
    public static void main(String[] args) {
	Puzzle1 puzzle1 = new Puzzle1();
	puzzle1.run(args);
    }

    public void run(String[] args) {
        System.out.println("input file is called " + args[0]);
	Scanner sc = null;

	Pattern mulPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
	System.out.println(mulPattern.pattern());

	int productSum = 0;
	try {
	    sc = new Scanner(new BufferedReader(new FileReader(args[0])));

	    while (sc.hasNextLine()) {
		String line = sc.nextLine();
		Scanner lineScan = new Scanner(line);

		String multiply = lineScan.findInLine(mulPattern);
		while (multiply != null) {
		    System.out.print(multiply + ", ");

		    Matcher mulMatch = mulPattern.matcher(multiply);
		    if (mulMatch.matches()) {
			try {
			    int factor01 = Integer.parseInt(mulMatch.group(1));
			    int factor02 = Integer.parseInt(mulMatch.group(2));
			    int product = factor01 * factor02;
			    System.out.println("" + factor01 + "x" + factor02 + "=" + product);
			    productSum += product;
			} catch (NumberFormatException nfe) {
			    // we know from the pattern that this will be 1 to 3 digits, so we should never hit this exception
			    // data is clean, but this is where we would handle errors
			    System.err.println(nfe.getMessage());
			}
		    }

		    multiply = lineScan.findInLine(mulPattern);
		}
	    }
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
	System.out.println("sum of mul(X,Y) " + productSum);
    }
}
