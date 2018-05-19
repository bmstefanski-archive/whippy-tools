package pl.bmstefanski.tools.impl.util.reflect;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;

public abstract class AbstractPacket {

    protected Object packet;

    public void send(Player player) {
        PacketSender.sendPacket(player, Collections.singletonList(packet));
    }

    public void send(Collection<? extends Player> players) {
        PacketSender.sendPacket(players, Collections.singletonList(packet));
    }

    public Object getPacket() {
        return packet;
    }

}
