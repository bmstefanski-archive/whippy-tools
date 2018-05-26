package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.impl.util.ParsingUtil;

import javax.inject.Inject;

public class MarkCommand implements CommandExecutor {

  @Inject private UserManager userManager;

  @Command(name = "mark", usage = "[player]", max = 1)
  @Permission("tools.command.mark")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {

    if (commandArguments.getSize() == 0) {

      if (!(commandSender instanceof Player)) {
        MessageBundle.create(MessageType.ONLY_PLAYER).sendTo(commandSender);
        return;
      }

      Player player = (Player) commandSender;
      User user = this.userManager.getUser(player.getUniqueId()).get();

      boolean markState = !user.isMark();
      user.setMark(markState);

      MessageBundle.create(MessageType.MARKED)
        .withField("state", ParsingUtil.parseBoolean(markState))
        .sendTo(player);

      if (markState) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, false, false));
        return;
      }

      player.removePotionEffect(PotionEffectType.GLOWING);
      return;
    }

    if (commandSender.hasPermission("tools.command.mark.other")) {

      if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
        MessageBundle.create(MessageType.PLAYER_NOT_FOUND)
          .withField("player", commandArguments.getParam(0))
          .sendTo(commandSender);
        return;
      }

      Player target = Bukkit.getPlayer(commandArguments.getParam(0));
      User user = this.userManager.getUser(target.getUniqueId()).get();

      boolean markState = !user.isMark();
      user.setMark(markState);

      MessageBundle.create(MessageType.MARKED_OTHER)
        .withField("state", ParsingUtil.parseBoolean(markState))
        .withField("player", target.getName())
        .sendTo(commandSender);
      MessageBundle.create(MessageType.MARKED)
        .withField("state", ParsingUtil.parseBoolean(markState))
        .sendTo(target);

      if (markState) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, false, false));
        return;
      }

      target.removePotionEffect(PotionEffectType.GLOWING);
    }
  }

}
