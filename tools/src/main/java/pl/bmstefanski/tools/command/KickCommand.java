package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;
import pl.bmstefanski.tools.storage.configuration.Messages;

import javax.inject.Inject;

import static pl.bmstefanski.tools.impl.util.MessageUtil.*;

public class KickCommand implements CommandExecutor {

  @Inject private Messages messages;

  @Command(name = "kick", usage = "[player] [reason]", min = 1, max = 16)
  @Permission("tools.command.kick")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
      MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
        .withField("player", commandArguments.getParam(0))
        .sendTo(commandSender);
      return;
    }

    Player target = Bukkit.getPlayer(commandArguments.getParam(0));

    if (commandSender.getName().equals(target.getName())) {
      MessageBundle.create(MessageType.CANNOT_KICK_YOURSELF).sendTo(commandSender);
      return;
    }

    String reason = "";

    if (commandArguments.getSize() == 1) {
      reason = colored(messages.getDefaultReason());
    } else if (commandArguments.getSize() > 1)
      reason = colored(StringUtils.join(commandArguments.getParams().toArray(), " ", 1, commandArguments.getArgs()));

    target.kickPlayer(reason);
  }

}
