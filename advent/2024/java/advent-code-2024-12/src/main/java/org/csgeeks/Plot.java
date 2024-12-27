package org.csgeeks;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Plot {
    public static final String UP_RIGHT   = "UpRight";
    public static final String UP_LEFT    = "UpLeft";
    public static final String DOWN_RIGHT = "DownRight";
    public static final String DOWN_LEFT  = "DownLeft";

    int area;
    Map<String, List<Point>> cornersMap;

    public Plot() {
	this.area = 0;
	cornersMap = new HashMap<String, List<Point>>();
    }

    public Plot(int area, Map<String, List<Point>> cornersMap) {
	this.area = area;
	this.cornersMap = cornersMap;
    }

    public void setArea(int area) {
	this.area = area;
    }

    public void setCornersMap(Map<String, List<Point>> cornersMap) {
	this.cornersMap = cornersMap;
    }

    public void putAllCornersMap(Map<String, List<Point>> cornersMap) {
	this.cornersMap.putAll(cornersMap);
    }

    public int getArea() {
	return area;
    }

    public Map<String, List<Point>> getCornersMap() {
	return cornersMap;
    }
}
