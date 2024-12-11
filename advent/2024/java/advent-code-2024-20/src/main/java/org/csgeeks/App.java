package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * Advent of Code 2024 #20: Day 10 Puzzle 2
 * 
 **/
public class App {
    public static void main(String[] args) {
        System.out.println("input file is called " + args[0]);
	int mapWidth = 0;
	int mapHeight = 0;

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));
	    List<List<Integer>> topographicMap = new ArrayList<List<Integer>>();

	    while (scan.hasNextLine() ) {
		String line = scan.nextLine();
		String[] row = line.split("");

		List<Integer> rowList = new ArrayList<Integer>();
		for (String height : row) {
		    rowList.add(Integer.parseInt(height));
		}

		topographicMap.add(rowList);

		if (mapWidth == 0) {
		    mapWidth = row.length;
		}
		mapHeight++;
	    }

	    System.out.println("map is " + mapWidth + " x " + mapHeight);

	    for (List<Integer> topographicRow : topographicMap) {
		for (Integer topographicHeight : topographicRow) {
		    System.out.print("" + topographicHeight);
		}
		System.out.println();
	    }

	    int totalRating = 0;
	    List<Point> trailHeads = findTrailHeads(topographicMap);
	    for (Point point : trailHeads) {
		int rating = trailRating(point, topographicMap);
		System.out.println("trail head at " + point + " with a rating of " + rating);
		totalRating += rating;
	    }

	    System.out.println("sum of the ratings of all trailheads is " + totalRating);
	    System.out.println("done");
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    public static List<Point> findTrailHeads(List<List<Integer>> topographicMap) {
	List<Point> trailHeads = new ArrayList<Point>();

	for (int y = 0; y < topographicMap.size(); y++) {
	    List<Integer> topographicRow = topographicMap.get(y);
	    for (int x = 0; x < topographicRow.size(); x++) {
		if (topographicRow.get(x).equals(0)) {
		    trailHeads.add(new Point(x, y));
		}
	    }
	}

	return trailHeads;
    }

    public static boolean inBounds(Point point, int width, int height) {
	return ((point.getX() >= 0 && point.getX() < width) &&
		(point.getY() >= 0 && point.getY() < height));
    }

    public static int trailRating(Point point, List<List<Integer>> topographicMap) {
	int mapHeight = topographicMap.size();
	int mapWidth  = topographicMap.get(0).size();

	int rating = 0;

	int x = point.getX();
	int y = point.getY();

	// System.out.print("at " + point + ", ");

	Point upPoint    = new Point(x, y - 1);
	Point downPoint  = new Point(x, y + 1);
	Point leftPoint  = new Point(x - 1, y);
	Point rightPoint = new Point(x + 1, y);
	
	int currentTopographicHeight = topographicMap.get(y).get(x);
	// System.out.println("at height " + currentTopographicHeight);

	if (currentTopographicHeight == 9) {
	    return 1;
	}

	if (inBounds(upPoint, mapWidth, mapHeight)) {
	    // System.out.println("check up");
	    int upTopographicHeight = topographicMap.get(upPoint.getY()).get(upPoint.getX());
	    if (upTopographicHeight == currentTopographicHeight + 1) {
		rating += trailRating(upPoint, topographicMap);
	    }
	}

	if (inBounds(downPoint, mapWidth, mapHeight)) {
	    // System.out.println("check down");
	    int downTopographicHeight = topographicMap.get(downPoint.getY()).get(downPoint.getX());
	    if (downTopographicHeight == currentTopographicHeight + 1) {
		rating += trailRating(downPoint, topographicMap);
	    }
	}

	if (inBounds(leftPoint, mapWidth, mapHeight)) {
	    // System.out.println("check left");
	    int leftTopographicHeight = topographicMap.get(leftPoint.getY()).get(leftPoint.getX());
	    if (leftTopographicHeight == currentTopographicHeight + 1) {
		rating += trailRating(leftPoint, topographicMap);
	    }
	}

	if (inBounds(rightPoint, mapWidth, mapHeight)) {
	    // System.out.println("check right");
	    int rightTopographicHeight = topographicMap.get(rightPoint.getY()).get(rightPoint.getX());
	    if (rightTopographicHeight == currentTopographicHeight + 1) {
		rating += trailRating(rightPoint, topographicMap);
	    }
	}

	return rating;
    }
}
