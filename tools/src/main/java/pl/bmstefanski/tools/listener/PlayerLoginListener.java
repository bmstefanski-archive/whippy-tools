package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class PlayerLoginListener implements Listener {

  @Inject private Messages messages;
  @Inject private UserManager userManager;
  @Inject private PluginConfig config;

  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event) {

    Player player = event.getPlayer();
    User user = this.userManager.getUser(player.getUniqueId());

    if (!player.hasPlayedBefore()) {
      user.setName(player.getName());
    }

    int maxNicknameLength = this.config.getMaxNicknameLength();

    if (player.getName().length() > maxNicknameLength) {
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringUtils.replace(this.messages.getTooLongNickname(), "%max%", maxNicknameLength + ""));
      return;
    }
  }

}
