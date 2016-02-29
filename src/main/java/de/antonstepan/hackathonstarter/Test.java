package de.antonstepan.hackathonstarter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.BitField;

public class Test {

  private final int N;
  private final int M;
  private final int K;
  private final String filename;
  private final Map<Integer, Map<Integer, Boolean>> matrix = new HashMap<>();

  public Test(String filename, int N, int M, int K) {
    this.N = N;
    this.M = M;
    this.K = K;
    this.filename = filename;
  }

  /**
   * @return the n
   */
  public int getN() {
    return N;
  }

  /**
   * @return the m
   */
  public int getM() {
    return M;
  }

  /**
   * @return the k
   */
  public int getK() {
    return K;
  }

  public void addFire(int y, int x, int b) {
    if (!matrix.containsKey(y)) {
      HashMap<Integer, Boolean> map = new HashMap<>();
      matrix.put(y, map);
    }
    
     Map<Integer, Boolean> map = matrix.get(y);

     int z = b - 1; 
     for(; z < x; ++z) {
       map.put(z, true);
     }
  }

  public boolean isFire(int y, int x) {
    boolean result = false;

    Map<Integer, Boolean> map = matrix.get(y);
    if (map != null) {
      if (map.containsKey(x)) {
        result = map.get(x);
      }
    }

    return result;
  }

  /**
   * @return the filename
   */
  public String getFilename() {
    return filename;
  }

  /**
   * @return the matrix
   */
  public Map<Integer, Map<Integer, Boolean>> getMatrix() {
    return matrix;
  }

}
