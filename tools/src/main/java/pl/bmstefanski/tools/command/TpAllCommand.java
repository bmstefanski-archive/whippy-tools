package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class TpAllCommand implements Messageable, CommandExecutor {

    private final Tools plugin;
    private final Messages messages;

    public TpAllCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "tpall", usage = "[player]", max = 1)
    @Permission("tools.command.tpaall")
    @GameOnly(false)
    @Override
    public void execute(CommandSender commandSender, CommandArguments commandArguments) {

        if (commandArguments.getSize() == 1) {

            if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
                sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", commandArguments.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(commandArguments.getParam(0));

            Bukkit.getOnlinePlayers().forEach(player -> player.teleport(target));
            sendMessage(target, messages.getTeleportSuccess());

            return;
        }

        if (!(commandSender instanceof Player)) {
            sendMessage(commandSender, messages.getOnlyPlayer());
            return;
        }

        Player target = (Player) commandSender;

        Bukkit.getOnlinePlayers().forEach(player -> player.teleport(target));
        sendMessage(target, messages.getTeleportSuccess());
    }
}
