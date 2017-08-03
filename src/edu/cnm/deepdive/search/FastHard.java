/**
 * 
 */
package edu.cnm.deepdive.search;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zaryn
 *
 */
public class FastHard {
  
  private static String haystackName;
  private static String needlesName;
  private static String outputName;
  /**
   * @param args
   */
  public static void main(String[] args) {
    haystackName = args[0];
    needlesName = args[1];
    outputName = args[2];
    long startTime = System.currentTimeMillis();
    ArrayList<Integer> haystack = readFile(haystackName);
    ArrayList<Integer> needles = readFile(needlesName);
    System.out.printf("Read data: %,d ms%n", System.currentTimeMillis() - startTime);
    startTime = System.currentTimeMillis();
    quickSort(haystack);
    System.out.printf("Sort data: %,d ms%n", System.currentTimeMillis() - startTime);
    startTime = System.currentTimeMillis();
    List<Integer> found = getNeedlesFound(haystack, needles);
    System.out.printf("Search data: %,d ms%n", System.currentTimeMillis() - startTime);
    writeFile(outputName, found);
  }
  
  private static ArrayList<Integer> readFile(String filename) {
    try (      
        InputStream stream = new FileInputStream(filename);
        InputStreamReader input = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(input);
    ){
      String line;
      ArrayList<Integer> results = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.length() > 0) {
          results.add(Integer.parseInt(line));
        }
      }
      return results;
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  
  private static List<Integer> getNeedlesFound(ArrayList<Integer> haystack, List<Integer> needles) {
    ArrayList<Integer> results = new ArrayList<>();
    for (int needle : needles) {
      int position = binarySearch(haystack, needle);
      if (position >= 0) {
        results.add(needle);
      }
    }
    return results;
  }
  
  private static void writeFile(String filename, List<Integer> found) {
    try(
        OutputStream stream = new FileOutputStream(filename);
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        PrintWriter printer = new PrintWriter(writer);
    ) {
      for (int needle : found) {
        printer.println(needle);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  
  private static void quickSort(ArrayList<Integer> data) {
    Random rng = new Random();
    quickSort(data, 0, data.size(), rng);
  }
  
  private static void quickSort(ArrayList<Integer> data, 
                                int start, int end, Random rng) {
    if (end - start > 1) {
      int pivotPosition = start + rng.nextInt(end - start);
      int partitionPosition = start;
      Integer pivot = data.get(pivotPosition);
      data.set(pivotPosition, data.get(start));
      data.set(start, pivot);
      for (int i = start + 1; i < end; i++) {
        Integer test = data.get(i);
        if (pivot.compareTo(test) > 0) {
          Integer temp = data.get(++partitionPosition);
          data.set(partitionPosition, data.get(i));
          data.set(i, temp);
        }
      }
      data.set(start, data.get(partitionPosition));
      data.set(partitionPosition, pivot);
      quickSort(data, start, partitionPosition, rng);
      quickSort(data, partitionPosition + 1, end, rng);
    }
  }
  
  private static int binarySearch(ArrayList<Integer>data, Integer value) {
    return binarySearch(data, value, 0, data.size());
  }
  
  private static int binarySearch(ArrayList<Integer>data, Integer value,
                                  int start, int end) {
    int midpoint = (start + end) / 2;
    int comparison = data.get(midpoint).compareTo(value);
    if (comparison == 0) {
      return midpoint;
    } else if (comparison < 0) {
      return (end - midpoint > 1) 
              ? binarySearch(data, value, midpoint +1, end)
              : -(end + 1);
          
    } else {
      return (midpoint > start)
              ? binarySearch(data, value, start, midpoint)
              : -(midpoint + 1);
    }
  }
  
}
