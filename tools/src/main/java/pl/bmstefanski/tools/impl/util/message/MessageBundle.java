package pl.bmstefanski.tools.impl.util.message;

import org.apache.commons.lang3.Validate;
import pl.bmstefanski.tools.impl.type.MessageType;

import static pl.bmstefanski.tools.impl.util.MessageUtil.*;

public final class MessageBundle {

  public static MessageBuilder create(MessageType messageType) {
    Validate.notNull(messageType.getMessage());

    return new MessageBuilder(messageType.getMessage());
  }

  public static MessageBuilder create(String message) {
    Validate.notNull(message);

    return new MessageBuilder(colored(message));
  }

  private MessageBundle() {
  }

}
