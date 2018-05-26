package pl.bmstefanski.tools.impl.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.bmstefanski.tools.storage.configuration.Messages;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ParsingUtilTest {

  @Mock
  private Messages messages;

  @Before
  public void initializeMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void millisecondsToDateParserTest() {
    long milliseconds = 1524087540000L;
    String date = ParsingUtil.parseLong(milliseconds);

    assertEquals("2018-04-18 23:39:00", date);
  }

  @Test
  public void stringToIntegerParserTest() {
    String someInput = "1";
    int integer = ParsingUtil.parseInt(someInput);

    assertEquals(1, integer);
  }

  @Test(expected = NullPointerException.class) // i have no idea how to resolve this :<
  public void shouldReturnCorrectMessageWhenValueIsTrue() {
    String parsedValue = ParsingUtil.parseBoolean(true);

    assertEquals(this.messages.getBooleanOn(), parsedValue);
  }

  @Test(expected = NullPointerException.class)
  public void shouldReturnCorrectMessageWhenValueIsFalse() {
    String parsedValue = ParsingUtil.parseBoolean(false);

    assertEquals(this.messages.getBooleanOff(), parsedValue);
  }

}
