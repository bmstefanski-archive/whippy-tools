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

package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.tools.api.ToolsAPI;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class PlayerMoveListener implements Listener, Messageable {

    private final ToolsAPI plugin;
    private final Messages messages;

    public PlayerMoveListener(ToolsAPI plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        User user = UserManager.getUser(player.getUniqueId());

        if (!this.plugin.getConfiguration().getCancelAfkOnMove() && !this.plugin.getConfiguration().getFreezeAfkPlayers()) {
            event.getHandlers().unregister(this);

            return;
        }

        if (user.isAfk()) {

            if (this.plugin.getConfiguration().getFreezeAfkPlayers()) {
                event.setTo(event.getFrom());
                return;
            }

            if (this.plugin.getConfiguration().getCancelAfkOnMove() && event.getFrom() == event.getTo()) {
                user.setAfk(false);
                sendMessage(player, this.messages.getNoLongerAfk());
                Bukkit.getOnlinePlayers().forEach(p ->
                        sendMessage(p, StringUtils.replace(this.messages.getNoLongerAfkGlobal(), "%player%", player.getName())));
            }
        }
    }

}
