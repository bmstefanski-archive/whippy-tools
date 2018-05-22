package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class PlayerInteractListener implements Listener, Messageable {

  @Inject private Messages messages;
  @Inject private UserManager userManager;
  @Inject private PluginConfig config;

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {

    Player player = event.getPlayer();
    User user = this.userManager.getUser(player.getUniqueId());

    if (this.config.getCancelAfkOnInteract()) {
      if (user.isGod()) {
        user.setAfk(false);
        sendMessage(player, this.messages.getNoLongerAfk());
        Bukkit.getOnlinePlayers().forEach(p ->
          sendMessage(p, StringUtils.replace(this.messages.getNoLongerAfkGlobal(), "%player%", player.getName())));
      }
    }
  }

}
