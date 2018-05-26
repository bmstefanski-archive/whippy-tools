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
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;
import pl.bmstefanski.tools.impl.util.GamemodeUtil;

public class GamemodeCommand implements CommandExecutor {

  @Command(name = "gamemode", usage = "<0/1/2/3> [player]", min = 1, max = 2, aliases = {"gm"})
  @Permission("tools.command.gamemode")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (commandArguments.getSize() == 1) {

      if (!(commandSender instanceof Player)) {
        MessageBundle.create(MessageType.ONLY_PLAYER).sendTo(commandSender);
        return;
      }

      Player player = (Player) commandSender;
      GameMode gameMode = GamemodeUtil.parseGameMode(commandArguments.getParam(0));

      if (gameMode == null) {
        MessageBundle.create(MessageType.GAMEMODE_FAIL).sendTo(player);
        return;
      }

      if (player.hasPermission("tools.command.gamemode." + gameMode.toString().toLowerCase())) {
        player.setGameMode(gameMode);
        MessageBundle.create(MessageType.GAMEMODE_SUCCESS)
          .withField("gamemode", gameMode.toString())
          .sendTo(player);
        return;
      }

      MessageBundle.create(MessageType.NO_PERMISSIONS)
        .withField("permission", "tools.command.gamemode" + gameMode.toString().toLowerCase())
        .sendTo(commandSender);

      return;
    }

    if (commandSender.hasPermission("tools.command.gamemode.other")) {

      if (Bukkit.getPlayer(commandArguments.getParam(1)) == null) {
        MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
          .withField("player", commandArguments.getParam(1))
          .sendTo(commandSender);
        return;
      }

      Player target = Bukkit.getPlayer(commandArguments.getParam(1));
      GameMode gameMode = GamemodeUtil.parseGameMode(commandArguments.getParam(0));

      if (gameMode == null) {
        MessageBundle.create(MessageType.GAMEMODE_FAIL).sendTo(commandSender);
        return;
      }

      if (target.hasPermission("tools.command.gamemode." + gameMode.toString().toLowerCase() + ".other")) {
        target.setGameMode(gameMode);

        MessageBundle.create(MessageType.GAMEMODE_SUCCESS)
          .withField("gamemode", gameMode.toString())
          .sendTo(target);
        MessageBundle.create(MessageType.GAMEMODE_SUCCESS_OTHER)
          .withField("gamemode", gameMode.toString())
          .withField("player", target.getName())
          .sendTo(commandSender);
        return;
      }

      MessageBundle.create(MessageType.NO_PERMISSIONS)
        .withField("permission", "tools.command.gamemode" + gameMode.toString().toLowerCase() + "other")
        .sendTo(commandSender);
      return;
    }

    MessageBundle.create(MessageType.NO_PERMISSIONS)
      .withField("permission", "tools.command.gamemode.other")
      .sendTo(commandSender);
  }

}
