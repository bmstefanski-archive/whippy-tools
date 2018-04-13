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

package pl.bmstefanski.tools.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.runnable.TeleportRequestTask;
import pl.bmstefanski.tools.storage.configuration.Messages;

import java.util.HashMap;
import java.util.Map;

public class TeleportManager implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public static final Map<Player, BukkitTask> TASK_MAP = new HashMap<>();

    public TeleportManager(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    public void teleport(Player player, Location location, int delay) {

        if (TASK_MAP.containsKey(player)) {
            sendMessage(player, messages.getCurrentlyTeleporting());
            return;
        }

        sendMessage(player, messages.getTeleport());

        Runnable runnable = new TeleportRequestTask(plugin, player, location, delay);
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, runnable, 0, 20);
        TASK_MAP.put(player, task);
    }
}
