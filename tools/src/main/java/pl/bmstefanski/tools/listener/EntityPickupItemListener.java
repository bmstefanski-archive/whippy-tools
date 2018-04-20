package pl.bmstefanski.tools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;

public class EntityPickupItemListener implements Listener {

    private final Tools plugin;

    public EntityPickupItemListener(Tools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPickup(EntityPickupItemEvent event) {

        Player player = (Player) event.getEntity();
        User user = this.plugin.getUserManager().getUser(player.getUniqueId());

        if (event.getEntity() instanceof Player) {
            if (this.plugin.getConfiguration().getDisableItemPickupWhileAfk()) {
                if (user.isAfk()) {
                    event.setCancelled(true);
                }
            }
        }

    }

}
