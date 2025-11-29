package app;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

  @Test
  void main_withInvalidOption_printsInvalidOptionAndExits() {
    String input = "other\n";
    InputStream originalIn = System.in;
    PrintStream originalOut = System.out;

    ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    System.setIn(testIn);
    System.setOut(new PrintStream(testOut));

    try {
      Main.main(new String[0]);
    } finally {
      System.setIn(originalIn);
      System.setOut(originalOut);
    }

    String output = testOut.toString();
    assertTrue(output.contains("Invalid option"));
  }
}
