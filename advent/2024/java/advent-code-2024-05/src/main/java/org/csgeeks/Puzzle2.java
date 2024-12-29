package org.csgeeks;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * Advent of Code 2024: Day 5 Puzzle 2
 * 
 * page order rules, one per line
 * X|Y
 * page X must come before page Y
 * 
 * followed by blank line
 *
 * followed by lines of pages to be printed, separated by commas
 * P1,P2,P3,P4,...,Pn
 * 
 * for each invalid lines of pages, fix the order, then find the fixed middle page number
 * obtain the sum of all the fixed middle page numbers
 **/
public class Puzzle2 {
    public static void main(String[] args) {
	Puzzle2 puzzle2 = new Puzzle2();
	puzzle2.run(args);
    }

    public void run(String[] args) {
        System.out.println("input file is called " + args[0]);
	Map<String, List<String>> rulesMap = new HashMap<String, List<String>>();

	try {
	    Scanner scan = new Scanner(new BufferedReader(new FileReader(args[0])));

	    while (scan.hasNextLine() ) {
		String line = scan.nextLine();
		if (line.equals("")) {
		    // System.out.println("found blank line");
		    break;
		}

		// System.out.println("print rule " + line);
		// System.out.println("rule " + line);
		List<String>ruleList = new ArrayList<String>(Arrays.asList(line.split("\\|")));
		if (ruleList.size() != 2) {
		    // data is clean, but this is where we would handle errors
		    System.err.println("rule is invalid");
		} else {
		    String page01 = ruleList.get(0);
		    String page02 = ruleList.get(1);
		    // System.out.println("page " + page01 + " before page " + page02);

		    List<String> pageRule = rulesMap.get(page01);
		    if (pageRule == null) {
			// create new page rule
			pageRule = new ArrayList<String>();
			pageRule.add(page02);
			rulesMap.put(page01, pageRule);
		    } else {
			// update existing page rule
			pageRule.add(page02);
		    }
		}
	    }

	    for (String page : rulesMap.keySet()) {
		System.out.print("page " + page + " before pages " );
		for (String next : rulesMap.get(page)) {
		    System.out.print("" + next + ",");
		}
		System.out.println();
	    }

	    int pageSum = 0;
	    while (scan.hasNextLine()) {
		String line = scan.nextLine();
		// System.out.println("pages list " + line);
		ArrayList<String>pagesList = new ArrayList<String>(Arrays.asList(line.split(",")));
		// System.out.print("print pages ");
		// for (String page : pagesList) {
		//     System.out.print("" + page + ",");
		// }
		// System.out.println();

		int pageIndex = validatePageOrder(rulesMap, pagesList);
		if (pageIndex == 0) {
		    // valid page, skip
		    continue;
		}
		// invalid page

		// fix the page order
		while (pageIndex != 0) {
		    System.out.print("pages list is invalid for ");
		    for (String page : pagesList) {
			System.out.print("" + page + ",");
		    }
		    System.out.println();
		    System.out.println("swap pages " + (pageIndex - 1) + " and " + pageIndex);

		    // since the rules break at pageIndex, we know pages (pageIndex-1) and pageIndex are out of order, hence the swap
		    Collections.swap(pagesList, (pageIndex - 1), pageIndex);
		    pageIndex = validatePageOrder(rulesMap, pagesList);
		}
		System.out.print("pages list is valid for ");
		for (String page : pagesList) {
		    System.out.print("" + page + ",");
		}
		System.out.println();


		// get the middle page
		int listSize = pagesList.size();
		if ((listSize % 2) == 0) {
		    // data is clean, but this is where we would handle errors
		    System.err.println("print page list is even " + listSize);
		    // no definition of "middle" if the print page list size is even
		} else {
		    int middle = (listSize + 1) / 2;
		    System.out.println("middle is " + middle);
		    System.out.println("middle page is " + pagesList.get(middle - 1));
		    pageSum += Integer.parseInt(pagesList.get(middle - 1));
		}
	    }

	    System.out.println("page sum " + pageSum);
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }

    // return 0 if valid
    // otherwise return pageIndex where rules break
    // note: rules breakage cannot start at 0 because are no pages in front of index 0 for the rule to break
    public int validatePageOrder(Map<String,List<String>> rulesMap, ArrayList<String> pagesList) {
	boolean valid = true;
	// System.out.println("pagesList.size() " + pagesList.size());

	ArrayList<String>checkList = (ArrayList<String>)pagesList.clone();  // shallow clone is okay, since we are not changing the contents of the entries
	int pageSize = pagesList.size();
	int pageIndex = 0;
	while (valid && (checkList.size() > 1)) {
	    pageIndex = (pageSize - checkList.size()) + 1;
	    // forward check
	    String pageBefore = checkList.remove(0);
	    // System.out.println("page before is " + pageBefore);
	    List<String> rulesForPage = rulesMap.get(pageBefore);
	    for (String page : checkList) {
		if ((rulesForPage == null) || !rulesForPage.contains(page)) {
		    System.out.println("rule breaks for page " + page);
		    valid = false;
		    break;
		}
		pageIndex++;
	    }
	}
	return (valid ? 0 : pageIndex);
    }
}
