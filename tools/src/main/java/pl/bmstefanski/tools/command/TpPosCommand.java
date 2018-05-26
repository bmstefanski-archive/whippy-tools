package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;
import pl.bmstefanski.tools.impl.util.ParsingUtil;

public class TpPosCommand implements CommandExecutor {

  @Command(name = "tppos", usage = "[x] [y] [z] [player]", min = 3, max = 4)
  @Permission("tools.command.tppos")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    int x = ParsingUtil.parseInt(commandArguments.getParam(0));
    int y = ParsingUtil.parseInt(commandArguments.getParam(1));
    int z = ParsingUtil.parseInt(commandArguments.getParam(2));

    if (commandArguments.getSize() == 3) {

      if (!(commandSender instanceof Player)) {
        MessageBundle.create(MessageType.ONLY_PLAYER).sendTo(commandSender);
        return;
      }

      Player player = (Player) commandSender;
      Location location = new Location(player.getWorld(), x, y, z);
      player.teleport(location);

      MessageBundle.create(MessageType.TPPOS)
        .withField("player", player.getName())
        .withField("x", x + "")
        .withField("y", y + "")
        .withField("z", z + "")
        .sendTo(player);
      return;
    }

    if (Bukkit.getPlayer(commandArguments.getParam(3)) == null) {
      MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
        .withField("player", commandArguments.getParam(3))
        .sendTo(commandSender);
      return;
    }

    Player player = Bukkit.getPlayer(commandArguments.getParam(3));
    Location location = new Location(player.getWorld(), x, y, z);
    player.teleport(location);

    MessageBundle.create(MessageType.TPPOS)
      .withField("player", player.getName())
      .withField("x", x + "")
      .withField("y", y + "")
      .withField("z", z + "")
      .sendTo(player);
  }

}
