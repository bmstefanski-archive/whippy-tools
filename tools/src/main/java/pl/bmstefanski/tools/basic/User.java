package pl.bmstefanski.tools.basic;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Optional;
import java.util.UUID;

public interface User {

  UUID getUniqueId();

  Optional<String> getName();

  String getIp();

  Player getPlayer();

  void setUniqueId(UUID uniqueId);

  void setName(String name);

  void setIp(String ip);

  void setGod(boolean god);

  void setAfk(boolean afk);

  boolean isOnline();

  boolean isGod();

  boolean isAfk();

  @Deprecated
  boolean isSecure();

  @Deprecated
  void setSecure(boolean secure);

  boolean isMark();

  void setMark(boolean mark);

  Location getLastLocation();

  void setLastLocation(Location location);

  Optional<BukkitTask> getBukkitTask();

  void setBukkitTask(BukkitTask bukkitTask);

}
