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

public class InvseeCommand implements CommandExecutor {

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
      MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
        .withField("player", commandArguments.getParam(0))
        .sendTo(player);
      return;
    }

    player.openInventory(Bukkit.getPlayer(commandArguments.getParam(0)).getInventory());

  }

}
