package pl.bmstefanski.tools.impl.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MessageUtilTest {

  @Test
  public void replaceSingleStringTest() {
    String originalMessage = "§d";
    String changedMessage = MessageUtil.colored("&d");

    assertEquals(originalMessage, changedMessage);
  }

  @Test
  public void replaceStringListTest() {
    List<String> originalList = Arrays.asList("§1", "§2", "§3");
    List<String> replacedList = MessageUtil.colored(Arrays.asList("&1", "&2", "&3"));

    assertEquals(originalList, replacedList);
  }

}
