package pl.bmstefanski.tools.impl.event;

import pl.bmstefanski.tools.basic.User;

public class UserAbortEvent extends EventImpl {

  private final User user;

  public UserAbortEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
