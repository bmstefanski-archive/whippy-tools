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

package pl.bmstefanski.tools.basic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.api.basic.Ban;

import java.util.UUID;

public class BanImpl implements Ban {

    private final String punisher;
    private final UUID punished;
    private String reason;
    private long time;

    public BanImpl(UUID punished, String punisher) {
        this.punished = punished;
        this.punisher = punisher;
    }

    @Override
    public UUID getPunished() {
        return punished;
    }

    @Override
    public Player getPunishedPlayer() {
        return Bukkit.getPlayer(punished);
    }

    @Override
    public String getPunisher() {
        return punisher;
    }

    @Override
    public Player getPunisherPlayer() {
        return Bukkit.getPlayer(punisher);
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public void setTime(long time) {
        this.time = time;
    }
}
