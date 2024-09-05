package org.csgeeks;

/**
 * Point Buy
 *
 */
public class PointBuy 
{
    public final static int MIN = 8;
    public final static int MAX = 15;
    public final static int TOTAL = 27;

    public static class Attribute
    {
	int value = MIN;

	public Attribute() { }
	public Attribute(int value) {
	    setValue(value);
	}
	public void setValue(int value) {
	    if ((value >= MIN) && (value <= MAX)) {
		this.value = value;
	    }
	}
	public int getValue() {
	    return value;
	}
	public int getCost() {
	    // point cost formula
	    return (value-8) + (value>13 ? (value-13) : 0);
	};

	public String toString() {
	    return Integer.toString(value);
	}
    }

    // using ability score names for tracking, order does not matter for the point buy arrays
    public static String pointBuyArrayString(Attribute str_, Attribute dex_, Attribute con_, Attribute int_, Attribute wis_, Attribute cha_) {
	return String.format("[%2d, %2d, %2d, %2d, %2d, %2d]",
			     str_.getValue(), dex_.getValue(), con_.getValue(), int_.getValue(), wis_.getValue(), cha_.getValue());
    }

    // using ability score names for tracking, order does not matter for the point buy arrays
    public static void printArray(Attribute str_, Attribute dex_, Attribute con_, Attribute int_, Attribute wis_, Attribute cha_) {
	System.out.println(pointBuyArrayString(str_, dex_, con_, int_, wis_, cha_));
    }

    public static void bruteForceOne() {
	// using ability score names for tracking, order does not matter for the point buy arrays
	Attribute str_ = new Attribute();
	Attribute dex_ = new Attribute();
	Attribute con_ = new Attribute();
	Attribute int_ = new Attribute();
	Attribute wis_ = new Attribute();
	Attribute cha_ = new Attribute();
	
	for (int _str = MIN; _str <= MAX; _str++) {
	    str_.setValue(_str);
	    for (int _dex = MIN; _dex <= MAX; _dex++) {
		dex_.setValue(_dex);
		for (int _con = MIN; _con <= MAX; _con++) {
		    con_.setValue(_con);
		    for (int _int = MIN; _int <= MAX; _int++) {
			int_.setValue(_int);
			for (int _wis = MIN; _wis <= MAX; _wis++) {
			    wis_.setValue(_wis);
			    for (int _cha = MIN; _cha <= MAX; _cha++) {
				cha_.setValue(_cha);
				int point_buy_total = str_.getCost() + dex_.getCost() + con_.getCost() + int_.getCost() + wis_.getCost() + cha_.getCost();
				if (point_buy_total == TOTAL) {
				    printArray(str_, dex_, con_, int_, wis_, cha_);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    public static void bruteForceTwo() {
	// using ability score names for tracking, order does not matter for the point buy arrays
	Attribute str_ = new Attribute();
	Attribute dex_ = new Attribute();
	Attribute con_ = new Attribute();
	Attribute int_ = new Attribute();
	Attribute wis_ = new Attribute();
	Attribute cha_ = new Attribute();
	
	for (int _str = MAX; _str >= MIN; _str--) {
	    str_.setValue(_str);
	    for (int _dex = _str; _dex >= MIN; _dex--) {
		dex_.setValue(_dex);
		for (int _con = _dex; _con >= MIN; _con--) {
		    con_.setValue(_con);
		    for (int _int = _con; _int >= MIN; _int--) {
			int_.setValue(_int);
			for (int _wis = _int; _wis >= MIN; _wis--) {
			    wis_.setValue(_wis);
			    for (int _cha = _wis; _cha >= MIN; _cha--) {
				cha_.setValue(_cha);
				int point_buy_total = str_.getCost() + dex_.getCost() + con_.getCost() + int_.getCost() + wis_.getCost() + cha_.getCost();
				if (point_buy_total == TOTAL) {
				    printArray(str_, dex_, con_, int_, wis_, cha_);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    public static void bruteForceThree() {
	// using ability score names for tracking, order does not matter for the point buy arrays
	Attribute str_ = new Attribute();
	Attribute dex_ = new Attribute();
	Attribute con_ = new Attribute();
	Attribute int_ = new Attribute();
	Attribute wis_ = new Attribute();
	Attribute cha_ = new Attribute();
	
	for (int _str = MAX; _str >= MIN; _str--) {
	    str_.setValue(_str);
	    for (int _dex = _str; _dex >= MIN; _dex--) {
		dex_.setValue(_dex);
		for (int _con = _dex; _con >= MIN; _con--) {
		    con_.setValue(_con);
		    for (int _int = _con; _int >= MIN; _int--) {
			int_.setValue(_int);
			int point_buy_used_one = str_.getCost() + dex_.getCost() + con_.getCost() + int_.getCost();
			if (point_buy_used_one < 9) {
			    continue; // 2 stats remaining, max spend each is 9, so max spend is 18.  27 - 18 = 9, so we need to spend at least 9 points.
			}
			for (int _wis = _int; _wis >= MIN; _wis--) {
			    wis_.setValue(_wis);
			    int point_buy_used_two = str_.getCost() + dex_.getCost() + con_.getCost() + int_.getCost() + wis_.getCost();
			    if (point_buy_used_two < 18) {
				continue; // 1 stat remining, max spend is 9.  27 - 9 = 18, so we need to spend at least 18 points.
			    }
			    for (int _cha = _wis; _cha >= MIN; _cha--) {
				cha_.setValue(_cha);
				int point_buy_total = str_.getCost() + dex_.getCost() + con_.getCost() + int_.getCost() + wis_.getCost() + cha_.getCost();
				if (point_buy_total == TOTAL) {
				    printArray(str_, dex_, con_, int_, wis_, cha_);
				}
			    }
			}
		    }
		}
	    }
	}
    }


    public static void main( String[] args )
    {
        // System.out.println( "Point Buy Arrays" );
	// bruteForceOne();  // 249862 skips
	// bruteForceTwo();  // 1651 skips
	bruteForceThree();   // 1473 skips
    }
}
