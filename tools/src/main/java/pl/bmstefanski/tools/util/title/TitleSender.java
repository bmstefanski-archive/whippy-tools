package pl.bmstefanski.tools.util.title;

import org.bukkit.entity.Player;
import pl.bmstefanski.tools.util.reflect.transition.PacketPlayOutTitle;

import java.util.Collection;

public interface TitleSender {

    void send(PacketPlayOutTitle.EnumTitleAction action, Player player, String message);

    void send(PacketPlayOutTitle.EnumTitleAction action, Collection<? extends Player> players, String message);

    void clearTitle();

    void resetTitle();

}
