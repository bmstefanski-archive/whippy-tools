package pl.bmstefanski.tools.command;

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

public class TpCommand implements CommandExecutor {

  @Command(name = "tp", usage = "[player] [target]", min = 1, max = 2)
  @Permission("tools.command.tp")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (commandArguments.getSize() == 1) {

      if (!(commandSender instanceof Player)) {
        MessageBundle.create(MessageType.ONLY_PLAYER).sendTo(commandSender);
        return;
      }

      if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
        MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
          .withField("player", commandArguments.getParam(0))
          .sendTo(commandSender);
        return;
      }

      Player player = (Player) commandSender;
      Player target = Bukkit.getPlayer(commandArguments.getParam(0));
      player.teleport(target);

      MessageBundle.create(MessageType.TELEPORT_SUCCESS).sendTo(commandSender);
      return;
    }

    if (Bukkit.getPlayer(commandArguments.getParam(0)) == null || Bukkit.getPlayer(commandArguments.getParam(1)) == null) {
      MessageBundle.create(MessageType.TP_FAILED)
        .withField("player", commandArguments.getParam(0))
        .withField("target", commandArguments.getParam(1))
        .sendTo(commandSender);
      return;
    }

    Player player = Bukkit.getPlayer(commandArguments.getParam(0));
    Player target = Bukkit.getPlayer(commandArguments.getParam(1));
    player.teleport(target);

    MessageBundle.create(MessageType.TP_SUCCESS)
      .withField("player", player.getName())
      .withField("target", target.getName())
      .sendTo(commandSender);
  }

}