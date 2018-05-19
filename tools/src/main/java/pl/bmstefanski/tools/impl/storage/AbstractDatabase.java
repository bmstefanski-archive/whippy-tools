package pl.bmstefanski.tools.impl.storage;

import pl.bmstefanski.tools.storage.Database;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDatabase implements Database {

  protected Connection connection;

  @Override
  public Connection getConnection() {
    return this.connection;
  }

  @Override
  public boolean closeConnection() throws SQLException {
    if (this.connection != null) {
      this.connection.close();
      return false;
    }

    return true;
  }

}
