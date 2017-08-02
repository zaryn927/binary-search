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

/**
 * @author zaryn
 *
 */
public class BruteForce {
  
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
    ArrayList<Integer> haystack = readFile(haystackName);
    ArrayList<Integer> needles = readFile(needlesName);
    List<Integer> found = getNeedlesFound(haystack, needles);
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
  
  private static List<Integer> getNeedlesFound(List<Integer> haystack, List<Integer> needles) {
    ArrayList<Integer> results = new ArrayList<>();
    for (int needle : needles) {
      for(int hay : haystack) {
        if (needle == hay) {
          results.add(needle);
          break;
        }
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
}
