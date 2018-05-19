package pl.bmstefanski.tools.impl.util.title;

import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.tools.impl.util.reflect.AbstractPacket;
import pl.bmstefanski.tools.impl.util.reflect.transition.PacketPlayOutTitle;

import java.util.Collection;

public class TitleSenderImpl implements Messageable, TitleSender {

  @Override
  public void send(PacketPlayOutTitle.EnumTitleAction action, Player player, String message) {
    resetTitle();

    String json = "{\"text\":\"" + fixColor(message) + "\"}";
    AbstractPacket packet = new PacketPlayOutTitle(action, json);

    packet.send(player);
  }

  @Override
  public void send(PacketPlayOutTitle.EnumTitleAction action, Collection<? extends Player> players, String message) {
    resetTitle();

    String json = "{\"text\":\"" + fixColor(message) + "\"}";
    AbstractPacket packet = new PacketPlayOutTitle(action, json);

    packet.send(players);
  }

  @Override
  public void clearTitle() {
    new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR, null);
  }

  @Override
  public void resetTitle() {
    new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
  }

}
