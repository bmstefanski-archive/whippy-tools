package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.impl.util.ParsingUtils;

import javax.inject.Inject;

public class MarkCommand implements CommandExecutor, Messageable {

  @Inject private Messages messages;
  @Inject private UserManager userManager;

  @Command(name = "mark", usage = "[player]", max = 1)
  @Permission("tools.command.mark")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (commandArguments.getSize() == 0) {

      if (!(commandSender instanceof Player)) {
        sendMessage(commandSender, messages.getOnlyPlayer());
        return;
      }

      Player player = (Player) commandSender;
      User user = this.userManager.getUser(player.getUniqueId()).get();

      boolean markState = !user.isMark();
      user.setMark(markState);

      sendMessage(player, StringUtils.replace(messages.getMarked(), "%state%", ParsingUtils.parseBoolean(markState)));

      if (markState) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, false, false));
        return;
      }

      player.removePotionEffect(PotionEffectType.GLOWING);
      return;
    }

    if (commandSender.hasPermission("tools.command.mark.other")) {

      if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
        sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", commandArguments.getParam(0)));
        return;
      }

      Player target = Bukkit.getPlayer(commandArguments.getParam(0));
      User user = this.userManager.getUser(target.getUniqueId()).get();

      boolean markState = !user.isMark();
      user.setMark(markState);

      sendMessage(commandSender, StringUtils.replaceEach(messages.getMarkedOther(),
        new String[]{"%state%", "%player%"},
        new String[]{ParsingUtils.parseBoolean(markState), target.getName()}));

      sendMessage(target, StringUtils.replace(messages.getMarked(), "%state%", ParsingUtils.parseBoolean(markState)));

      if (markState) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, false, false));
        return;
      }

      target.removePotionEffect(PotionEffectType.GLOWING);
    }
  }

}
