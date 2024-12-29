package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * Advent of Code 2024: Day 13 Puzzle 1
 * 
 **/
public class Puzzle1 {
    public static final long TOKENS_FOR_BUTTON_A = 3L;
    public static final long TOKENS_FOR_BUTTON_B = 1L;

    public static void main(String[] args) {
	Puzzle1 puzzle1 = new Puzzle1();
	puzzle1.run(args);
    }

    public void run(String[] args) {
        System.out.println("input file is called " + args[0]);

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));
	    Point buttonA = null;
	    Point buttonB = null;
	    Point prize = null;
	    long totalTokens = 0;

	    while (scan.hasNextLine()) {
		String line = scan.nextLine();
		System.out.println(line);

		Scanner lineScan = new Scanner(line);
		if (lineScan.hasNext()) {
		    lineScan.findInLine("(.+): X[+=](\\d+), Y[+=](\\d+)");
		    MatchResult result = lineScan.match();
		    if (result.group(1).equals("Button A")) {
			buttonA = new Point(Long.parseLong(result.group(2)), Long.parseLong(result.group(3)));
		    }
		    if (result.group(1).equals("Button B")) {
			buttonB = new Point(Long.parseLong(result.group(2)), Long.parseLong(result.group(3)));
		    }
		    if (result.group(1).equals("Prize")) {
			prize = new Point(Long.parseLong(result.group(2)), Long.parseLong(result.group(3)));
		    }
		}

		if ((buttonA != null) && (buttonB != null) && (prize != null)) {
		    long tokens = checkWinnable(buttonA, buttonB, prize);
		    buttonA = null;
		    buttonB = null;
		    prize = null;
		    System.out.println("tokens " + tokens);
		    totalTokens += tokens;
		}
	    }
	    System.out.println("total tokens " + totalTokens);
	    System.out.println("done");
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    long checkWinnable(Point buttonA, Point buttonB, Point prize) {
	System.out.println("Button A: " + buttonA);
	System.out.println("Button B: " + buttonB);
	System.out.println("Prize: " + prize);

	long buttonAX = buttonA.getX();
	long buttonAY = buttonA.getY();

	long buttonBX = buttonB.getX();
	long buttonBY = buttonB.getY();

	long prizeX = prize.getX();
	long prizeY = prize.getY();

	long pressesBX = prizeX / buttonBX;
	long pressesBY = prizeY / buttonBY;

	long pressesB = ((pressesBX < pressesBY) ? pressesBX : pressesBY);

	boolean found = false;

	long pressesA = 0;

	while (!found && (pressesB > 0)) {
	    long prizeAX = prizeX - (pressesB * buttonBX);
	    long prizeAY = prizeY - (pressesB * buttonBY);

	    if ((prizeAX % buttonAX == 0) && (prizeAY % buttonAY == 0)) {
		long pressesAX = prizeAX / buttonAX;
		long pressesAY = prizeAY / buttonAY;
		if (pressesAX == pressesAY) {
		    found = true;
		    pressesA = pressesAX;
		    continue;
		}
	    }
	    pressesB--;
	}

	return (found ? (pressesA * TOKENS_FOR_BUTTON_A) + (pressesB * TOKENS_FOR_BUTTON_B) : 0);
    }
}
