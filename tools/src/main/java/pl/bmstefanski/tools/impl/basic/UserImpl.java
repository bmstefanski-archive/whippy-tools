/*
 MIT License

 Copyright (c) 2018 Whippy ToolsImpl

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

package pl.bmstefanski.tools.impl.basic;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.ToolsImpl;

import java.util.UUID;

public class UserImpl implements User {

    private UUID uuid;
    private String name;
    private String ip;
    private boolean god;
    private boolean afk;
    private boolean secure;
    private boolean mark;
    private Location lastLocation;
    private BukkitTask bukkitTask;

    public UserImpl(UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getOfflinePlayer(uuid).getName();

        ToolsImpl.getInstance().getUserManager().addUser(this);
    }

    public UserImpl(String playerName) {
        this.name = playerName;
        // todo uuid
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIp() {
        return isOnline() ? getPlayer().getAddress().getAddress().toString() : ip;
    }

    @Override
    public Player getPlayer() {
        if (!isOnline()) {
            return null;
        }

        return Bukkit.getPlayer(this.uuid);
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public void setGod(boolean god) {
        this.god = god;
    }

    @Override
    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    @Override
    public boolean isOnline() {
        if (this.uuid == null) {
            return false;
        }

        Player player = Bukkit.getPlayer(this.uuid);
        return player != null;
    }

    @Override
    public boolean isGod() {
        return god;
    }

    @Override
    public boolean isAfk() {
        return this.afk;
    }

    @Override
    @Deprecated
    public boolean isSecure() {
        return this.secure;
    }

    @Override
    @Deprecated
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean isMark() {
        return this.mark;
    }

    @Override
    public void setMark(boolean mark) {
        this.mark = mark;
    }

    @Override
    public Location getLastLocation() {
        return this.lastLocation;
    }

    @Override
    public void setLastLocation(Location location) {
        this.lastLocation = location;
    }

    @Override
    public BukkitTask getBukkitTask() {
        return this.bukkitTask;
    }

    @Override
    public void setBukkitTask(BukkitTask bukkitTask) {
        this.bukkitTask = bukkitTask;
    }

}
