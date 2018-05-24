package pl.bmstefanski.tools.manager;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.basic.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserManager {

  Optional<User> getUser(UUID uuid);

  Optional<User> getUser(String userName);

  Optional<User> getUser(Player player);

  void addUser(User user);

  void removeUser(User user);

  Set<User> getOnlinePlayers();

  void teleportToLocation(User user, Location location);

  ImmutableMap<String, User> getUserNameMap();

  ImmutableMap<UUID, User> getUserUniqueIdMap();

}
