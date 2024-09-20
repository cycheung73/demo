package org.csgeeks;

// input via Stream
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;

// input via Scanner
import java.util.Scanner;

/**
 * Application
 *
 */
public class Application
{

    public static void main( String[] args )
    {
	Attributes attributes = new Attributes();

        System.out.println("Hello, World!  This is Attributes.");
	System.out.println("How would you like to generate your attributes?");

	// get input
	int choice = 0;
	int numberOfChoices = attributes.getNumberOfChoices();
	while ((choice < 1) || (choice > numberOfChoices)) {
	    for (int i = 1; i <= numberOfChoices; i++) {
		System.out.println(attributes.getChoiceString(i));
	    }
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
	    if ((choice < 1) || (choice > numberOfChoices)) {
		System.out.println("Invalid choice, try again");
	    }
	}

	System.out.println("You chose: '" + choice + "'");

	int[] array;

	switch (choice) {
	case 1:
	    // standard array
	    array = attributes.getStandardArrayValues();
	    break;
	case 2:
	    // point buy array
	    array = attributes.selectPointBuyChoices();
	    break;
	case 3:
	    // random array
	    array = attributes.getRandomArray();
	    break;
	default:
	    // should never get here
	    System.out.println("Should never get here, default to standard array");
	    array = attributes.getStandardArrayValues();
	    break;
	}
	
	System.out.println("Your attribute array is: ");
	attributes.printArray(array);

	attributes.assignAttributes(array);

	attributes.printAttributes();
    }
}
