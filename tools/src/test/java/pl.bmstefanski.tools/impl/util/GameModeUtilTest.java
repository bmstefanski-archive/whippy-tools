package pl.bmstefanski.tools.impl.util;

import org.bukkit.GameMode;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameModeUtilTest {

  @Test
  public void shouldReturnCorrectEnumFromNumberValue() {
    GameMode gameMode = GamemodeUtil.parseGameMode("1");
    GameMode newGameMode = GameMode.CREATIVE;

    assertEquals(gameMode, newGameMode);
  }

  @Test
  public void shouldReturnProperEnumFromStringValue() {
    GameMode gameMode = GamemodeUtil.parseGameMode("survival");
    GameMode newGameMode = GameMode.SURVIVAL;

    assertEquals(gameMode, newGameMode);
  }

  @Test
  public void failWhenTheExpectedValueIsNotTheSame() {
    GameMode gameMode = GamemodeUtil.parseGameMode("adventure");
    GameMode newGameMode = GameMode.SPECTATOR;

    assertNotEquals(gameMode, newGameMode);
  }

  @Test
  public void failWhenTheValueDoesNotExist() {
    GameMode gameMode = GamemodeUtil.parseGameMode("extraMode");

    assertNull(gameMode);
  }

}
