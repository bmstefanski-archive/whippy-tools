/*
 MIT License

 Copyright (c) 2018 Whippy Tools

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

package pl.bmstefanski.tools.storage;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;

public class MySQLDatabase extends AbstractDatabase {

    private HikariDataSource dataSource;

    private final String serverName;
    private final int port;
    private final String databaseName;
    private final String user;
    private final String password;

    public MySQLDatabase(String serverName, int port, String databaseName, String user, String password) {
        this.serverName = serverName;
        this.port = port;
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
        this.dataSource = new HikariDataSource();

        connect();

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {
        this.dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        this.dataSource.addDataSourceProperty("serverName", serverName);
        this.dataSource.addDataSourceProperty("port", port);
        this.dataSource.addDataSourceProperty("databaseName", databaseName);
        this.dataSource.addDataSourceProperty("user", user);
        this.dataSource.addDataSourceProperty("password", password);
        this.dataSource.setMaximumPoolSize(10);
    }

}
