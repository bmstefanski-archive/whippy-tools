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
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;
import pl.bmstefanski.tools.storage.configuration.Messages;

import javax.inject.Inject;

public class TpHereCommand implements CommandExecutor {

  @Inject private Messages messages;

  @Command(name = "tphere", usage = "[player]", min = 1, max = 1)
  @Permission("tools.command.tphere")
  @GameOnly
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
      MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
        .withField("player", commandArguments.getParam(0))
        .sendTo(commandSender);
      return;
    }

    Player player = (Player) commandSender;
    Player target = Bukkit.getPlayer(commandArguments.getParam(0));
    target.teleport(player);

    MessageBundle.create(MessageType.TP_SUCCESS)
      .withField("player", player.getName())
      .withField("target", target.getName())
      .sendTo(player);
  }

}
