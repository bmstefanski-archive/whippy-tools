package pl.bmstefanski.tools.util;

import org.bukkit.GameMode;
import org.junit.Test;
import pl.bmstefanski.tools.impl.util.GamemodeUtils;

import static org.junit.Assert.*;

public class GameModeUtilsTest {

    @Test
    public void shouldReturnCorrectEnumFromNumberValue() {
        GameMode gameMode = GamemodeUtils.parseGameMode("1");
        GameMode newGameMode = GameMode.CREATIVE;

        assertEquals(gameMode, newGameMode);
    }

    @Test
    public void shouldReturnProperEnmmFromStringValue() {
        GameMode gameMode = GamemodeUtils.parseGameMode("survival");
        GameMode newGameMode = GameMode.SURVIVAL;

        assertEquals(gameMode, newGameMode);
    }

    @Test
    public void failWhenTheExpectedValueIsNotTheSame() {
        GameMode gameMode = GamemodeUtils.parseGameMode("adventure");
        GameMode newGameMode = GameMode.SPECTATOR;

        assertNotEquals(gameMode, newGameMode);
    }

    @Test
    public void failWhenTheValueDoesNotExist() {
        GameMode gameMode = GamemodeUtils.parseGameMode("extraMode");

        assertNull(gameMode);
    }

}
