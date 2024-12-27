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

/**
 * Advent of Code 2024 #09: Day 5 Puzzle 1
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
 * for each valid lines of pages, find the middle page number
 * obtain the sum of all the valid middle page numbers
 **/
public class Puzzle1 {
    public static void main(String[] args) {
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
		// System.out.println("print list " + line);
		ArrayList<String>printList = new ArrayList<String>(Arrays.asList(line.split(",")));
		// System.out.print("print pages ");
		// for (String page : printList) {
		//     System.out.print("" + page + ",");
		// }
		// System.out.println();

		boolean valid = true;
		// System.out.println("printList.size() " + printList.size());

		ArrayList<String>checkList = (ArrayList<String>)printList.clone();  // shallow clone is okay, since we are not changing the contents of the entries
		while (valid && (checkList.size() > 1)) {
		    // forward check
		    String pageBefore = checkList.remove(0);
		    // System.out.println("page before is " + pageBefore);
		    List<String> rulesForPage = rulesMap.get(pageBefore);
		    for (String page : checkList) {
			if ((rulesForPage == null) || !rulesForPage.contains(page)) {
			    // System.out.println("rule breaks for page " + page);
			    valid = false;
			    break;
			}
		    }
		}

		if (valid) {
		    System.out.print("print list is valid for ");
		    for (String page : printList) {
			System.out.print("" + page + ",");
		    }
		    System.out.println();

		    int listSize = printList.size();
		    if ((listSize % 2) == 0) {
			// data is clean, but this is where we would handle errors
			System.err.println("print page list is even " + listSize);
			// no definition of "middle" if the print page list size is even
		    } else {
			int middle = (listSize + 1) / 2;
			System.out.println("middle is " + middle);
			System.out.println("middle page is " + printList.get(middle - 1));
			pageSum += Integer.parseInt(printList.get(middle - 1));
		    }
		}
	    }

	    System.out.println("page sum " + pageSum);
	} catch (IOException ioex) {
	    // data is clean, but this is where we would handle errors
	    System.err.println(ioex.getMessage());
	}
    }
}
