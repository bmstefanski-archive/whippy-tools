package pl.bmstefanski.tools.command;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class AfkCommand implements Messageable, CommandExecutor {

  @Inject private UserManager userManager;
  @Inject private Messages messages;
  @Inject private PluginConfig config;

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

      sendMessage(player, messages.getNoLongerAfk());
      Bukkit.getOnlinePlayers().forEach(p ->
        sendMessage(p, StringUtils.replace(messages.getNoLongerAfkGlobal(), "%player%", player.getName())));

      return;
    }

    user.setAfk(true);

    if (this.config.getGodWhileAfk()) {
      user.setGod(true);
    }

    sendMessage(player, messages.getAfk());
    Bukkit.getOnlinePlayers().forEach(p ->
      sendMessage(p, StringUtils.replace(messages.getAfkGlobal(), "%player%", player.getName())));
  }

}
