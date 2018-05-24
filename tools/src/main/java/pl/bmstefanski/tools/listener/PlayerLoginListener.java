package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.event.UserAbortEvent;
import pl.bmstefanski.tools.impl.event.UserLoadEvent;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class PlayerLoginListener implements Listener {

  @Inject private Messages messages;
  @Inject private UserManager userManager;
  @Inject private PluginConfig config;

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerLogin(PlayerLoginEvent event) {

    System.out.println(this.userManager);

    Player player = event.getPlayer();
    User user = this.userManager.getUser(player.getUniqueId()).get();

    UserLoadEvent userLoadEvent = new UserLoadEvent(user);

    if (userLoadEvent.isCancelled()) {
      UserAbortEvent userAbortEvent = new UserAbortEvent(user);
      Bukkit.getPluginManager().callEvent(userAbortEvent);
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Session aborted.");
      return;
    }

    int maxNicknameLength = this.config.getMaxNicknameLength();

    if (player.getName().length() > maxNicknameLength) {
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringUtils.replace(this.messages.getTooLongNickname(), "%max%", maxNicknameLength + ""));
      return;
    }

    Bukkit.getPluginManager().callEvent(userLoadEvent);
  }

}
