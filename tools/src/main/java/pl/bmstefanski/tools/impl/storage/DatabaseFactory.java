package pl.bmstefanski.tools.impl.storage;

import org.apache.commons.lang.Validate;
import pl.bmstefanski.tools.impl.ToolsImpl;
import pl.bmstefanski.tools.impl.exception.UnknownDatabaseTypeException;
import pl.bmstefanski.tools.storage.Database;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import java.util.Optional;

public final class DatabaseFactory {

  private final static PluginConfig CONFIG = ToolsImpl.getInstance().getPluginConfig();

  public static Database getDatabase(String databaseName) {
    Validate.notNull(databaseName, "Database type's name cannot be null");

    Optional<Database> optionalDatabase = Optional.empty();
    if (databaseName.equalsIgnoreCase("mysql")) {
      optionalDatabase = Optional.of(new MysqlDatabase(
        ToolsImpl.getInstance(),
        ToolsImpl.getInstance().getExecutorService(),
        CONFIG.getMySQLSection().getDatabase(),
        CONFIG.getMySQLSection().getHostname(),
        CONFIG.getMySQLSection().getUsername(),
        CONFIG.getMySQLSection().getPassword(),
        CONFIG.getMySQLSection().getPort())); // temporary, until i refactor configs
    }

    return optionalDatabase.orElseThrow(UnknownDatabaseTypeException::new);
  }

  private DatabaseFactory() {}

}
