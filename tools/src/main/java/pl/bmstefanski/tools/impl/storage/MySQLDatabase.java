/*
 MIT License

 Copyright (c) 2018 Whippy ToolsImpl

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package pl.bmstefanski.tools.impl.storage;

import com.zaxxer.hikari.HikariDataSource;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.storage.callable.DatabaseConnectionCallable;
import pl.bmstefanski.tools.storage.DatabaseCallable;
import pl.bmstefanski.tools.util.thread.Executor;
import pl.bmstefanski.tools.impl.util.thread.ExecutorInitializer;

import java.sql.SQLException;

public class MySQLDatabase extends AbstractDatabase {

    private final Tools plugin;
    private HikariDataSource dataSource;

    private final String databaseName;
    private final String serverName;
    private final String user;
    private final String password;
    private final int port;


    public MySQLDatabase(Tools plugin, String databaseName, String serverName, String user, String password, int port) {
        this.plugin = plugin;
        this.databaseName = databaseName;
        this.serverName = serverName;
        this.user = user;
        this.password = password;
        this.port = port;
        this.dataSource = new HikariDataSource();

        this.connect();

        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void connect() {
        DatabaseCallable<Void> databaseCallable = new DatabaseConnectionCallable(
                this.dataSource,
                this.serverName,
                this.port,
                this.databaseName,
                this.user,
                this.password
        );

        Executor<Void> executor = new ExecutorInitializer<Void>().newExecutor(databaseCallable);
        executor.execute();
    }

}
