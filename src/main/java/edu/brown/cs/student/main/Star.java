package edu.brown.cs.student.main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Star {
  public int starID;
  public String properName;
  public double x;
  public double y;
  public double z;

  public Star(int starID, String properName, double x, double y, double z) {
    this.starID = starID;
    this.properName = properName;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double findDistance(Star star1, Star star2) {
    double xdis = Math.pow(star1.x - star2.x, 2);
    double ydis = Math.pow(star1.y - star2.y, 2);
    double zdis = Math.pow(star1.z - star2.z, 2);
    return Math.sqrt(xdis + ydis + zdis);
  }

//I tried a couple of implementations of finding the nearest star, however, none of them are curerntly working.
  public ArrayList<Integer> nearestStar(Star star, ArrayList<Star> data, int k) {
    ArrayList<Integer> nearStars = new ArrayList<Integer>();
    HashMap<Star, Double> distanceMap = new HashMap<Star, Double>();

    for (Star n : data) {
      distanceMap.put(n, findDistance(star, n));
    }
    int i = 0;
    double minDistance = Double.POSITIVE_INFINITY;
    while (i < k) {
      for (Star astar : distanceMap.keySet()) {
        Double dis = distanceMap.get(astar);
        if (dis < minDistance) {
          minDistance = dis;
          distanceMap.remove(astar);
          nearStars.add(astar.starID);
        }
      }
    i = i + 1;
    }
    return nearStars;
  }

  public ArrayList<Integer> nearestStar2(Star star, ArrayList<Star> data, int k) {
    ArrayList<Integer> nearStars = new ArrayList<Integer>();
    HashMap<Star, Double> distanceMap = new HashMap<Star, Double>();

    for (Star n : data) {
      distanceMap.put(n, findDistance(star, n));
    }
    int i = 0;
    double minDistance = Double.POSITIVE_INFINITY;
    Star addStar = new Star(5432454, "dummy", 0, 0, 0);
    while (i < k) {
      for (Star astar : distanceMap.keySet()) {
        Double dis = distanceMap.get(astar);
        if (dis < minDistance) {
          minDistance = dis;
          distanceMap.remove(astar);
          addStar = astar;
        }
      }
      nearStars.add(addStar.starID);
      i = i +1;
    }
    return nearStars;
  }

  public Double maxArrayValue(ArrayList<Double> array) {
    Double maxValue = array.get(0);
    for (Double aDouble : array) {
      if (aDouble > maxValue) {
        maxValue = aDouble;
      }
    }
    return maxValue;
  }

  //throws an indexoutofbounds excception but unsure why
  public ArrayList<Integer> nearestStar3(Star star, ArrayList<Star> data, int k) {
    ArrayList<Integer> nearStars = new ArrayList<Integer>(k);
    ArrayList<Double> nearestDistance = new ArrayList<Double>(k);
    HashMap<Integer, Double> distanceMap = new HashMap<Integer, Double>();
    //put the distance and star id into a hashmap
    for (Star n : data) {
      distanceMap.put(n.starID, findDistance(star, n));
    }

    //fills the nearStars array with 0's and the nearestDistance with infinity
    int x = 0;
    while (x < k) {
      nearestDistance.set(x, Double.POSITIVE_INFINITY);
      nearStars.set(x, 0);
      x = x + 1;
    }
    //store the value of the maximum distance of the nearestDistance array
    Double maxDistance = star.maxArrayValue(nearestDistance);

    for (int i : distanceMap.keySet()) {
      if (star.starID != i) {
        if (distanceMap.get(i) < maxDistance) {
          int index = nearestDistance.indexOf(maxDistance);
          nearStars.set(index, i);
          nearestDistance.set(index, distanceMap.get(i));
        }
        maxDistance = star.maxArrayValue(nearestDistance);
      }
    }
    return nearStars;
  }
}
