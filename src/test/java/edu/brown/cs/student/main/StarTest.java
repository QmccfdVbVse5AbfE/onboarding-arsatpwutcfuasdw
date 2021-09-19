package edu.brown.cs.student.main;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class StarTest {
  @Test
  public void testfindDistance() {
    Star star1 = new Star(1, "test1", 0, 0, 0);
    Star star2 = new Star(2, "test2", 0, 0, 0);
    //zero distance
    double distance1  = star1.findDistance(star1, star2);
    assertEquals(distance1, 0, 0.01);

    //some actual distance
    Star star3 = new Star(3, "test3", 2, 2, 2);
    double distance2 = star3.findDistance(star3, star2);
    assertEquals(distance2, 3.46, 0.01);

    //distance with negative position
    Star star4 = new Star(4, "test4", -2, -2, -2);
    double distance3 = star4.findDistance(star3, star4);
    assertEquals(distance3, 6.92, 0.01);
  }

  @Test
  public void testMaxArrayValue() {
    Star star1 = new Star(1, "test1", 0, 0, 0);
    ArrayList<Double> testArray1 = new ArrayList<>();
    testArray1.add(1.0);
    testArray1.add(9.0);
    testArray1.add(123.1);
    double max = star1.maxArrayValue(testArray1);
    assertEquals(max, 123.1, 0);
  }

  @Test
  public void testfindNearestStar3() {
    Star star1 = new Star(1, "test1", 0, 0, 0);
    Star star2 = new Star(2, "test2", 0, 0, 0);
    Star star3 = new Star(3, "test3", 2, 2, 2);
    Star star4 = new Star(4, "test4", -2, -2, -2);
    ArrayList<Star> starList = new ArrayList<>(4);
    starList.add(star1);
    starList.add(star2);
    starList.add(star3);
    starList.add(star4);
    ArrayList<Integer> expectedoutput = new ArrayList<>();
    expectedoutput.add(2);
    ArrayList<Integer> actual = star1.nearestStar3(star1, starList, 1);
    assertEquals(actual, expectedoutput);
  }
}
