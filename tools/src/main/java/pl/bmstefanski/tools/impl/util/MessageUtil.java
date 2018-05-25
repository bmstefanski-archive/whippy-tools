package pl.bmstefanski.tools.impl.util;

import org.bukkit.ChatColor;

public final class MessageUtil {

  public static String colored(String message) {
    return message != null ? ChatColor.translateAlternateColorCodes('&', message) : null;
  }

  private MessageUtil() {
  }

}
