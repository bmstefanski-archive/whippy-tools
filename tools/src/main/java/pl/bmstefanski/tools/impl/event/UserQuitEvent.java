package pl.bmstefanski.tools.impl.event;

import pl.bmstefanski.tools.basic.User;

public class UserQuitEvent extends EventImpl {

  private final User user;

  public UserQuitEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
