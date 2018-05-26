package pl.bmstefanski.tools.command;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;

public class RepairCommand implements CommandExecutor {

  @Command(name = "repair")
  @Permission("tools.command.repair")
  @GameOnly
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {
    Player player = (Player) commandSender;
    ItemStack item = player.getInventory().getItemInMainHand();

    if (item.getType() == Material.AIR) {
      MessageBundle.create(MessageType.CANNOT_REPAIR).sendTo(player);
      return;
    }

    if (item.getDurability() == item.getType().getMaxDurability()) {
      MessageBundle.create(MessageType.CANNOT_REPAIR_FULL).sendTo(player);
      return;
    }

    item.setDurability((short) 0);

    MessageBundle.create(MessageType.REPAIRED)
      .withField("item", item.getType().name().toLowerCase())
      .sendTo(player);
  }

}
