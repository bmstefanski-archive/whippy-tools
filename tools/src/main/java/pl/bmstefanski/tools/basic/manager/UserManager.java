/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package pl.bmstefanski.tools.basic.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.UserImpl;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class UserManager {

    private static final Map<UUID, User> UUID_USER_MAP = new HashMap<>();
    private static final Map<String, User> NAME_USER_MAP = new HashMap<>();

    private static final Cache<UUID, User> UUID_USER_CACHE = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
    private static final Cache<String, User> NAME_USER_CACHE = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    public static User getUser(UUID uuid) {
        User user = UUID_USER_CACHE.getIfPresent(uuid);

        if (user == null) {
            user = UUID_USER_MAP.get(uuid);

            if (user != null) {
                UUID_USER_MAP.put(uuid, user);
                return user;
            }

            return new UserImpl(uuid);
        }

        return user;
    }

    public static User getUser(String playerName) {
        User user = NAME_USER_CACHE.getIfPresent(playerName);

        if (user == null) {
            user = NAME_USER_MAP.get(playerName);

            if (user != null) {
                NAME_USER_MAP.put(playerName, user);
                return user;
            }

            return new UserImpl(playerName);
        }

        return user;
    }

    public static void addUser(User user) {
        UUID_USER_MAP.put(user.getUUID(), user);
        UUID_USER_CACHE.put(user.getUUID(), user);

        if (user.getName() != null) {
            NAME_USER_MAP.put(user.getName(), user);
            NAME_USER_CACHE.put(user.getName(), user);
        }
    }

    public static void removeUser(User user) {
        UUID_USER_MAP.remove(user.getUUID());
        UUID_USER_CACHE.invalidate(user.getUUID());

        NAME_USER_MAP.remove(user.getName());
        NAME_USER_CACHE.invalidate(user.getName());
    }

    public Collection<User> getOnlinePlayers() {
        Collection<User> userCollection = new HashSet<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            userCollection.add(getUser(player.getUniqueId()));
        }

        return userCollection;
    }
}
