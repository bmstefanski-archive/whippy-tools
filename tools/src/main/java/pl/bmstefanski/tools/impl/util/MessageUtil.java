package pl.bmstefanski.tools.impl.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MessageUtil {

  public static String colored(String message) {
    return message != null ? ChatColor.translateAlternateColorCodes('&', message) : null;
  }

  public static List<String> colored(List<String> messages) {
    return messages.stream().map(String::toString).collect(Collectors.toCollection(ArrayList::new));
  }

  private MessageUtil() {
  }

}
