package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Advent of Code 2024: Day 9 Puzzle 2
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
	    compactedBlocks[i] = fileBlocks[i];
	}

	while (endIndex >= 0) {
	    int fileSize = 0;
	    int fileId = 0;
	    // we found a file
	    if (compactedBlocks[endIndex] != -1) {
		fileId = compactedBlocks[endIndex];
		System.out.println("found fileId " + fileId);
		// find the beginning of the file (since we are going backwards)
		while ((endIndex >= 0) && (compactedBlocks[endIndex] == fileId)) {
		    fileSize++;
		    endIndex--;
		}
		System.out.println("fileSize is " + fileSize + ", endIndex is " + endIndex);
	    } else {
		endIndex--;
		continue;
	    }

	    int blankSize = 0;
	    int index = 0;
	    // starting at the beginning, try to find enough blank space to fit the file
	    while ((index <= endIndex) && (blankSize < fileSize)) {
		blankSize = 0;
		if (compactedBlocks[index] == -1) {
		    // System.out.println("found a blank");
		    while (compactedBlocks[index] == -1) {
			blankSize++;
			index++;
		    }
		} else {
		    index++;
		}
		// System.out.println("index is " + index + ", blankSize is " + blankSize);
	    }
	    // either we checked up to the file or we found enough space

	    // found an empty space that can fit the file
	    if ((fileSize > 0) && (blankSize > 0) && (blankSize >= fileSize)) {
		System.out.println("blankSize is " + blankSize + ", fileSize is " + fileSize);
		// move file to empty space
		// System.out.println("(index - blankSize) is " + (index - blankSize));
		// System.out.println("(index - blankSize) + fileSize is " + ((index - blankSize) + fileSize));
		for (int i = (index - blankSize); i < ((index - blankSize) + fileSize); i++) {
		    compactedBlocks[i] = compactedBlocks[endIndex+1];
		}
		// printFileBlocks(compactedBlocks);
		// blank out file
		// System.out.println("(endIndex + 1) is " + (endIndex + 1));
		// System.out.println("(endIndex + 1) + fileSize is " + ((endIndex + 1) + fileSize));
		for (int i = endIndex + 1; i < ((endIndex + 1) + fileSize); i++) {
		    compactedBlocks[i] = -1;
		}
		// printFileBlocks(compactedBlocks);
	    } else {
		System.out.println("fileId cannot be moved " + fileId);
	    }
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
