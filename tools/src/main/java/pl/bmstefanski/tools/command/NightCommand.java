package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;

public class NightCommand implements CommandExecutor {

  @Command(name = "night", usage = "[world]", max = 1)
  @Permission("tools.command.night")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (commandArguments.getSize() == 0) {

      if (!(commandSender instanceof Player)) {
        MessageBundle.create(MessageType.ONLY_PLAYER).sendTo(commandSender);
        return;
      }

      Player player = (Player) commandSender;
      player.getWorld().setTime(12566);

      MessageBundle.create(MessageType.NIGHT)
        .withField("world", player.getWorld().getName())
        .sendTo(player);
      return;
    }

    if (Bukkit.getWorld(commandArguments.getParam(0)) == null) {
      MessageBundle.create(MessageType.WORLD_NOT_FOUND)
        .withField("world", commandArguments.getParam(0))
        .sendTo(commandSender);
      return;
    }

    World world = Bukkit.getWorld(commandArguments.getParam(0));
    world.setTime(12566);

    MessageBundle.create(MessageType.NIGHT)
      .withField("world", world.getName())
      .sendTo(commandSender);
  }

}
