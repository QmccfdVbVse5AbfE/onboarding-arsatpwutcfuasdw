package edu.brown.cs.student.main;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StarTest {
  @Test
  public void testfindDistance() {
    Star star1 = new Star(1, "test1", 0, 0, 0);
    Star star2 = new Star(2, "test2", 0, 0, 0);
    //zero distance
    double distance1  = star1.findDistance(star1, star2);
    assertEquals(distance1, 0, 0.01);

    Star star3 = new Star(3, "test2", 2, 2, 2);
    double distance2 = star3.findDistance(star3, star2);
    assertEquals(distance2, 3.46, 0.01);
  }
}
