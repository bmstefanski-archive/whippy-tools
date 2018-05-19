package pl.bmstefanski.tools.impl.util.title;

import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.util.reflect.transition.PacketPlayOutTitle;

import java.util.Collection;

public interface TitleSender {

  void send(PacketPlayOutTitle.EnumTitleAction action, Player player, String message);

  void send(PacketPlayOutTitle.EnumTitleAction action, Collection<? extends Player> players, String message);

  void clearTitle();

  void resetTitle();

}
