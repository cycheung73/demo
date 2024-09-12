package org.csgeeks;

// input via Stream
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// input via Scanner
import java.util.Scanner;

// random number generator
import java.util.Random;

// random number generator
import java.util.concurrent.ThreadLocalRandom;

// sorting
import java.util.Arrays;

/**
 * Attributes
 *
 */
public class Attributes 
{
    public static void printChoices()
    {
	System.out.println("  1 - standard array ([15,14,13,12,10,8])");
	System.out.println("  2 - point buy (27 points total)");
	System.out.println("  3 - random (4d6 drop lowest)");
    }

    public static void printArray(int[] array)
    {
	printArray("", array);
    }

    public static void printArray(String prefix, int[] array)
    {
	String arrayFormat = String.format("%s%d, %d, %d, %d, %d, %d",
					   prefix,
					   array[0], array[1], array[2],
					   array[3], array[4], array[5]);
	System.out.println(arrayFormat);
    }

    public static int[] getRandomArray()
    {
	int[] array = new int[6];
	for (int i = 0; i < 6; i++) {
	    array[i] = fourRollsDropLowest(); // d6() + d6() + d6();
	    // System.out.println("next");
	}
	return array;
    }

    public static int d6()
    {
	int roll = 0;
	Random rand = new Random();
	roll = rand.nextInt(6) + 1; // random number between 0 and 5, plus 1
	// roll = ThreadLocalRandom.current().nextInt(1, 6 + 1);  // random number between 1 and 6
	// System.out.println("roll is '" + roll + "'");
	return roll;
    }

    public static int fourRollsDropLowest()
    {
	int[] fourRolls = {d6(), d6(), d6(), d6()};
	Arrays.sort(fourRolls);
	// for (int i = 0; i < fourRolls.length; i++) {
	//     System.out.println(fourRolls[i]);
	// }
	return fourRolls[1] + fourRolls[2] + fourRolls[3];
    }

    public static void assignAttributes(int[] attributes, int[] array)
    {
	// initial attributes
	for (int i = 0; i < 6; i++) {
	    attributes[i] = 0;
	}
	System.out.println("Assign attributes: [1] STR, [2] DEX, [3] CON, [4] INT, [5] WIS, [6] CHA");
	for (int i = 0; i < 6; i++) {
	    int choice = 0;
	    while ((choice < 1) || (choice > 6) || (attributes[choice - 1] != 0)) {
		System.out.println("Assign " + array[i] + " to?");
		try {
		    // input with Scanner
		    Scanner scanIn = new Scanner(System.in);
		    choice = scanIn.nextInt();
		} catch (Exception ex) {
		    System.out.println("Exception: " + ex);
		}
		if ((choice < 1) || (choice > 6) || (attributes[choice - 1] != 0)) {
		    System.out.println("Invalid choice, try again");
		}
	    }
	    attributes[choice - 1] = array[i];
	}
    }

    public static void printAttributes(int[] attributes)
    {
	System.out.println("STR: " + attributes[0]);
	System.out.println("DEX: " + attributes[1]);
	System.out.println("CON: " + attributes[2]);
	System.out.println("INT: " + attributes[3]);
	System.out.println("WIS: " + attributes[4]);
	System.out.println("CHA: " + attributes[5]);
    }

    public static int[] selectPointBuyChoices() {
	final int BLOCKS = 8;
	final int CHOICES = PointBuyChoices.VALUES.length;
	int block = 0;
	int choice = 0;
	while (choice == 0) {
	    for (int i = 0; i < BLOCKS; i++) {
		printArray(String.format("  %d - ", i + 1), PointBuyChoices.VALUES[(block * BLOCKS) + i]);
		if (((block * BLOCKS) + 1) > CHOICES) break;
	    }
	    System.out.println("Select array [1-8] or [9] to go to the next set:");
	    while ((choice < 1) || (choice > 9)) {
		try {
		    // input with Scanner
		    Scanner scanIn = new Scanner(System.in);
		    choice = scanIn.nextInt();
		} catch (Exception ex) {
		    System.out.println("Exception: " + ex);
		}
		if ((choice < 1) || (choice > 9)) {
		    System.out.println("Invalid choice, try again");
		}
	    }
	    System.out.println("Choice is: '" + choice + "'");
	    if ((choice >= 1) && (choice <= 8)) {
		choice = ((block * BLOCKS) + choice);
	    } else {
		// choice is 9; advance to the next block
		block++;
		// if at the end; reset block
		if ((block * BLOCKS) >= CHOICES) {
		    block = 0;
		}
		choice = 0;  // reset choice
	    }
	}
	return PointBuyChoices.VALUES[choice - 1];
    }

    public static void main( String[] args )
    {
        System.out.println("Hello, World!  This is Attributes.");
	System.out.println("How would you like to generate your attributes?");

	// get input
	int choice = 0;
	while ((choice < 1) || (choice > 3)) {
	    printChoices();
	    try {
		// input with Stream
		// BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		// choice = Integer.parseInt(reader.readLine());
		
		// input with Scanner
		Scanner scanIn = new Scanner(System.in);
		choice = scanIn.nextInt();

		// input with Console
		// choice = Integer.parseInt(System.console().readLine());
	    } catch (Exception ex) {
		System.out.println("Exception: " + ex);
	    }
	    if ((choice < 1) || (choice > 3)) {
		System.out.println("Invalid choice, try again");
	    }
	}

	System.out.println("You chose: '" + choice + "'");

	int[] array;

	switch (choice) {
	case 1:
	    // standard array
	    array = StandardArray.VALUES;
	    break;
	case 2:
	    // point buy array
	    array = selectPointBuyChoices();
	    break;
	case 3:
	    // random array
	    array = getRandomArray();
	    break;
	default:
	    // should never get here
	    System.out.println("Should never get here, default to standard array");
	    array = StandardArray.VALUES;
	    break;
	}
	
	System.out.println("Your attribute array is: ");
	printArray(array);

	int[] attributes = new int[6];
	assignAttributes(attributes, array);

	printAttributes(attributes);
    }
}
