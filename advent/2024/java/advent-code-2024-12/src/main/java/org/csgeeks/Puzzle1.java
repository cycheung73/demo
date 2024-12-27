package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Advent of Code 2024 #23: Day 12 Puzzle 1
 * 
 **/
public class Puzzle1 {
    public static void main(String[] args) {
	Puzzle1 puzzle1 = new Puzzle1();
	puzzle1.run(args);
    }

    public void run(String[] args) {
        System.out.println("input file is called " + args[0]);
	List<String[]> rowsList = new ArrayList<String[]>();
	int  width = 0;
	int height = 0;

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));

	    while (scan.hasNextLine() ) {
		String line = scan.nextLine();

		String[] row = line.split("");
		rowsList.add(row);
		width = row.length;
	    }
	    height = rowsList.size();

	    String[][] gardenMap = rowsList.toArray(new String[height][width]);

	    // for (int y = 0; y < height; y++) {
	    // 	System.out.print("+");
	    // 	for (int x = 0; x < width; x++) {
	    // 	    System.out.print("-+");
	    // 	}
	    // 	System.out.println();

	    // 	System.out.print("|");
	    // 	for (int x = 0; x < width; x++) {
	    // 	    System.out.print(gardenMap[y][x] + "|");
	    // 	}
	    // 	System.out.println();
	    // }
	    // System.out.print("+");
	    // for (int x = 0; x < width; x++) {
	    // 	System.out.print("-+");
	    // }
	    // System.out.println();

	    Map<String, List<Point>> dataList = calculateData(gardenMap);

	    int totalPrice = 0;
	    for (String plot : dataList.keySet()) {
		for (Point data : dataList.get(plot)) {
		    System.out.println("plot " + plot + " has area " + data.getX() + " and perimeter " + data.getY());
		    totalPrice += (data.getX() * data.getY());
		}
	    }
	    System.out.println("total price is " + totalPrice);
	    System.out.println("done");
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    public Map<String, List<Point>> calculateData(String[][] gardenMap) {
	Map<String, List<Point>> plotList = new HashMap<String, List<Point>>();

	int height = gardenMap.length;
	int  width = gardenMap[0].length;

	String[][] trackMap = new String[height][];
	for (int h = 0; h < trackMap.length; h++) {
	    trackMap[h] = gardenMap[h].clone();
	}

	System.out.println(width + " x " + height + " map");

	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		String gardenPlot = trackMap[y][x];
		if (!gardenPlot.equals(".")) {
		    Point gardenData = getArea(gardenMap, trackMap, x, y);
		    List<Point> areaList = plotList.get(gardenPlot);
		    if (areaList == null) {
			areaList = new ArrayList<Point>();
			plotList.put(gardenPlot, areaList);
		    }
		    areaList.add(gardenData);
		}
	    }
	}

	return plotList;
    }

    public Point getArea(String[][] gardenMap, String[][] trackMap, int x, int y) {
	int height = gardenMap.length;
	int  width = gardenMap[0].length;

	int area  = 1;
	int perim = 4;
	String gardenPlot = gardenMap[y][x];
	trackMap[y][x] = ".";

	// check up
	if (inBounds(x, y - 1, width, height) && (trackMap[y - 1][x].equals(gardenPlot))) {
	    Point data = getArea(gardenMap, trackMap, x, y - 1);
	    area  += data.getX();
	    perim += data.getY();
	}
	// check down
	if (inBounds(x, y + 1, width, height) && (trackMap[y + 1][x].equals(gardenPlot))) {
	    Point data = getArea(gardenMap, trackMap, x, y + 1);
	    area  += data.getX();
	    perim += data.getY();
	}
	// check left
	if (inBounds(x - 1, y, width, height) && (trackMap[y][x - 1].equals(gardenPlot))) {
	    Point data = getArea(gardenMap, trackMap, x - 1, y);
	    area  += data.getX();
	    perim += data.getY();
	}
	// check right
	if (inBounds(x + 1, y, width, height) && (trackMap[y][x + 1].equals(gardenPlot))) {
	    Point data = getArea(gardenMap, trackMap, x + 1, y);
	    area  += data.getX();
	    perim += data.getY();
	}

	// correct for perimeter
	if (inBounds(x, y - 1, width, height) && (gardenMap[y - 1][x].equals(gardenPlot))) {
	    perim -= 1;
	}
	if (inBounds(x, y + 1, width, height) && (gardenMap[y + 1][x].equals(gardenPlot))) {
	    perim -= 1;
	}
	if (inBounds(x - 1, y, width, height) && (gardenMap[y][x - 1].equals(gardenPlot))) {
	    perim -= 1;
	}
	if (inBounds(x + 1, y, width, height) && (gardenMap[y][x + 1].equals(gardenPlot))) {
	    perim -= 1;
	}

	return new Point(area, perim);
    }

    public boolean inBounds(int x, int y, int width, int height) {
	return ((x >= 0 && x < width) && (y >= 0 && y < height));
    }
}
