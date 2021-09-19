package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CsvReader {
  public ArrayList<Star> starData = null;

  public CsvReader(ArrayList<Star> starData) {
    this.starData = starData;
  }

  public void readCSV(String fileName) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String row = br.readLine();
      if (row != null) {
        String[] headers = row.split(",");
        if (headers[0].equals("StarID") && headers[1].equals("ProperName") &&
            headers[2].equals("X") && headers[3].equals("Y") && headers[4].equals("Z")) {
          row = br.readLine();
          while (row != null) {
            String[] arguments2 = row.split(",");
            Star newStar = new Star(Integer.parseInt(arguments2[0]), arguments2[1],
                Double.parseDouble(arguments2[2]), Double.parseDouble(arguments2[3]),
                Double.parseDouble(arguments2[4]));
            starData.add(newStar);
            row = br.readLine();
          }
          int size = starData.size();
          String printMessage = "Read ".concat(String.valueOf(size)).concat(" stars from ").concat(fileName);
          System.out.println(printMessage);
        } else {
          System.out.println("Invalid header for star Data");
        }
      } else {
        System.out.println("Empty CSV");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}




