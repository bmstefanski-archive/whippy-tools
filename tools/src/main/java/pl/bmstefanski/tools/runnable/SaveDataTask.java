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
import pl.bmstefanski.tools.type.StatementType;
import pl.bmstefanski.tools.util.UUIDUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveDataTask extends BukkitRunnable {

    private final User user;

    public SaveDataTask(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {
            PreparedStatement preparedStatement = StatementType.SAVE_PLAYER.build();

            preparedStatement.setBytes(1, UUIDUtils.getBytesFromUUID(this.user.getUUID()));
            preparedStatement.setString(2, this.user.getName());
            preparedStatement.setString(3, this.user.getName());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
