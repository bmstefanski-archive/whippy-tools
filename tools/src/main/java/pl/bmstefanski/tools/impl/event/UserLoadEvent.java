package pl.bmstefanski.tools.impl.event;

import pl.bmstefanski.tools.basic.User;

public class UserLoadEvent extends EventImpl {

  private final User user;

  public UserLoadEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}
