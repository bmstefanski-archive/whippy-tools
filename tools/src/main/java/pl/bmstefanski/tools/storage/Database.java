package pl.bmstefanski.tools.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {

  void connect() throws SQLException;

  boolean closeConnection() throws SQLException;

  Connection getConnection();

}
