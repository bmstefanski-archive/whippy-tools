package pl.bmstefanski.tools.impl.manager;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.runnable.TeleportRequestTask;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class UserManagerImpl implements UserManager {

  private final Tools plugin;
  private final Messages messages;
  private final PluginConfig config;
  private final Server server;

  private final ConcurrentMap<String, User> userNameMap = new ConcurrentHashMap<>(16, 0.9F, 1);
  private final ConcurrentMap<UUID, User> userUniqueIdMap = new ConcurrentHashMap<>(16, 0.9F, 1);

  @Inject
  UserManagerImpl(Tools plugin, Messages messages, PluginConfig config, Server server) {
    this.plugin = plugin;
    this.messages = messages;
    this.config = config;
    this.server = server;
  }

  @Override
  public Optional<User> getUser(UUID uniqueId) {
    Validate.notNull(uniqueId, "Player unique id cannot be null!");

    return this.userUniqueIdMap.values()
      .stream()
      .filter(user -> user.getUniqueId().equals(uniqueId))
      .findFirst();
  }

  @Override
  public Optional<User> getUser(String playerName) {
    Validate.notNull(playerName, "Player name cannot be null!");

    return this.userNameMap.values()
      .stream()
      .filter(user -> user.getName().get().equals(playerName))
      .findFirst();
  }

  @Override
  public Optional<User> getUser(Player player) {
    Validate.notNull(player, "Player cannot be null!");

    return this.getUser(player.getUniqueId());
  }

  @Override
  public void addUser(User user) {
    Validate.notNull(user);

    this.userUniqueIdMap.put(user.getUniqueId(), user);
    if (user.getName().isPresent()) {
      this.userNameMap.put(user.getName().get(), user);
    }
  }

  @Override
  public void removeUser(User user) {
    Validate.notNull(user);

    this.userUniqueIdMap.remove(user.getUniqueId());
    this.userNameMap.remove(user.getName().get());
  }

  @Override
  public Set<User> getOnlinePlayers() {
    return this.server.getOnlinePlayers().stream()
      .map(player -> this.getUser(player.getUniqueId()))
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public void teleportToLocation(User user, Location location) {
    Validate.notNull(user);

    if (user.getBukkitTask().isPresent()) {
      String translatedMessage = ChatColor.translateAlternateColorCodes('&', this.messages.getCurrentlyTeleporting());
      user.getPlayer().sendMessage(translatedMessage);
      return;
    }

    if (location == null) {
      Location lastLocation = user.getPlayer().getBedSpawnLocation();
      user.setLastLocation(lastLocation);
    }

    Runnable runnable = new TeleportRequestTask(this.plugin, this.messages, this.config, location, user);
    BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(this.plugin, runnable, 0, 20);
    user.setBukkitTask(bukkitTask);
  }

  @Override
  public ImmutableMap<String, User> getUserNameMap() {
    return ImmutableMap.copyOf(this.userNameMap);
  }

  @Override
  public ImmutableMap<UUID, User> getUserUniqueIdMap() {
    return ImmutableMap.copyOf(this.userUniqueIdMap);
  }

}
