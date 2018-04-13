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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.Ban;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.BanManager;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class PlayerPreLogin implements Listener, Messageable {

    private final Tools plugin;
    private final Messages messages;

    public PlayerPreLogin(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

        User user = UserManager.getUser(event.getUniqueId());
        Ban ban = BanManager.getBan(user.getUUID());

        if (ban == null) {
            return;
        }

        if (!user.isBanned()) {
            plugin.getBanResource().remove(ban);
            return;
        }

        String banFormat = listToString(messages.getBanFormat());
        String untilFormat = fixColor(messages.getPermanentBan());

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, StringUtils.replaceEach(banFormat,
                new String[]{"%punisher%", "%until%", "%reason%"},
                new String[]{ban.getPunisher(), ban.getTime() <= 0 ? untilFormat : ban.getTime() + "", ban.getReason()}));
    }
}
