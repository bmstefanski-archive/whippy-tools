package pl.bmstefanski.tools.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.basic.UserImpl;
import pl.bmstefanski.tools.impl.event.UserAbortEvent;
import pl.bmstefanski.tools.impl.event.UserInitEvent;
import pl.bmstefanski.tools.manager.UserManager;

import javax.inject.Inject;
import java.util.Optional;

public class PlayerPreLoginListener implements Listener {

  @Inject private UserManager userManager;

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
    Optional<User> optionalUser = this.userManager.getUser(event.getUniqueId());

    optionalUser.ifPresent(user -> {
      UserInitEvent userInitEvent = new UserInitEvent(user);

      if (userInitEvent.isCancelled()) {
        UserAbortEvent userAbortEvent = new UserAbortEvent(user);
        Bukkit.getPluginManager().callEvent(userAbortEvent);
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Session aborted.");
        return;
      }

      this.userManager.addUser(user);
    });

    if (!optionalUser.isPresent()) {
      User user = new UserImpl(event.getUniqueId());
      this.userManager.addUser(user);
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§aAccount has been created! Log In Again"); // todo
    }
  }

}
