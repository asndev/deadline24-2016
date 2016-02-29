package de.antonstepan.hackathonstarter;

import com.google.common.collect.ImmutableList;

import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.List;

import de.antonstepan.helper.FileUtils;

public class Parser {

  public List<Test> parse(String filename) {

    ImmutableList<String> lines = FileUtils.readFile(filename);
    int T = parseInt(lines.get(0));
    List<Test> tests = new ArrayList<>();

    int startLine = 1;

    for (int i = 0; i < T; i++) {
      String[] line = lines.get(startLine)
          .split(" ");
      Test test = new Test(filename, parseInt(line[0]), parseInt(line[1]), parseInt(line[2]));

      int burningCount = parseInt(lines.get(startLine + 1));

      int j;
      for (j = 0; j < burningCount; j++) {
        String[] bey = lines.get(startLine + 2 + j)
            .split(" ");
        int b = parseInt(bey[0]);
        int e = parseInt(bey[1]);
        int y = parseInt(bey[2]);

        test.addFire(y - 1, e, b);
      }

      tests.add(test);
      startLine = startLine + 2 + j;
    }

    return tests;
  }
}
