import ee.quirino.BloomFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BloomFilterTest {

  static BloomFilter filter;
  static List<String> wordlist = new ArrayList<>();
  private static final DecimalFormat df = new DecimalFormat("0.000000");

  @BeforeAll
  public static void init() {
    try {
      BufferedReader br =
          new BufferedReader(
              new FileReader(
                  BloomFilterTest.class.getClassLoader().getResource("wordlist.txt").getFile()));
      String line;
      while ((line = br.readLine()) != null) {
        filter = new BloomFilter();
        filter.input(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void verifyWordsAreInFilterTest() {
    assertEquals(false, filter.verify("11111"));
    assertEquals(true, filter.verify("ABA"));
    assertEquals(true, filter.verify("actinoids"));
    assertEquals(false, filter.verify("actinss"));
    assertEquals(true, filter.verify("ristra"));
    assertEquals(true, filter.verify("false"));
  }

  @Test
  void generatedStringTest() {
    int falsePositive = 0;
    int tries = 1000000;
    String generatedString;
    Random random = new Random();
    int leftLimit = 97;
    int rightLimit = 122;
    for(int i= 0; i<tries; i++){
      generatedString = random.ints(leftLimit, rightLimit + 1)
              .limit(5)
              .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
              .toString();
      if(filter.verify(generatedString) && !wordlist.contains(generatedString)){
        falsePositive++;
      }
    }
    double percentFalsePositive = 100* (double)falsePositive/tries;
    System.out.println("False positives: " + df.format(percentFalsePositive) + "%");
  }
}
