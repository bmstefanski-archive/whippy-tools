package pl.bmstefanski.tools.impl.storage.resource;

import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.storage.DatabaseQueryImpl;
import pl.bmstefanski.tools.impl.storage.StatementFactory;
import pl.bmstefanski.tools.impl.util.UniqueIdUtils;
import pl.bmstefanski.tools.storage.Resource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Consumer;

public class UserResourceImpl implements Resource {

  private final Tools plugin;

  public UserResourceImpl(Tools plugin) {
    this.plugin = plugin;
  }

  @Override
  public void load() {
    PreparedStatement preparedStatement = StatementFactory.getStatement("players-select");
    ResultSet resultSet = new DatabaseQueryImpl(plugin, preparedStatement).executeQuery();

    Consumer<ResultSet> consumer = result -> {
      try {
        while (resultSet.next()) {
          UUID uniqueId = UniqueIdUtils.getUUIDFromBytes(resultSet.getBytes("uuid"));
          User user = this.plugin.getUserManager().getUser(uniqueId);

          user.setUUID(uniqueId);
          user.setName(resultSet.getString("name"));

          preparedStatement.close();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    };

    consumer.accept(resultSet);
  }

  @Override
  public void save(User user) {
    try (PreparedStatement preparedStatement = StatementFactory.getStatement("players-insert")) {
      preparedStatement.setBytes(1, UniqueIdUtils.getBytesFromUUID(user.getUUID()));
      preparedStatement.setString(2, user.getName());
      new DatabaseQueryImpl(this.plugin, preparedStatement).executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void checkTable() {
    try(PreparedStatement preparedStatement = StatementFactory.getStatement("users-table")) {
      new DatabaseQueryImpl(this.plugin, preparedStatement).executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

}
