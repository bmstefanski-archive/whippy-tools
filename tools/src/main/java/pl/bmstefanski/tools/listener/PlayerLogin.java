package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.bmstefanski.tools.api.ToolsAPI;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.runnable.LoadDataTask;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class PlayerLogin implements Listener {

    private final ToolsAPI plugin;
    private final Messages messages;

    public PlayerLogin(ToolsAPI plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {

        Player player = event.getPlayer();
        User user = UserManager.getUser(player.getUniqueId());

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
