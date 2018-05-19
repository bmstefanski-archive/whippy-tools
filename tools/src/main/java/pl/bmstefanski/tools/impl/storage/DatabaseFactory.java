package pl.bmstefanski.tools.impl.storage;

import org.apache.commons.lang.Validate;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.ToolsImpl;
import pl.bmstefanski.tools.impl.exception.UnknownDatabaseTypeException;
import pl.bmstefanski.tools.storage.Database;

import java.util.Optional;

public final class DatabaseFactory {

  private static final Tools PLUGIN = ToolsImpl.getInstance();

  public static Database getDatabase(String databaseName) {
    Validate.notNull(databaseName, "Database type's name cannot be null");

    Optional<Database> optionalDatabase = Optional.empty();
    if (databaseName.equalsIgnoreCase("mysql")) {
      optionalDatabase = Optional.of(new MysqlDatabase(
        PLUGIN,
        PLUGIN.getConfiguration().getMySQLSection().getDatabase(),
        PLUGIN.getConfiguration().getMySQLSection().getHostname(),
        PLUGIN.getConfiguration().getMySQLSection().getUsername(),
        PLUGIN.getConfiguration().getMySQLSection().getPassword(),
        PLUGIN.getConfiguration().getMySQLSection().getPort())); // temporary, until i refactor configs
    }

    return optionalDatabase.orElseThrow(UnknownDatabaseTypeException::new);
  }

  private DatabaseFactory() {}

}
