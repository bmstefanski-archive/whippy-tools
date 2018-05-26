package pl.bmstefanski.tools.impl.type;

import org.apache.commons.lang3.StringUtils;

import static pl.bmstefanski.tools.impl.ToolsImpl.getInstance;

public enum MessageType {

  ON(getInstance().getMessages().getBooleanOn()),
  OFF(getInstance().getMessages().getBooleanOff()),
  NO_PERMISSIONS(getInstance().getMessages().getNoPermissions()),
  ONLY_PLAYER(getInstance().getMessages().getOnlyPlayer()),
  UNKNOWN_COMMAND(getInstance().getMessages().getUnknownCommand()),
  LIST_FULL(getInstance().getMessages().getListFull()),
  LIST_BASIC(getInstance().getMessages().getListBasic()),
  PLAYER_NOT_FOUND(getInstance().getMessages().getPlayerNotFound()),
  GAMEMODE_SUCCESS(getInstance().getMessages().getGamemodeSuccess()),
  GAMEMODE_SUCCESS_OTHER(getInstance().getMessages().getGamemodeSuccessOther()),
  SUCCESSFULLY_RELOADED(getInstance().getMessages().getSuccessfullyReloaded()),
  SUCCESSFULLY_DISABLED(getInstance().getMessages().getSuccessfullyDisabled()),
  HEALED(getInstance().getMessages().getHealed()),
  HEALED_OTHER(getInstance().getMessages().getHealedOther()),
  FED(getInstance().getMessages().getFed()),
  FED_OTHER(getInstance().getMessages().getFedOther()),
  FLY_SWITCHED(getInstance().getMessages().getFlySwitched()),
  FLY_SWITCHED_OTHER(getInstance().getMessages().getFlySwitchedOther()),
  SETSPAWN_SUCCES(getInstance().getMessages().getSetspawnSuccess()),
  SPAWN_FAILED(getInstance().getMessages().getSpawnFailed()),
  TELEPORT_CANCELLED(getInstance().getMessages().getTeleportCancelled()),
  CURRENTLY_TELEPORTING(getInstance().getMessages().getCurrentlyTeleporting()),
  GOD_SWITCHED(getInstance().getMessages().getGodSwitched()),
  GOD_SWITCHED_OTHER(getInstance().getMessages().getGodSwitchedOther()),
  TELEPORT_SUCCESS(getInstance().getMessages().getTeleportSuccess()),
  BROADCAST_FORMAT(getInstance().getMessages().getBroadcastFormat()),
  CLEAR(getInstance().getMessages().getClear()),
  CLEAR_OTHER(getInstance().getMessages().getClearOther()),
  WHOIS(StringUtils.join(getInstance().getMessages().getWhois(), "\n")),
  GAMEMODE_FAIL(getInstance().getMessages().getGamemodeFail()),
  DEFAULT_REASON(getInstance().getMessages().getDefaultReason()),
  TELEPORT(getInstance().getMessages().getTeleport()),
  NO_LONGER_AFK(getInstance().getMessages().getNoLongerAfk()),
  NO_LONGER_AFK_GLOBAL(getInstance().getMessages().getNoLongerAfkGlobal()),
  AFK(getInstance().getMessages().getAfk()),
  AFK_GLOBAL(getInstance().getMessages().getAfkGlobal()),
  HAT_CANT_BE_AIR(getInstance().getMessages().getHatCantBeAir()),
  HAT(getInstance().getMessages().getHat()),
  SKULL_ONLY(getInstance().getMessages().getSkullOnly()),
  SKULL_SOMEONE(getInstance().getMessages().getSkullSomeone()),
  TP_SUCCESS(getInstance().getMessages().getTpSuccess()),
  TP_FAILED(getInstance().getMessages().getTpFailed()),
  TPPOS(getInstance().getMessages().getTppos()),
  DAY(getInstance().getMessages().getDay()),
  WORLD_NOT_FOUND(getInstance().getMessages().getWorldNotFound()),
  NIGHT(getInstance().getMessages().getNight()),
  CANNOT_REPAIR(getInstance().getMessages().getCannotRepair()),
  CANNOT_REPAIR_FULL(getInstance().getMessages().getCannotRepairFull()),
  REPAIRED(getInstance().getMessages().getRepaired()),
  CANNOT_KICK_YOURSELF(getInstance().getMessages().getCannotKickYourself()),
  TOO_LONG_NICKNAME(getInstance().getMessages().getTooLongNickname()),
  STRUCK(getInstance().getMessages().getStruck()),
  STRUCK_OTHER(getInstance().getMessages().getStruckOther()),
  SET_NICKNAME(getInstance().getMessages().getSetNickname()),
  SET_NICKNAME_OTHER(getInstance().getMessages().getSetNicknameOther()),
  REALNAME(getInstance().getMessages().getRealname()),
  REALNAME_OTHER(getInstance().getMessages().getRealnameOther()),
  MARKED(getInstance().getMessages().getMarked()),
  MARKED_OTHER(getInstance().getMessages().getMarkedOther());

  private final String message;

  MessageType(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}
