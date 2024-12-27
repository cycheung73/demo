package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Advent of Code 2024 #17: Day 9 Puzzle 1
 * 
 **/
public class Puzzle1 {
    public static void main(String[] args) {
	Puzzle1 puzzle1 = new Puzzle1();
	puzzle1.run(args);
    }

    public void run(String[] args) {
        System.out.println("input file is called " + args[0]);

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));

	    while (scan.hasNextLine()) {
		String line = scan.nextLine();
		System.out.println("line is " + line);

		String[] diskMap = line.split("");
		// for (String digit : diskMap) {
		//     System.out.println("digit is " + digit);
		// }

		int[] fileBlocks = diskMapToFileBlocks(diskMap);
		printFileBlocks(fileBlocks);

		int[] compactedBlocks = compactFileBlocks(fileBlocks);
		printFileBlocks(compactedBlocks);

		long checksum = getFilesystemChecksum(compactedBlocks);
		System.out.println("checksum is " + checksum);
	    }
	    System.out.println("done");
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    public void printFileBlocks(int[] fileBlocks) {
	for (int i = 0; i < fileBlocks.length; i++) {
	    if (fileBlocks[i] == -1) {
		System.out.print(".");
	    } else {
		System.out.print("" + fileBlocks[i]);
	    }
	}
	System.out.println();
    }

    public int[] diskMapToFileBlocks(String[] diskMap) {
	int index = 0;
	int fileBlockSize = 0;
	int[] diskMapInt = new int[diskMap.length];
	for (String digit : diskMap) {
	    diskMapInt[index] = Integer.parseInt(digit);
	    fileBlockSize += diskMapInt[index];
	    index++;
	}

	int[] fileBlocks = new int[fileBlockSize];
	int fileId = 0;
	int fileBlockIndex = 0;
	for (int i = 0; i < diskMap.length; i++) {
	    if ((i % 2) == 0) {
		for (int j = 0; j < diskMapInt[i]; j++) {
		    fileBlocks[fileBlockIndex] = fileId;
		    fileBlockIndex++;
		}
		fileId++;
	    }
	    if ((i % 2) == 1) {
		for (int j = 0; j < diskMapInt[i]; j++) {
		    fileBlocks[fileBlockIndex] = -1;
		    fileBlockIndex++;
		}
	    }
	}

	return fileBlocks;
    }

    public int[] compactFileBlocks(int[] fileBlocks) {
	int endIndex = fileBlocks.length - 1;  // array from 0 to fileBlockSize-1
	int[] compactedBlocks = new int[fileBlocks.length];
	for (int i = 0; i < compactedBlocks.length; i++) {
	    compactedBlocks[i] = -1;
	}

	for (int index = 0; index <= endIndex; index++) {
	    if (fileBlocks[index] == -1) {
		while (fileBlocks[endIndex] == -1) {
		    endIndex--;
		}
		compactedBlocks[index] = fileBlocks[endIndex];
		compactedBlocks[endIndex] = -1;
		endIndex--;
	    } else {
		compactedBlocks[index] = fileBlocks[index];
	    }
	    // printFileBlocks(compactedBlocks);
	}

	return compactedBlocks;
    }

    public long getFilesystemChecksum(int[] fileBlocks) {
	long checksum = 0;
	for (int i = 0; i < fileBlocks.length; i++) {
	    if (fileBlocks[i] != -1) {
		// System.out.println("" + i + " x " + fileBlocks[i] + " is " + (i * fileBlocks[i]));
		checksum += (i * fileBlocks[i]);
	    }
	}
	return checksum;
    }
}
