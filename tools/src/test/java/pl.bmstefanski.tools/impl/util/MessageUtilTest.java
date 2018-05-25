package pl.bmstefanski.tools.impl.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageUtilTest {

  @Test
  public void shouldReturnReplacedChar() {
    String originalMessage = "§";
    String changedMessage = MessageUtil.colored(originalMessage);

    assertEquals(originalMessage, changedMessage);
  }

}
