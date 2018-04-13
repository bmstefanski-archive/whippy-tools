package pl.bmstefanski.tools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;

public class EntityPickupItem implements Listener {

    private final Tools plugin;

    public EntityPickupItem(Tools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPickup(EntityPickupItemEvent event) {

        Player player = (Player) event.getEntity();
        User user = UserManager.getUser(player.getUniqueId());

        if (event.getEntity() instanceof Player) {

            if (plugin.getConfiguration().getDisableItemPickupWhileAfk()) {

                if (user.isAfk()) {
                    event.setCancelled(true);
                }

            }

        }

    }

}
