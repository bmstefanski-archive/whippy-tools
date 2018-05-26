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

package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;

import static pl.bmstefanski.tools.impl.util.MessageUtil.*;

public class NicknameCommand implements CommandExecutor {

  @Command(name = "nick", min = 1, max = 2, usage = "[player] [nickname]", aliases = {"setnick", "nickname"})
  @Permission("tools.command.nick")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (commandArguments.getSize() == 1) {

      if (!(commandSender instanceof Player)) {
        MessageBundle.create(MessageType.ONLY_PLAYER).sendTo(commandSender);
        return;
      }

      Player player = (Player) commandSender;
      String nickname = colored(commandArguments.getParam(0) + ChatColor.RESET);

      player.setDisplayName(nickname);
      player.setPlayerListName(nickname);

      MessageBundle.create(MessageType.SET_NICKNAME)
        .withField("nickname", commandArguments.getParam(0))
        .sendTo(player);

      return;
    }

    if (commandSender.hasPermission("tools.command.nick.other")) {

      if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
        MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
          .withField("player", commandArguments.getParam(0))
          .sendTo(commandSender);
        return;
      }

      Player target = Bukkit.getPlayer(commandArguments.getParam(0));
      String nickname = colored(commandArguments.getParam(1) + ChatColor.RESET);

      target.setDisplayName(nickname);
      target.setPlayerListName(nickname);

      MessageBundle.create(MessageType.SET_NICKNAME)
        .withField("nickname", commandArguments.getParam(1))
        .sendTo(target);
      MessageBundle.create(MessageType.SET_NICKNAME_OTHER)
        .withField("player", target.getName())
        .withField("nickname", commandArguments.getParam(1))
        .sendTo(commandSender);
    }
  }

}
