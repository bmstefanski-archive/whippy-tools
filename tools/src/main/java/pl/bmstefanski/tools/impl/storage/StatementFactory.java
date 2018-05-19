package pl.bmstefanski.tools.impl.storage;

import org.apache.commons.lang.Validate;
import pl.bmstefanski.tools.impl.ToolsImpl;
import pl.bmstefanski.tools.impl.exception.UnknownStatementTypeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public final class StatementFactory {

  private static final String USERS_TABLE_QUERY;
  private static final String PLAYERS_INSERT_QUERY;
  private static final String PLAYERS_SELECT_QUERY;
  private static final String PLAYERS_DELETE_QUERY;

  static {
    USERS_TABLE_QUERY = new StatementBuilder().createTableIfDoesNotExist("players", "`uuid` BINARY(16) PRIMARY KEY, `name` VARCHAR(16) NOT NULL UNIQUE").build();
    PLAYERS_INSERT_QUERY= new StatementBuilder().insertInto("players").values("?, ?").onDuplicateKey().update("`name` = ?").build();
    PLAYERS_SELECT_QUERY = new StatementBuilder().select().all().from("players").build();
    PLAYERS_DELETE_QUERY= new StatementBuilder().delete().from("players").where("`uuid` = ?").build();
  }

  public static PreparedStatement getStatement(String preparedStatement) {
    Validate.notNull(preparedStatement, "Prepared statement name cannot be null!");

    Connection connection = ToolsImpl.getInstance().getDatabase().getConnection();
    Optional<PreparedStatement> result = Optional.empty();
    try {

      if (preparedStatement.equalsIgnoreCase("players-select")) {
        result = Optional.of(connection.prepareStatement(PLAYERS_SELECT_QUERY));
      } else if (preparedStatement.equalsIgnoreCase("players-insert")) {
        result = Optional.of(connection.prepareStatement(PLAYERS_INSERT_QUERY));
      } else if (preparedStatement.equalsIgnoreCase("players-delete")) {
        result = Optional.of(connection.prepareStatement(PLAYERS_DELETE_QUERY));
      } else if (preparedStatement.equalsIgnoreCase("users-table")) {
        result = Optional.of(connection.prepareStatement(USERS_TABLE_QUERY));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return result.orElseThrow(UnknownStatementTypeException::new);
  }

  private StatementFactory() {}

}
