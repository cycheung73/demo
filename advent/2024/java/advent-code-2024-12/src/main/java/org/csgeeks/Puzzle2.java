package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * Advent of Code 2024 #24: Day 12 Puzzle 2
 * 
 **/
public class Puzzle2 {
    public static void main(String[] args) {
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

	    Map<String, List<Plot>> areaList = calculateData(gardenMap);

	    int totalPrice = 0;
	    for (String plotKey : areaList.keySet()) {
		for (Plot plotValue : areaList.get(plotKey)) {
		    int  area = plotValue.getArea();
		    int sides = 4;
		    System.out.println("plot " + plotKey + " has area " + area);
		    Map<String, List<Point>> cornersMap = plotValue.getCornersMap();
		    if (cornersMap.size() > 0) {
			// System.out.println("with corners");
			int numAllCorners = 0;
			int spareCorners = 0;
			List<Integer> numCornersList = new ArrayList<Integer>();
			for (String dir : cornersMap.keySet()) {
			    int size = cornersMap.get(dir).size();
			    numCornersList.add(size);
			    // System.out.println("corner " + dir + " with size " + size);
			    // for (Point point : cornersMap.get(dir)) {
			    // 	System.out.println("at " + point);
			    // }
			}
			if (numCornersList.size() == 4) {
			    Collections.sort(numCornersList);
			    // for (Integer num : numCornersList) {
			    // 	System.out.println(num);
			    // }
			    numAllCorners = numCornersList.get(0);
			}
			for (int corners : numCornersList) {
			    spareCorners += (corners - numAllCorners);
			}
			System.out.print(" with all-corners " + numAllCorners + " and spare-corners " + spareCorners + " ");
			sides += (numAllCorners * 4) + (spareCorners * 2);
		    }
		    System.out.println(" with sides " + sides);

		    totalPrice += (area * sides);
		}
	    }
	    System.out.println("total price is " + totalPrice);
	    System.out.println("done");
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    public static Map<String, List<Plot>> calculateData(String[][] gardenMap) {
	Map<String, List<Plot>> plotList = new HashMap<String, List<Plot>>();

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
		    Plot gardenData = getData(gardenMap, trackMap, x, y);
		    List<Plot> areaList = plotList.get(gardenPlot);
		    if (areaList == null) {
			areaList = new ArrayList<Plot>();
			plotList.put(gardenPlot, areaList);
		    }
		    areaList.add(gardenData);
		}
	    }
	}

	return plotList;
    }

    public static Plot getData(String[][] gardenMap, String[][] trackMap, int x, int y) {
	int height = gardenMap.length;
	int  width = gardenMap[0].length;

	int area  = 1;
	Map<String, List<Point>> cornersMap = new HashMap<String, List<Point>>();
	cornersMap.put(Plot.UP_RIGHT, new ArrayList<Point>());
	cornersMap.put(Plot.DOWN_RIGHT, new ArrayList<Point>());
	cornersMap.put(Plot.UP_LEFT, new ArrayList<Point>());
	cornersMap.put(Plot.DOWN_LEFT, new ArrayList<Point>());
	String gardenPlot = gardenMap[y][x];
	trackMap[y][x] = ".";

	// calculate area
	// check up
	if (inBounds(x, y - 1, width, height) && (trackMap[y - 1][x].equals(gardenPlot))) {
	    Plot data = getData(gardenMap, trackMap, x, y - 1);
	    area += data.getArea();
	    // cornersMap.putAll(data.getCornersMap());
	    cornersMap.get(Plot.UP_RIGHT).addAll(data.getCornersMap().get(Plot.UP_RIGHT));
	    cornersMap.get(Plot.DOWN_RIGHT).addAll(data.getCornersMap().get(Plot.DOWN_RIGHT));
	    cornersMap.get(Plot.UP_LEFT).addAll(data.getCornersMap().get(Plot.UP_LEFT));
	    cornersMap.get(Plot.DOWN_LEFT).addAll(data.getCornersMap().get(Plot.DOWN_LEFT));
	}
	// check down
	if (inBounds(x, y + 1, width, height) && (trackMap[y + 1][x].equals(gardenPlot))) {
	    Plot data = getData(gardenMap, trackMap, x, y + 1);
	    area += data.getArea();
	    // cornersMap.putAll(data.getCornersMap());
	    cornersMap.get(Plot.UP_RIGHT).addAll(data.getCornersMap().get(Plot.UP_RIGHT));
	    cornersMap.get(Plot.DOWN_RIGHT).addAll(data.getCornersMap().get(Plot.DOWN_RIGHT));
	    cornersMap.get(Plot.UP_LEFT).addAll(data.getCornersMap().get(Plot.UP_LEFT));
	    cornersMap.get(Plot.DOWN_LEFT).addAll(data.getCornersMap().get(Plot.DOWN_LEFT));
	}
	// check left
	if (inBounds(x - 1, y, width, height) && (trackMap[y][x - 1].equals(gardenPlot))) {
	    Plot data = getData(gardenMap, trackMap, x - 1, y);
	    area += data.getArea();
	    // cornersMap.putAll(data.getCornersMap());
	    cornersMap.get(Plot.UP_RIGHT).addAll(data.getCornersMap().get(Plot.UP_RIGHT));
	    cornersMap.get(Plot.DOWN_RIGHT).addAll(data.getCornersMap().get(Plot.DOWN_RIGHT));
	    cornersMap.get(Plot.UP_LEFT).addAll(data.getCornersMap().get(Plot.UP_LEFT));
	    cornersMap.get(Plot.DOWN_LEFT).addAll(data.getCornersMap().get(Plot.DOWN_LEFT));
	}
	// check right
	if (inBounds(x + 1, y, width, height) && (trackMap[y][x + 1].equals(gardenPlot))) {
	    Plot data = getData(gardenMap, trackMap, x + 1, y);
	    area += data.getArea();
	    // cornersMap.putAll(data.getCornersMap());
	    cornersMap.get(Plot.UP_RIGHT).addAll(data.getCornersMap().get(Plot.UP_RIGHT));
	    cornersMap.get(Plot.DOWN_RIGHT).addAll(data.getCornersMap().get(Plot.DOWN_RIGHT));
	    cornersMap.get(Plot.UP_LEFT).addAll(data.getCornersMap().get(Plot.UP_LEFT));
	    cornersMap.get(Plot.DOWN_LEFT).addAll(data.getCornersMap().get(Plot.DOWN_LEFT));
	}

	// calculate corners
	// up and right, but no NE diagonal
	if ((inBounds(x, y - 1, width, height) && (gardenMap[y - 1][x].equals(gardenPlot))) &&
	    (inBounds(x + 1, y, width, height) && (gardenMap[y][x + 1].equals(gardenPlot))) &&
	    (!gardenMap[y - 1][x + 1].equals(gardenPlot))) {
	    System.out.println(gardenPlot + " has an up-right corner at (" + x + "," + y + ")");
	    List<Point> upRightList = cornersMap.get(Plot.UP_RIGHT);
	    upRightList.add(new Point(x, y));
	}
	// down and right, but no SE diagonal
	if ((inBounds(x, y + 1, width, height) && (gardenMap[y + 1][x].equals(gardenPlot))) &&
	    (inBounds(x + 1, y, width, height) && (gardenMap[y][x + 1].equals(gardenPlot))) &&
	    (!gardenMap[y + 1][x + 1].equals(gardenPlot))) {
	    System.out.println(gardenPlot + " has a down-right corner at (" + x + "," + y + ")");
	    List<Point> downRightList = cornersMap.get(Plot.DOWN_RIGHT);
	    downRightList.add(new Point(x, y));
	}
	// up and left, but no NW diagonal
	if ((inBounds(x, y - 1, width, height) && (gardenMap[y - 1][x].equals(gardenPlot))) &&
	    (inBounds(x - 1, y, width, height) && (gardenMap[y][x - 1].equals(gardenPlot))) &&
	    (!gardenMap[y - 1][x - 1].equals(gardenPlot))) {
	    System.out.println(gardenPlot + " has an up-left corner at (" + x + "," + y + ")");
	    List<Point> upLeftList = cornersMap.get(Plot.UP_LEFT);
	    upLeftList.add(new Point(x,y));
	}
	// down and left, but no SW diagonal
	if ((inBounds(x, y + 1, width, height) && (gardenMap[y + 1][x].equals(gardenPlot))) &&
	    (inBounds(x - 1, y, width, height) && (gardenMap[y][x - 1].equals(gardenPlot))) &&
	    (!gardenMap[y + 1][x - 1].equals(gardenPlot))) {
	    System.out.println(gardenPlot + " has a down-left corner at (" + x + "," + y + ")");
	    List<Point> downLeftList = cornersMap.get(Plot.DOWN_LEFT);
	    downLeftList.add(new Point(x,y));
	}

	return new Plot(area, cornersMap);
    }

    public static boolean inBounds(int x, int y, int width, int height) {
	return ((x >= 0 && x < width) && (y >= 0 && y < height));
    }
}
