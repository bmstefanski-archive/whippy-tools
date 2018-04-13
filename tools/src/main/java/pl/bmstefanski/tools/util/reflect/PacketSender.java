/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package pl.bmstefanski.tools.util.reflect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

 /*
    Author: Dzikoysk
    Source: https://github.com/FunnyGuilds/FunnyGuilds/blob/master/src/main/java/net/dzikoysk/funnyguilds/util/reflect/PacketSender.java
 */

public class PacketSender {

    private static Method getHandle;
    private static Field playerConnection;
    private static Method sendPacket;

    static {
        getHandle = Reflections.getMethod(Reflections.getBukkitClass("entity.CraftPlayer"), "getHandle");
        sendPacket = Reflections.getMethod(Reflections.getCraftClass("PlayerConnection"), "sendPacket");
        playerConnection = Reflections.getField(Reflections.getCraftClass("EntityPlayer"), "playerConnection");
    }

    public static void sendPacket(Collection<? extends Player> players, List<Object> packets) {
        Bukkit.getOnlinePlayers().forEach(player -> sendPacket(player, packets));
    }

    public static void sendPacket(Player target, List<Object> packets) {
        if (target == null) {
            return;
        }

        try {
            Object handle = getHandle.invoke(target);
            Object connection = playerConnection.get(handle);

            for (Object packet : packets) {
                sendPacket.invoke(connection, packet);
            }
        } catch (IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    private PacketSender() {}

}
