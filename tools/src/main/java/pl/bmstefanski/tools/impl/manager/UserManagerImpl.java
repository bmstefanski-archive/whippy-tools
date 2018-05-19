package pl.bmstefanski.tools.impl.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.basic.UserImpl;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.impl.runnable.TeleportRequestTask;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class UserManagerImpl implements UserManager {

  private final Tools plugin;

  private final Map<UUID, User> uuidUserMap = new HashMap<>();
  private final Map<String, User> nameUserMap = new HashMap<>();

  private final Cache<UUID, User> uuidUserCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
  private final Cache<String, User> nameUserCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();


  public UserManagerImpl(Tools plugin) {
    this.plugin = plugin;
  }

  @Override
  public User getUser(UUID uuid) {
    Validate.notNull(uuid);

    User user = uuidUserCache.getIfPresent(uuid);

    if (user == null) {
      user = uuidUserMap.get(uuid);

      if (user != null) {
        uuidUserMap.put(uuid, user);
        return user;
      }
      return new UserImpl(uuid);
    }

    return user;
  }

  @Override
  public User getUser(String playerName) {
    Validate.notNull(playerName);

    User user = nameUserCache.getIfPresent(playerName);

    if (user == null) {
      user = nameUserMap.get(playerName);

      if (user != null) {
        nameUserMap.put(playerName, user);
        return user;
      }
      return new UserImpl(playerName);
    }

    return user;
  }

  @Override
  public void addUser(User user) {
    Validate.notNull(user);

    uuidUserCache.put(user.getUUID(), user);
    uuidUserMap.put(user.getUUID(), user);

    if (user.getName() != null) {
      nameUserCache.put(user.getName(), user);
      nameUserMap.put(user.getName(), user);
    }
  }

  @Override
  public void invalidateUser(User user) {
    Validate.notNull(user);

    uuidUserCache.invalidate(user.getUUID());
    nameUserCache.invalidate(user.getName());
    uuidUserMap.remove(user.getUUID());
    nameUserMap.remove(user.getName());
  }

  @Override
  public Set<User> getOnlinePlayers() {
    Set<User> users = new HashSet<>();
    Bukkit.getOnlinePlayers().forEach(user -> users.add(this.getUser(user.getUniqueId())));

    return users;
  }

  @Override
  public void teleportToLocation(User user, Location location) {
    Validate.notNull(user);

    if (user.getBukkitTask() != null) {
      String translatedMessage = ChatColor.translateAlternateColorCodes('&', this.plugin.getMessages().getCurrentlyTeleporting());
      user.getPlayer().sendMessage(translatedMessage);
      return;
    }

    if (location == null) {
      Location lastLocation = user.getPlayer().getBedSpawnLocation();
      user.setLastLocation(lastLocation);
    }

    Runnable runnable = new TeleportRequestTask(this.plugin, location, user);
    BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(this.plugin, runnable, 0, 20);
    user.setBukkitTask(bukkitTask);
  }

}
