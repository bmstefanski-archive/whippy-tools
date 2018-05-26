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

package pl.bmstefanski.tools.listener;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class PlayerMoveListener implements Listener {

  @Inject private Messages messages;
  @Inject private UserManager userManager;
  @Inject private PluginConfig config;
  @Inject private Server server;

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {

    Player player = event.getPlayer();
    User user = this.userManager.getUser(player.getUniqueId()).get();

    if (!this.config.getCancelAfkOnMove() && !this.config.getFreezeAfkPlayers()) {
      event.getHandlers().unregister(this);

      return;
    }

    if (user.isAfk()) {

      if (this.config.getFreezeAfkPlayers()) {
        event.setTo(event.getFrom());
        return;
      }

      if (this.config.getCancelAfkOnMove() && event.getFrom() == event.getTo()) {
        user.setAfk(false);
        MessageBundle.create(MessageType.NO_LONGER_AFK).sendTo(player);
        MessageBundle.create(MessageType.NO_LONGER_AFK_GLOBAL)
          .withField("player", player.getName())
          .sendTo(this.server.getOnlinePlayers());
      }
    }
  }

}
