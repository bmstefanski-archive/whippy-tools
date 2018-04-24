package pl.bmstefanski.tools.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.ToolsImpl;
import pl.bmstefanski.tools.storage.configuration.Messages;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ParsingUtilsTest {

    @Mock
    private Messages messages;

    @Before
    public void initializeMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void millisecondsToDateParserTest() {
        long milliseconds = 1524087540000L;
        String date = ParsingUtils.parseLong(milliseconds);

        assertEquals("2018-04-18 23:39:00", date);
    }

    @Test
    public void stringToIntegerParserTest() {
        String someInput = "1";
        int integer = ParsingUtils.parseInt(someInput);

        assertEquals(1, integer);
    }

    @Test(expected = NullPointerException.class) // i have no idea how to resolve this :<
    public void shouldReturnCorrectMessageWhenValueIsTrue() {
        String parsedValue = ParsingUtils.parseBoolean(true);

        assertEquals(this.messages.getBooleanOn(), parsedValue);
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnCorrectMessageWhenValueIsFalse() {
        String parsedValue = ParsingUtils.parseBoolean(false);

        assertEquals(this.messages.getBooleanOff(), parsedValue);
    }

}
