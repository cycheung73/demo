package org.csgeeks;

public class Point {
    long x;
    long y;
    public Point(long x, long y) {
	this.x = x;
	this.y = y;
    }
    public long getX() {
	return x;
    }
    public long getY() {
	return y;
    }
    public Point subtract(Point pt2) {
	return new Point(this.x - pt2.getX(), this.y - pt2.getY());
    }
    public Point add(Point pt2) {
	return new Point(this.x + pt2.getX(), this.y + pt2.getY());
    }
    public String toString() {
	return "(" + this.x + "," + this.y + ")";
    }
    @Override
    public boolean equals(Object o) {
	if (o == null)
	    return false;
	if (o == this)
	    return true;
	if (!(o instanceof Point))
	    return false;
	Point pt2 = (Point)o;
	return this.x == pt2.getX() && this.y == pt2.getY();
    }
    @Override
    public int hashCode() {
	int hash = 7;
	hash = 31 * hash + Long.hashCode(this.x);
	hash = 31 * hash + Long.hashCode(this.y);
	return hash ;
    }
}
