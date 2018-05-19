package pl.bmstefanski.tools.manager;

import org.bukkit.Location;
import pl.bmstefanski.tools.basic.User;

import java.util.Set;
import java.util.UUID;

public interface UserManager {

  User getUser(UUID uuid);

  User getUser(String userName);

  void addUser(User user);

  void invalidateUser(User user);

  Set<User> getOnlinePlayers();

  void teleportToLocation(User user, Location location);

}
