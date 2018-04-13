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
import pl.bmstefanski.tools.api.ToolsAPI;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.Parser;

public class MarkCommand implements CommandExecutor, Messageable, Parser {

    private final ToolsAPI plugin;
    private final Messages messages;

    public MarkCommand(ToolsAPI plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

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
            User user = UserManager.getUser(player.getUniqueId());

            boolean markState = !user.isMark();
            user.setMark(markState);

            sendMessage(player, StringUtils.replace(messages.getMarked(), "%state%", parseBoolean(markState)));

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
            User user = UserManager.getUser(target.getUniqueId());

            boolean markState = !user.isMark();
            user.setMark(markState);

            sendMessage(commandSender, StringUtils.replaceEach(messages.getMarkedOther(),
                    new String[] {"%state%", "%player%"},
                    new String[] {parseBoolean(markState), target.getName()}));

            sendMessage(target, StringUtils.replace(messages.getMarked(), "%state%", parseBoolean(markState)));

            if (markState) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, false, false));
                return;
            }

            target.removePotionEffect(PotionEffectType.GLOWING);
        }

    }

}
