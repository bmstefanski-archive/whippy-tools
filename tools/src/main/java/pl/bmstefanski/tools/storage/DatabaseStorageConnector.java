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

import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.storage.Database;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.type.DatabaseType;

public class DatabaseStorageConnector {

    private final DatabaseType databaseType;
    private final Tools plugin;
    private Database database;

    public DatabaseStorageConnector(Tools plugin, DatabaseType databaseType) {
        this.plugin = plugin;
        this.databaseType = databaseType;

        connect();
    }

    public void connect() {

        PluginConfig config = plugin.getConfiguration();

        switch (databaseType) {
            case MYSQL:
                this.database = new MySQLDatabase(
                        config.getMySQLSection().getHostname(),
                        config.getMySQLSection().getPort(),
                        config.getMySQLSection().getDatabase(),
                        config.getMySQLSection().getUsername(),
                        config.getMySQLSection().getPassword()
                );
        }

    }

    public Database getDatabase() {
        return database;
    }
}
