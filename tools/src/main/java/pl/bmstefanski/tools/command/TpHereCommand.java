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

public class TpHereCommand implements Messageable, CommandExecutor {

    private final Tools plugin;
    private final Messages messages;

    public TpHereCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "tphere", usage = "[player]", min = 1, max = 1)
    @Permission("tools.command.tphere")
    @GameOnly
    @Override
    public void execute(CommandSender commandSender, CommandArguments commandArguments) {

        if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
            sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", commandArguments.getParam(0)));
            return;
        }

        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(commandArguments.getParam(0));

        target.teleport(player);
        sendMessage(player, StringUtils.replaceEach(messages.getTpSuccess(),
                new String[] {"%player%", "%target%"},
                new String[] {target.getName(), player.getName()}));

    }

}
