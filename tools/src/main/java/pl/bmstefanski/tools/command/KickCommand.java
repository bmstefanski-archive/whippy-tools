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

public class KickCommand implements Messageable, CommandExecutor {

  @Inject private Messages messages;

  @Command(name = "kick", usage = "[player] [reason]", min = 1, max = 16)
  @Permission("tools.command.kick")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
      sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", commandArguments.getParam(0)));
      return;
    }

    Player target = Bukkit.getPlayer(commandArguments.getParam(0));

    if (commandSender.getName().equals(target.getName())) {
      sendMessage(commandSender, messages.getCannotKickYourself());
      return;
    }

    String reason = "";

    if (commandArguments.getSize() == 1) {
      reason = fixColor(messages.getDefaultReason());
    } else if (commandArguments.getSize() > 1)
      reason = fixColor(StringUtils.join(commandArguments.getParams().toArray(), " ", 1, commandArguments.getArgs()));

    target.kickPlayer(reason);
  }

}
