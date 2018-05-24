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

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class PlayerJoinListener implements Listener, Messageable {

  @Inject private UserManager userManager;
  @Inject private PluginConfig config;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {

    Player player = event.getPlayer();
    User user = this.userManager.getUser(player.getUniqueId()).get();

    if (!user.getName().isPresent()) {
      user.setName(player.getName());
    }

    event.setJoinMessage(fixColor(StringUtils.replace(this.config.getJoinFormat(), "%player%", player.getName())));

    user.setLastLocation(player.getLocation());

    if (this.config.getFlyOnJoin()) {
      if (player.isFlying()) {
        player.setFlying(true);
        player.setAllowFlight(true);
      }
    }

    if (this.config.getSafeLogin()) {
      player.setFallDistance(0F);
    }
  }

}
