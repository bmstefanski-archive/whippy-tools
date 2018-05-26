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

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.impl.util.ParsingUtil;

import javax.inject.Inject;

public class WhoisCommand implements CommandExecutor {

  @Inject private UserManager userManager;

  @Command(name = "whois", usage = "[player]", max = 1)
  @Permission("tools.command.whois")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (commandArguments.getSize() == 0) {

      if (!(commandSender instanceof Player)) {
        MessageBundle.create(MessageType.ONLY_PLAYER).sendTo(commandSender);
        return;
      }

      Player player = (Player) commandSender;

      MessageBundle.create(this.message(player)).sendTo(player);
      return;
    }

    if (commandSender.hasPermission("tools.command.whois.other")) {

      if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
        MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
          .withField("player", commandArguments.getParam(0))
          .sendTo(commandSender);
        return;
      }

      Player target = Bukkit.getPlayer(commandArguments.getParam(0));

      MessageBundle.create(this.message(target)).sendTo(commandSender);
    }
  }

  private String message(Player player) {
    User user = this.userManager.getUser(player.getUniqueId()).get();

    Location location = player.getLocation();
    String playerHealth = player.getHealth() + "/20";
    String playerFoodLevel = player.getFoodLevel() + "/20";
    String playerGamemode = player.getGameMode().toString().toLowerCase();
    String playerLocation = "("
      + player.getWorld().getName() + ", "
      + location.getBlockX() + ", "
      + location.getBlockY() + ", "
      + location.getBlockZ() + ")";
    String playerJoin = ParsingUtil.parseLong(player.getFirstPlayed());
    String playerLast = user.isOnline() ? "online" : ParsingUtil.parseLong(player.getLastPlayed());
    String whois = MessageBundle.create(MessageType.WHOIS).toString();

    return StringUtils.replaceEach(whois,
      new String[]{"%nickname%", "%uuid%", "%ip%", "%registered%", "%last%", "%location%", "%hp%", "%hunger%", "%gamemode%", "%god%", "%fly%"},
      new String[]{player.getName(), player.getUniqueId().toString(), player.getAddress().getAddress().toString(),
        playerJoin, playerLast, playerLocation, playerHealth, playerFoodLevel, playerGamemode, ParsingUtil.parseBoolean(user.isGod()),
        ParsingUtil.parseBoolean(player.isFlying())
      });
  }

}
