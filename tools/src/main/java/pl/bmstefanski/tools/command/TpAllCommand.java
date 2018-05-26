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

public class TpAllCommand implements CommandExecutor {

  @Command(name = "tpall", usage = "[player]", max = 1)
  @Permission("tools.command.tpaall")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (commandArguments.getSize() == 1) {

      if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
        MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
          .withField("player", commandArguments.getParam(0))
          .sendTo(commandSender);
        return;
      }

      Player target = Bukkit.getPlayer(commandArguments.getParam(0));
      Bukkit.getOnlinePlayers().forEach(player -> player.teleport(target));

      MessageBundle.create(MessageType.TELEPORT_SUCCESS).sendTo(target);
      return;
    }

    if (!(commandSender instanceof Player)) {
      MessageBundle.create(MessageType.ONLY_PLAYER).sendTo(commandSender);
      return;
    }

    Player target = (Player) commandSender;

    Bukkit.getOnlinePlayers().forEach(player -> player.teleport(target));
    MessageBundle.create(MessageType.TELEPORT_SUCCESS).sendTo(target);
  }

}
