package pl.bmstefanski.tools.api.util;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface Packet {

    void send(Player player);

    void send(Collection<? extends Player> players);

    Object getPacket();

}
