package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Advent of Code 2024 #14: Day 7 Puzzle 2
 * 
 **/
public class Puzzle2 {
    public static void main(String[] args) {
	Puzzle2 puzzle2 = new Puzzle2();
	puzzle2.run(args);
    }

    public void run(String[] args) {
        System.out.println("input file is called " + args[0]);

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));
	    long totalCalibration = 0;

	    while (scan.hasNextLine() ) {
		String line = scan.nextLine();
		// System.out.println("line is " + line);

		Scanner lineScan = new Scanner(line);
		Long equationResult = null;
		List<Long> operandList = new ArrayList<Long>();
		if (lineScan.hasNext("\\d+:")) {
		    String firstToken = lineScan.next("\\d+:");
		    // remove trailing ":", no need for null check because we lineScan.hasNext() is true and therefore lineScan.next() must return something ending with ":"
		    firstToken = firstToken.substring(0, firstToken.length() - 1);
		    // System.out.println("first token is " + firstToken);
		    equationResult = Long.parseLong(firstToken);
		}
		while (lineScan.hasNext("\\d+")) {
		    String nextToken = lineScan.next("\\d+");
		    // System.out.println("next token is " + nextToken);
		    operandList.add(Long.parseLong(nextToken));
		}
		System.out.print("result is " + equationResult + "; operands are");
		for (Long operand : operandList) {
		    System.out.print(" " + operand);
		}
		System.out.println();
		if (possibleEquation(equationResult, operandList)) {
		    System.out.println("possible equation with result " + equationResult);
		    totalCalibration += equationResult;
		}
		// System.out.println("end of line");
	    }

	    System.out.println("total calibration " + totalCalibration);
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    public boolean possibleEquation(Long equationResult, List<Long> operandList) {
	int operationLimit = (int)Math.pow(3, (operandList.size() - 1)) - 1;
	// System.out.println("operationLimit is " + operationLimit);
	// System.out.println("equationResult is " + equationResult);
	for (int operationMask = 0; operationMask <= operationLimit; operationMask++) {
	    Long operationResult = applyOperation(operandList, operationMask);
	    // System.out.println("operationResult is " + operationResult);
	    if (equationResult.equals(operationResult)) {
		return true;
	    }
	}
	return false;
    }

    // operationMask: 0 is addition, 1 is multiplication; LSB is the first operation
    public Long applyOperation(List<Long> operandList, int operationMask) {
	// System.out.println("applyOperation");
	Long result = operandList.get(0);
	for (int i = 1; i < operandList.size(); i++) {
	    int operation = operationMask % 3;
	    // System.out.println("operation " + operation);
	    if (operation == 0) {
		result += operandList.get(i);
	    }
	    if (operation == 1) {
		result *= operandList.get(i);
	    }
	    if (operation == 2) {
		result = Long.parseLong(result.toString() + operandList.get(i).toString());
	    }
	    operationMask = operationMask / 3;
	}
	// System.out.println("result " + result);
	return result;
    }
}
