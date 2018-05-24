package pl.bmstefanski.tools.impl.event;

import pl.bmstefanski.tools.basic.User;

public class UserInitEvent extends EventImpl {

  private final User user;

  public UserInitEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
