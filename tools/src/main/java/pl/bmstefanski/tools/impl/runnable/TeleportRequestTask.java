/*
 MIT License

 Copyright (c) 2018 Whippy ToolsImpl

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

package pl.bmstefanski.tools.impl.runnable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

public class TeleportRequestTask implements Runnable, Messageable {

  private final Tools plugin;
  private final Messages messages;
  private final Location location;
  private final User user;

  private int delay;

  public TeleportRequestTask(Tools plugin, Messages messages, PluginConfig config, Location location, User user) {
    this.plugin = plugin;
    this.messages = messages;
    this.location = location;
    this.user = user;

    this.delay = config.getDelay();
  }

  @Override
  public void run() {

    Player player = this.user.getPlayer();
    Location lastLocation = this.user.getLastLocation();

    if (player.getLocation().distance(this.location) > 0.5) {
      this.user.getBukkitTask().cancel();
      this.user.setBukkitTask(null);

      sendMessage(player, this.messages.getTeleportCancelled());
      return;
    }

    if (delay == 0) {
      player.teleport(lastLocation);
      this.user.getBukkitTask().cancel();
      this.user.setBukkitTask(null);

      sendMessage(player, this.messages.getTeleportSuccess());
      return;
    }

    delay--;
  }
}
