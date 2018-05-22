package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

import javax.inject.Inject;

public class SkullCommand implements Messageable, CommandExecutor {

  @Inject private Messages messages;

  @Command(name = "skull", usage = "[player]", max = 1)
  @Permission("tools.command.skull")
  @GameOnly
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {
    Player player = (Player) commandSender;

    ItemStack skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();

    if (commandArguments.getSize() == 0) {
      skullMeta.setOwningPlayer(player);
      skullItem.setItemMeta(skullMeta);
      player.getInventory().addItem(skullItem);
      sendMessage(player, messages.getSkullOnly());
      return;
    }

    OfflinePlayer skullOwner = Bukkit.getOfflinePlayer(commandArguments.getParam(0));

    skullMeta.setOwningPlayer(skullOwner);
    skullItem.setItemMeta(skullMeta);
    player.getInventory().addItem(skullItem);

    sendMessage(player, StringUtils.replace(messages.getSkullSomeone(), "%player%", commandArguments.getParam(0)));
  }

}
