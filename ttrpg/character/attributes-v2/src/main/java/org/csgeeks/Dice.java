package org.csgeeks;

// random number generator
import java.util.Random;

// random number generator
// import java.util.concurrent.ThreadLocalRandom;

/*
 * Dice - using the plural for better understanding
 *
 */
public class Dice
{
    public static final int DEFAULT_SIZE = 6;
    public static final Dice D4;
    public static final Dice D6;
    public static final Dice D8;
    public static final Dice D10;
    public static final Dice D12;
    public static final Dice D20;
    public static final Dice D100;

    static {
	D4   = new Dice(4);
	D6   = new Dice(6);
	D8   = new Dice(8);
	D10  = new Dice(10);
	D12  = new Dice(12);
	D20  = new Dice(20);
	D100 = new Dice(100);
    }
    
    int size;

    public Dice() {
	size = DEFAULT_SIZE;
    }

    public Dice(int size) {
	if (size > 1) {
	    this.size = size;
	} else {
	    this.size = DEFAULT_SIZE;
	}
    }

    public int getSize() {
	return size;
    }

    // no setSize() - create a new Dice with the desired size

    // default one roll
    public int roll() {
	Random rand = new Random();
	return rand.nextInt(size) + 1;  // random number between 1 and size
	// return ThreadLocalRandom.current().nextInt(1, 6 + 1);  // random number between 1 and 6
    }

    public int roll(int times) {
	int sum = 0;
	for (int i = 0; i < times; i++) {
	    sum += roll();
	}
	return sum;
    }
}
