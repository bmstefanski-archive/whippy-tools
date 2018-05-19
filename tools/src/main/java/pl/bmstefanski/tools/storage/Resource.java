package pl.bmstefanski.tools.storage;

import pl.bmstefanski.tools.basic.User;

public interface Resource {

  void load();

  void save(User user);

  void checkTable();

}
