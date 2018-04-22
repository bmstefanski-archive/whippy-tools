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

package pl.bmstefanski.tools.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.storage.DatabaseQueryImpl;
import pl.bmstefanski.tools.storage.DatabaseConsumer;
import pl.bmstefanski.tools.type.StatementType;
import pl.bmstefanski.tools.util.UUIDUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadDataTask extends BukkitRunnable {

    private final User user;

    public LoadDataTask(User user) {
        this.user = user;
    }

    @Override
    public void run() {

        PreparedStatement preparedStatement = StatementType.LOAD_PLAYER.create();
        try {
            preparedStatement.setString(1, this.user.getUUID().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = new DatabaseQueryImpl(preparedStatement).executeQuery();

        DatabaseConsumer<ResultSet> consumer = result -> {
            try {

                while (result.next()) {
                    this.user.setUUID(UUIDUtils.getUUIDFromBytes(result.getBytes("uuid")));
                    this.user.setName(result.getString("name"));

                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        };

        consumer.accept(resultSet);
    }

}
