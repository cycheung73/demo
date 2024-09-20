package org.csgeeks;

// input via Stream
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;

// input via Scanner
import java.util.Scanner;

// random number generator
import java.util.Random;

// random number generator
// import java.util.concurrent.ThreadLocalRandom;

// sorting
import java.util.Arrays;

/**
 * Attributes
 *
 */
public class Attributes 
{
    public static final int NUMBER_OF_CHOICES = 3;
    public static final int LENGTH = 6;

    public static final int MIN = 3;
    public static final int MAX = 18;

    int[] attributes;

    public Attributes() {
	attributes = new int[LENGTH];
    }

    public int[] getAttributes() {
	return attributes;
    }

    public void setAttributes(int[] attributes) {
	int length = Math.min(this.attributes.length, attributes.length);
	for (int i = 0; i < length; i++) {
	    this.attributes[i] = attributes[i];
	}
	// error condition
	for (int i = length; i < LENGTH; i++) {
	    this.attributes[i] = MIN;
	}
    }

    public int getNumberOfChoices()
    {
	return NUMBER_OF_CHOICES;
    }

    public String getChoiceString(int choice) {
	String choiceStr;
	switch (choice) {
	case 1:
	    choiceStr = "  1 - standard array ([15, 14, 13, 12, 10, 8])";
	    break;
	case 2:
	    choiceStr = "  2 - point buy (27 points total)";
	    break;
	case 3:
	    choiceStr = "  3 - random (4d6 drop lowest)";
	    break;
	default:
	    choiceStr = "Invalid choice, try again";
	    break;
	}
	return choiceStr;
    }

    public void printArray(int[] array)
    {
	printArray("", array);
    }

    public void printArray(String prefix, int[] array)
    {
	String arrayFormat = String.format("%s%d, %d, %d, %d, %d, %d",
					   prefix,
					   array[0], array[1], array[2],
					   array[3], array[4], array[5]);
	System.out.println(arrayFormat);
    }

    public int[] getRandomArray()
    {
	int[] array = new int[6];
	for (int i = 0; i < 6; i++) {
	    array[i] = fourRollsDropLowest();
	    // System.out.println("next");
	}
	return array;
    }

    // roll 4d6 drop the lowest
    public int fourRollsDropLowest()
    {
	int[] fourRolls = {Dice.D6.roll(), Dice.D6.roll(), Dice.D6.roll(), Dice.D6.roll()};
	Arrays.sort(fourRolls);
	// for (int i = 0; i < fourRolls.length; i++) {
	//     System.out.println(fourRolls[i]);
	// }
	return fourRolls[1] + fourRolls[2] + fourRolls[3];  // exclude fourRolls[0] (lowest)
    }

    // given an array of attribute values, assign them to specific attributes
    public void assignAttributes(int[] array)
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

    public void printAttributes()
    {
	System.out.println("STR: " + attributes[0]);
	System.out.println("DEX: " + attributes[1]);
	System.out.println("CON: " + attributes[2]);
	System.out.println("INT: " + attributes[3]);
	System.out.println("WIS: " + attributes[4]);
	System.out.println("CHA: " + attributes[5]);
    }

    public int[] getStandardArrayValues() {
	return StandardArray.VALUES;
    }

    // pick 1 out of the 64 point buy choices
    public int[] selectPointBuyChoices() {
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
}
