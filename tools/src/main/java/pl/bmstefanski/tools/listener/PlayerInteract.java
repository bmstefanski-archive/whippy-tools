package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class PlayerInteract implements Listener, Messageable {

    private final Tools plugin;
    private final Messages messages;

    public PlayerInteract(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        User user = UserManager.getUser(player.getUniqueId());

        if (plugin.getConfiguration().getCancelAfkOnInteract()) {

            if (user.isGod()) {
                user.setAfk(false);
                sendMessage(player, messages.getNoLongerAfk());
                Bukkit.getOnlinePlayers().forEach(p ->
                        sendMessage(p, StringUtils.replace(messages.getNoLongerAfkGlobal(), "%player%", player.getName())));
            }

        }

    }

}
