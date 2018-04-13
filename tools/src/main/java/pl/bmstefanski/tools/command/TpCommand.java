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

public class TpCommand implements Messageable, CommandExecutor {

    private final Tools plugin;
    private final Messages messages;

    public TpCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "tp", usage = "[player] [target]", min = 1, max = 2)
    @Permission("tools.command.tp")
    @GameOnly(false)
    @Override
    public void execute(CommandSender commandSender, CommandArguments commandArguments) {

        if (commandArguments.getSize() == 1) {

            if (!(commandSender instanceof Player)) {
                sendMessage(commandSender, messages.getOnlyPlayer());
                return;
            }

            if (Bukkit.getPlayer(commandArguments.getParam(0)) == null) {
                sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", commandArguments.getParam(0)));
                return;
            }

            Player player = (Player) commandSender;
            Player target = Bukkit.getPlayer(commandArguments.getParam(0));

            player.teleport(target);
            sendMessage(commandSender, messages.getTeleportSuccess());

            return;
        }

        if (Bukkit.getPlayer(commandArguments.getParam(0)) == null || Bukkit.getPlayer(commandArguments.getParam(1)) == null) {
            sendMessage(commandSender, StringUtils.replaceEach(messages.getTpFailed(),
                    new String[] {"%player%", "%target%"},
                    new String[] {commandArguments.getParam(0), commandArguments.getParam(1)}));
            return;
        }

        Player player = Bukkit.getPlayer(commandArguments.getParam(0));
        Player target = Bukkit.getPlayer(commandArguments.getParam(1));

        player.teleport(target);
        sendMessage(commandSender, StringUtils.replaceEach(messages.getTpSuccess(),
                new String[] {"%player%", "%target%"},
                new String[] {player.getName(), target.getName()}));
    }
}