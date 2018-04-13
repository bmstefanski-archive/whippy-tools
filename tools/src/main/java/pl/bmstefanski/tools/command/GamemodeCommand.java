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
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.GamemodeUtils;

public class GamemodeCommand implements Messageable, CommandExecutor {

    private final Tools plugin;
    private final Messages messages;

    public GamemodeCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "gamemode", usage = "<0/1/2/3> [player]", min = 1, max = 2, aliases = {"gm"})
    @Permission("tools.command.gamemode")
    @GameOnly(false)
    @Override
    public void execute(CommandSender commandSender, CommandArguments commandArguments) {

        if (commandArguments.getSize() == 1) {

            if (!(commandSender instanceof Player)) {
                sendMessage(commandSender, messages.getOnlyPlayer());
                return;
            }

            Player player = (Player) commandSender;
            GameMode gameMode = GamemodeUtils.parseGameMode(commandArguments.getParam(0));

            if (gameMode == null) {
                sendMessage(player, messages.getGamemodeFail());
                return;
            }

            if (player.hasPermission("tools.command.gamemode." + gameMode.toString().toLowerCase())) {
                player.setGameMode(gameMode);
                sendMessage(player, StringUtils.replace(messages.getGamemodeSuccess(), "%gamemode%", gameMode.toString()));
                return;
            }

            sendMessage(commandSender, StringUtils.replace(messages.getNoPermissions(),
                    "%permission%", "tools.command.gamemode." + gameMode.toString().toLowerCase()));

            return;
        }

        if (commandSender.hasPermission("tools.command.gamemode.other")) {

            if (Bukkit.getPlayer(commandArguments.getParam(1)) == null) {
                sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", commandArguments.getParam(1)));
                return;
            }

            Player target = Bukkit.getPlayer(commandArguments.getParam(1));
            GameMode gameMode = GamemodeUtils.parseGameMode(commandArguments.getParam(0));

            if (gameMode == null) {
                sendMessage(commandSender, messages.getGamemodeFail());
                return;
            }

            if (target.hasPermission("tools.command.gamemode." + gameMode.toString().toLowerCase() + ".other")) {
                target.setGameMode(gameMode);

                sendMessage(target, StringUtils.replace(messages.getGamemodeSuccess(), "%gamemode%", gameMode.toString()));
                sendMessage(commandSender, StringUtils.replaceEach(messages.getGamemodeSuccessOther(),
                        new String[] {"%gamemode%", "%player%"},
                        new String[] {gameMode.toString(), target.getName()}));
                return;
            }

            sendMessage(commandSender, StringUtils.replace(messages.getNoPermissions(),
                    "%permission%", "tools.command.gamemode." + gameMode.toString().toLowerCase() + ".other"));
            return;
        }

        sendMessage(commandSender, StringUtils.replace(messages.getNoPermissions(), "%permission%", "tools.command.gamemode.other"));
    }

}
