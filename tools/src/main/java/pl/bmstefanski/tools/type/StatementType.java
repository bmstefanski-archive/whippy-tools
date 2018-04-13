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

package pl.bmstefanski.tools.type;

import pl.bmstefanski.tools.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum StatementType {

    LOAD_PLAYER("SELECT * FROM `players` WHERE `uuid` = ?"),
    SAVE_PLAYER("INSERT INTO `players` (`uuid`, `name`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `name` = ?"),
    CHECK_PLAYER("CREATE TABLE IF NOT EXISTS `players` (`uuid` BINARY(16) NOT NULL, `name` VARCHAR(50) NOT NULL, PRIMARY KEY (`uuid`), UNIQUE INDEX (`name`));"),
    LOAD_BANS("SELECT * FROM `bans`"),
    SAVE_BANS("UPDATE `bans` SET `reason` = ?, `until` = ? WHERE `punished` = ?"),
    ADD_BAN("INSERT INTO `bans` (`punisher`, `punished`, `until`, `reason`) VALUES (?, ?, ?, ?)"),
    REMOVE_BAN("DELETE FROM `bans` WHERE `punished` = ?"),
    CHECK_BAN("CREATE TABLE IF NOT EXISTS `bans` (`punisher` VARCHAR(100) NOT NULL, `punished` BINARY(16) NOT NULL, `until` BIGINT NOT NULL, `reason` VARCHAR(100) NOT NULL);");

    private String sql;

    StatementType(String sql) {
        this.sql = sql;
    }

    public PreparedStatement build() {

        Connection connection = Tools.getInstance().getDatabase().getConnection();

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(this.sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statement;
    }
}
