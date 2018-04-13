package pl.bmstefanski.tools.command;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class RepairCommand implements Messageable, CommandExecutor {

    private final Tools plugin;
    private final Messages messages;

    public RepairCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "repair")
    @Permission("tools.command.repair")
    @GameOnly
    @Override
    public void execute(CommandSender commandSender, CommandArguments commandArguments) {
        Player player = (Player) commandSender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.AIR) {
            sendMessage(player, messages.getCannotRepair());
            return;
        }

        if (item.getDurability() == item.getType().getMaxDurability()) {
            sendMessage(player, messages.getCannotRepairFull());
            return;
        }

        item.setDurability((short) 0);
        sendMessage(player, StringUtils.replace(messages.getRepaired(), "%item%", item.getType().name().toLowerCase()));

    }

}
