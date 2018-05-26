package pl.bmstefanski.tools.command;

import org.bukkit.Server;
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
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class AfkCommand implements CommandExecutor {

  @Inject private UserManager userManager;
  @Inject private Messages messages;
  @Inject private PluginConfig config;
  @Inject private Server server;

  @Command(name = "afk")
  @Permission("tools.command.afk")
  @GameOnly
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {
    Player player = (Player) commandSender;

    User user = this.userManager.getUser(player.getUniqueId()).get();

    if (user.isAfk()) {
      user.setAfk(false);
      user.setGod(false);

      MessageBundle.create(MessageType.NO_LONGER_AFK).sendTo(player);
      MessageBundle.create(MessageType.NO_LONGER_AFK_GLOBAL)
        .withField("player", player.getName())
        .sendTo(this.server.getOnlinePlayers());

      return;
    }

    user.setAfk(true);

    if (this.config.getGodWhileAfk()) {
      user.setGod(true);
    }

    MessageBundle.create(MessageType.AFK).sendTo(player);
    MessageBundle.create(MessageType.AFK_GLOBAL)
      .withField("player", player.getName())
      .sendTo(this.server.getOnlinePlayers());
  }

}
