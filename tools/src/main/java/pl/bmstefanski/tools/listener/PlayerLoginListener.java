package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.runnable.LoadDataTask;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class PlayerLoginListener implements Listener {

    private final Tools plugin;
    private final Messages messages;

    public PlayerLoginListener(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {

        Player player = event.getPlayer();
        User user = this.plugin.getUserManager().getUser(player.getUniqueId());

        if (!player.hasPlayedBefore()) {
            user.setName(player.getName());
        }

        int maxNicknameLength = this.plugin.getConfiguration().getMaxNicknameLength();

        if (player.getName().length() > maxNicknameLength) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringUtils.replace(this.messages.getTooLongNickname(), "%max%", maxNicknameLength + ""));
            return;
        }

        new LoadDataTask(user).runTask(this.plugin);
    }

}
