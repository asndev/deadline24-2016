package de.antonstepan.hackathonstarter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class App {
  public static void main(String[] args) {
    String filename = "in/wildfire10.in";

    Parser parser = new Parser();
    System.out.println("Parsing file: " + filename);
    long startTime = System.nanoTime();
    List<Test> input = parser.parse(filename);
    long endTime = System.nanoTime();
    System.out.println("parsed in " + (endTime - startTime) / 1000000 + "ms");

    for (Test test : input) {
      Integer solve = solve(test);
      System.out.println(solve);
    }
    endTime = System.nanoTime();
    System.out.println("done in " + (endTime - startTime) / 1000000 + "ms");
  }

  private static Integer solve(Test test) {
    List<Integer[]> drops = new ArrayList<>();

    for (int m = 0; m < test.getM(); m++) {
      for (int n = 0; n < test.getN(); n++) {
        if (test.isFire(m, n)) {
          Integer[] cell = validateCell(test, m, n, test.getK());
          if (cell != null) {
            drops.add(cell);
          }
        }
      }

    }
    return computeScore(test.getN(), test.getM(), test.getK(), drops);
  }

  private static Integer computeScore(int n, int m, int K, List<Integer[]> drops) {
    boolean[][] matrix = new boolean[m][n];

    drops.parallelStream()
        .forEach(d -> {
          int col = d[0];
          int row = d[1];

          for (int k = 0; k < K; k++) {
            matrix[col - 1][row + k] = true;
            matrix[col][row + k] = true;
            matrix[col + 1][row + k] = true;
          }
        });

    int finalScore = 0;
    for (boolean[] bs : matrix) {
      for (boolean b : bs) {
        if (b)
          finalScore++;
      }
    }

    return finalScore;
  }

  private static Integer[] validateCell(Test test, int colIndex, int rowIndex, int K) {
    Integer[] result = new Integer[2];

    for (int k = 0; k < K; k++) {
      try {
        if (!(test.isFire(colIndex - 1, rowIndex + k) && test.isFire(colIndex, rowIndex + k)
            && test.isFire(colIndex + 1, rowIndex + k))) {
          result = null;
          break;
        }
      } catch (ArrayIndexOutOfBoundsException ex) {
        result = null;
        break;
      }
    }

    if (result != null) {
      result[0] = colIndex;
      result[1] = rowIndex;
    }

    return result;
  }

}

class Foobar {

  private final String filename;
  private final List<Integer> scores;

  public Foobar(String filename, List<Integer> scores) {
    this.filename = filename;
    this.scores = scores;
  }

  /**
   * @return the filename
   */
  public String getFilename() {
    return filename;
  }

  /**
   * @return the scores
   */
  public List<Integer> getScores() {
    return scores;
  }
}