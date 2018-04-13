package pl.bmstefanski.tools.util.reflect;

import org.bukkit.entity.Player;
import pl.bmstefanski.tools.api.util.Packet;

import java.util.Collection;
import java.util.Collections;

public abstract class AbstractPacket implements Packet {

    protected Object packet;

    @Override
    public void send(Player player) {
        PacketSender.sendPacket(player, Collections.singletonList(packet));
    }

    @Override
    public void send(Collection<? extends Player> players) {
        PacketSender.sendPacket(players, Collections.singletonList(packet));
    }

    @Override
    public Object getPacket() {
        return packet;
    }
}
