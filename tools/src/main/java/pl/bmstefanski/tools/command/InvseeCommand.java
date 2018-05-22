package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
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

import javax.inject.Inject;

public class InvseeCommand implements Messageable, CommandExecutor {

  @Inject private Messages messages;

  @Command(name = "invsee", usage = "[player]", max = 1)
  @Permission("tools.command.invsee")
  @GameOnly
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    Player player = (Player) commandSender;

    if (commandArguments.getSize() == 0) {
      player.openInventory(player.getInventory());
      return;
    }

    if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
      sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", commandArguments.getParam(0)));
      return;
    }

    player.openInventory(Bukkit.getPlayer(commandArguments.getParam(0)).getInventory());

  }

}
