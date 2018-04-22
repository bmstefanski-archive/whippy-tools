package pl.bmstefanski.tools.impl.storage.callable;

import com.zaxxer.hikari.HikariDataSource;
import pl.bmstefanski.tools.storage.DatabaseCallable;

public class DatabaseConnectionCallable implements DatabaseCallable<Void> {

    private HikariDataSource dataSource;

    private final String serverName;
    private final int port;
    private final String databaseName;
    private final String user;
    private final String password;

    public DatabaseConnectionCallable(HikariDataSource dataSource, String serverName, int port, String databaseName, String user, String password) {
        this.serverName = serverName;
        this.port = port;
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
        this.dataSource = dataSource;
    }

    @Override
    public Void call() {
        int cores = Runtime.getRuntime().availableProcessors();

        this.dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        this.dataSource.addDataSourceProperty("serverName", serverName);
        this.dataSource.addDataSourceProperty("port", port);
        this.dataSource.addDataSourceProperty("databaseName", databaseName);
        this.dataSource.addDataSourceProperty("user", user);
        this.dataSource.addDataSourceProperty("password", password);
        this.dataSource.addDataSourceProperty("cachePrepStmts", true);
        this.dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        this.dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.dataSource.addDataSourceProperty("useServerPrepStmts", true);
        this.dataSource.setMaximumPoolSize((cores * 2) + 1);

        return null;
    }
}
