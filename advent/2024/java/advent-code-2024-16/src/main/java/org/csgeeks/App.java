package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * Advent of Code 2024 #16: Day 8 Puzzle 2
 * 
 **/
public class App {

    public static void main(String[] args) {
        System.out.println("input file is called " + args[0]);

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));
	    int height = 0;
	    int width = 0;

	    Map<String, List<Point>> antennaMap = new HashMap<String, List<Point>>();
	    Set<Point> antinodeSet = new HashSet<Point>();

	    int y = 0;
	    while (scan.hasNextLine() ) {
		String line = scan.nextLine();
		// System.out.println("line is " + line);
		String[] lineElements = line.split("");
		width = lineElements.length;

		for (int x = 0; x < width; x++) {
		    // System.out.print(lineElements[x] + ":");
		    if (!lineElements[x].equals(".")) {
			Point point = new Point(x, y);
			System.out.println("found " + lineElements[x] + " at " + point);

			List<Point> pointsList = antennaMap.get(lineElements[x]);
			if (pointsList == null) {
			    pointsList = new ArrayList<Point>();
			    antennaMap.put(lineElements[x], pointsList);
			}
			pointsList.add(point);
		    }
		}
		// System.out.println();
		y++;
	    }
	    height = y;

	    System.out.println("width x height is " + width + " x " + height);

	    for (String key : antennaMap.keySet()) {
		List<Point> pointsList = antennaMap.get(key);
		System.out.print("For key " + key + ", there are " + pointsList.size() + " points ");
		for (Point point : pointsList) {
		    antinodeSet.add(point);
		    System.out.print(point + ",");
		}
		System.out.println();
		Point[] pointsArray = pointsList.toArray(new Point[pointsList.size()]);
		for (int i = 0; i < (pointsArray.length - 1); i++) {
		    for (int j = i + 1; j < pointsArray.length; j++) {
			System.out.println("comparing " + pointsArray[i] + " with " + pointsArray[j]);
			Point delta = pointsArray[i].subtract(pointsArray[j]);  // I - J
			// System.out.println("delta is " + delta);
			// System.out.println(pointsArray[i] + " plus  " + delta + " is " + pointsArray[i].add(delta));
			// System.out.println(pointsArray[j] + " minus " + delta + " is " + pointsArray[j].subtract(delta));
			Point point01 = pointsArray[i].add(delta);       // I + delta
			Point point02 = pointsArray[j].subtract(delta);  // J - delta

			while (inBound(point01, width, height)) {
			    antinodeSet.add(point01);
			    point01 = point01.add(delta);
			}
			while (inBound(point02, width, height)) {
			    antinodeSet.add(point02);
			    point02 = point02.subtract(delta);
			}
		    }
		}
	    }

	    for (Point point : antinodeSet) {
		System.out.println(point);
	    }

	    System.out.println("unique antinodes " + antinodeSet.size());
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    public static boolean inBound(Point point, int width, int height) {
	return (((point.getX() >= 0) && (point.getX() < width)) && ((point.getY() >= 0) && (point.getY() < height)));
    }
}
