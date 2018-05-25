package pl.bmstefanski.tools.impl.util.message;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageBuilderTest {

  @Test
  public void singleFieldTest() {
    String message = MessageBundle.create("var.: %TEST%").withField("TEST", "Variable").toString();

    assertEquals("var.: Variable", message);
  }

  @Test
  public void multipleFieldsTest() {
    String message = MessageBundle.create("0: %NULL%; 1: %ONE%; 2: %TWO%")
      .withField("NULL", "null")
      .withField("ONE", "first")
      .withField("TWO", "second")
      .toString();

    assertEquals("0: null; 1: first; 2: second", message);
  }

}
