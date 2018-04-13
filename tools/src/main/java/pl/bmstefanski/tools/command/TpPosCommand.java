package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import pl.bmstefanski.tools.util.Parser;

public class TpPosCommand implements Messageable, Parser, CommandExecutor {

    private final Tools plugin;
    private final Messages messages;

    public TpPosCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "tppos", usage = "[x] [y] [z] [player]", min = 3, max = 4)
    @Permission("tools.command.tppos")
    @GameOnly(false)
    @Override
    public void execute(CommandSender commandSender, CommandArguments commandArguments) {

        int x = parseInt(commandArguments.getParam(0));
        int y = parseInt(commandArguments.getParam(1));
        int z = parseInt(commandArguments.getParam(2));

        if (commandArguments.getSize() == 3) {

            if (!(commandSender instanceof Player)) {
                sendMessage(commandSender, messages.getOnlyPlayer());
                return;
            }

            Player player = (Player) commandSender;

            Location location = new Location(player.getWorld(), x, y, z);
            player.teleport(location);
            sendMessage(player, StringUtils.replaceEach(messages.getTppos(),
                    new String[] {"%player%" ,"%x%", "%y%", "%z%"},
                    new String[] {player.getName(), x + "", y + "", z + ""}));

            return;
        }

        if (Bukkit.getPlayer(commandArguments.getParam(3)) == null) {
            sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", commandArguments.getParam(3)));
            return;
        }

        Player player = Bukkit.getPlayer(commandArguments.getParam(3));
        Location location = new Location(player.getWorld(), x, y, z);

        player.teleport(location);
        sendMessage(player, StringUtils.replaceEach(messages.getTppos(),
                new String[] {"%player%" ,"%x%", "%y%", "%z%"},
                new String[] {player.getName(), x + "", y + "", z + ""}));

    }

}
