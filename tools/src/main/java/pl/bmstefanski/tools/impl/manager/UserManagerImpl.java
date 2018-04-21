package pl.bmstefanski.tools.impl.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.basic.UserImpl;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.runnable.TeleportRequestTask;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserManagerImpl implements UserManager {

    private final Tools plugin;
    private final Cache<UUID, User> uuidUserCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
    private final Cache<String, User> nameUserCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    public UserManagerImpl(Tools plugin) {
        this.plugin = plugin;
    }

    @Override
    public User getUser(UUID uuid) {
        Validate.notNull(uuid);

        User user = this.uuidUserCache.getIfPresent(uuid);
        return Optional.of(user).orElseGet(() -> new UserImpl(uuid));
    }

    @Override
    public User getUser(String userName) {
        Validate.notNull(userName);

        User user = this.nameUserCache.getIfPresent(userName);
        return Optional.of(user).orElseGet(() -> new UserImpl(userName));
    }

    @Override
    public void addUser(User user) {
        Validate.notNull(user);

        this.uuidUserCache.put(user.getUUID(), user);
        this.nameUserCache.put(user.getName(), user);
    }

    @Override
    public void invalidateUser(User user) {
        Validate.notNull(user);

        this.uuidUserCache.invalidate(user);
        this.nameUserCache.invalidate(user);
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
        Validate.notNull(location);

        Runnable runnable = new TeleportRequestTask(this.plugin, location, user);
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(this.plugin, runnable, 0, 20);
        user.setBukkitTask(bukkitTask);
    }

}
