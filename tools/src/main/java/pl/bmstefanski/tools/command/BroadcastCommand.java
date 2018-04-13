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

package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.reflect.TitleSender;
import pl.bmstefanski.tools.util.reflect.transition.PacketPlayOutTitle;

public class BroadcastCommand implements Messageable, CommandExecutor {

    private final Tools plugin;
    private final Messages messages;

    public BroadcastCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "broadcast", usage = "<action/title/subtitle/chat>", min = 2, max = 16, aliases = {"bc"})
    @Permission("tools.command.broadcast")
    @GameOnly(false)
    @Override
    public void execute(CommandSender commandSender, CommandArguments commandArguments) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i < commandArguments.getSize(); i++) {
            stringBuilder.append(" ");
            stringBuilder.append(commandArguments.getParam(i));
        }

        String message = stringBuilder.toString();
        TitleSender title = new TitleSender();

        switch (commandArguments.getParam(0)) {
            case "action":
                title.send(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, Bukkit.getOnlinePlayers(), message);
                break;

            case "title":
                title.send(PacketPlayOutTitle.EnumTitleAction.TITLE, Bukkit.getOnlinePlayers(), message);
                break;

            case "subtitle":
                title.send(PacketPlayOutTitle.EnumTitleAction.TITLE, Bukkit.getOnlinePlayers(), "");
                title.send(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, Bukkit.getOnlinePlayers(), message);
                break;

            case "chat":
                Bukkit.broadcastMessage(fixColor(StringUtils.replace(messages.getBroadcastFormat(), "%message%", stringBuilder.toString())));
                break;
        }
    }
}
