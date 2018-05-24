package pl.bmstefanski.tools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class EntityPickupItemListener implements Listener {

  @Inject private UserManager userManager;
  @Inject private PluginConfig config;

  @EventHandler
  public void onPlayerPickup(EntityPickupItemEvent event) {

    Player player = (Player) event.getEntity();
    User user = this.userManager.getUser(player.getUniqueId()).get();

    if (event.getEntity() instanceof Player) {
      if (this.config.getDisableItemPickupWhileAfk()) {
        if (user.isAfk()) {
          event.setCancelled(true);
        }
      }
    }

  }

}
