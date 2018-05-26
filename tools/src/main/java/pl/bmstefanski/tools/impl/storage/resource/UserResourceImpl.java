package pl.bmstefanski.tools.impl.storage.resource;

import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.basic.UserImpl;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.impl.storage.DatabaseQueryImpl;
import pl.bmstefanski.tools.impl.storage.StatementFactory;
import pl.bmstefanski.tools.impl.util.UniqueIdUtil;
import pl.bmstefanski.tools.storage.Resource;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class UserResourceImpl implements Resource {

  private final UserManager userManager;
  private final ExecutorService executorService;

  @Inject
  UserResourceImpl(UserManager userManager, ExecutorService executorService) {
    this.userManager = userManager;
    this.executorService = executorService;
  }

  @Override
  public void load() {
    PreparedStatement preparedStatement = StatementFactory.getStatement("players-select");
    ResultSet resultSet = new DatabaseQueryImpl(this.executorService, preparedStatement).executeQuery();

    Consumer<ResultSet> consumer = result -> {
      try {
        while (resultSet.next()) {
          UUID uniqueId = UniqueIdUtil.getUUIDFromBytes(resultSet.getBytes("uuid"));

          User user = this.userManager.getUser(uniqueId).orElseGet(() -> {
            User newUser = new UserImpl(uniqueId);
            this.userManager.addUser(newUser);
            return newUser;
          });

          user.setUniqueId(uniqueId);
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
      preparedStatement.setBytes(1, UniqueIdUtil.getBytesFromUUID(user.getUniqueId()));
      preparedStatement.setString(2, user.getName().get());
      preparedStatement.setString(3, user.getName().get());
      new DatabaseQueryImpl(this.executorService, preparedStatement).executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void checkTable() {
    try (PreparedStatement preparedStatement = StatementFactory.getStatement("users-table")) {
      new DatabaseQueryImpl(this.executorService, preparedStatement).executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

}
