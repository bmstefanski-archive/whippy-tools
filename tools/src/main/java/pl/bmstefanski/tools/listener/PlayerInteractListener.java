package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class PlayerInteractListener implements Listener {

  @Inject private Messages messages;
  @Inject private UserManager userManager;
  @Inject private PluginConfig config;
  @Inject private Server server;

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {

    Player player = event.getPlayer();
    User user = this.userManager.getUser(player.getUniqueId()).get();

    if (this.config.getCancelAfkOnInteract()) {
      if (user.isGod()) {
        user.setAfk(false);
        MessageBundle.create(MessageType.NO_LONGER_AFK).sendTo(player);
        MessageBundle.create(MessageType.NO_LONGER_AFK_GLOBAL)
          .withField("player", player.getName())
          .sendTo(this.server.getOnlinePlayers());
      }
    }
  }

}
