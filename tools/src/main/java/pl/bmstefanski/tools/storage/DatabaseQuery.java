package pl.bmstefanski.tools.storage;

import java.sql.ResultSet;

public interface DatabaseQuery {

  int executeUpdate();

  ResultSet executeQuery();

}
