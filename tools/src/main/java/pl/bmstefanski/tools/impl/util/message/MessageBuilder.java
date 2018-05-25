package pl.bmstefanski.tools.impl.util.message;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.basic.User;

import java.util.Collection;

import static pl.bmstefanski.tools.impl.util.MessageUtil.*;

public final class MessageBuilder {

  private String messageContent = "";

  MessageBuilder(String messageContent) {
    this.messageContent = messageContent;
  }

  public MessageBuilder withField(String target, String replacement) {
    Validate.notNull(target, "Target value cannot be null!");
    Validate.notNull(replacement, "Replacement value cannot be null!");

    this.messageContent = StringUtils.replace(this.messageContent, "%" + target + "%", replacement);
    return this;
  }

  public void sendTo(Player player) {
    Validate.notNull(player, "Player cannot be null!");

    player.sendMessage(colored(this.messageContent));
  }

  public void sendTo(Collection<? extends Player> players) {
    players.forEach(this::sendTo);
  }

  public void sendTo(User user) {
    this.sendTo(user.getPlayer());
  }

  @Override
  public String toString() {
    return this.messageContent;
  }

}
