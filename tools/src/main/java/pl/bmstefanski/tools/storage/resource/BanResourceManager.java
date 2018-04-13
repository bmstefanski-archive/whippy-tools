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

package pl.bmstefanski.tools.storage.resource;

import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.Ban;
import pl.bmstefanski.tools.api.storage.Resource;
import pl.bmstefanski.tools.basic.BanImpl;
import pl.bmstefanski.tools.basic.manager.BanManager;
import pl.bmstefanski.tools.type.StatementType;
import pl.bmstefanski.tools.util.UUIDUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BanResourceManager implements Resource {

    private final Tools plugin;
    private final List<Ban> banList = BanManager.getBans();

    public BanResourceManager(Tools plugin) {
        this.plugin = plugin;
    }

    @Override
    public void load() {
        try {
            PreparedStatement preparedStatement = StatementType.LOAD_BANS.build();

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID punished = UUIDUtils.getUUIDFromBytes(resultSet.getBytes("punished"));
                String punisher = resultSet.getString("punisher");

                Ban ban = new BanImpl(punished, punisher);

                ban.setReason(resultSet.getString("reason"));
                ban.setTime(resultSet.getLong("until"));

                banList.add(ban);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save() {

        try {
            for (Ban ban : banList) {
                PreparedStatement preparedStatement = StatementType.SAVE_BANS.build();

                preparedStatement.setString(1, ban.getReason());
                preparedStatement.setLong(2, ban.getTime());
                preparedStatement.setBytes(3, UUIDUtils.getBytesFromUUID(ban.getPunished()));

                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void add(Ban ban) {

        try {
            PreparedStatement preparedStatement = StatementType.ADD_BAN.build();

            preparedStatement.setString(1, ban.getPunisher());
            preparedStatement.setBytes(2, UUIDUtils.getBytesFromUUID(ban.getPunished()));
            preparedStatement.setLong(3, ban.getTime());
            preparedStatement.setString(4, ban.getReason());

            preparedStatement.executeUpdate();

            banList.add(ban);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Ban ban) {

        try {
            PreparedStatement preparedStatement = StatementType.REMOVE_BAN.build();

            preparedStatement.setBytes(1, UUIDUtils.getBytesFromUUID(ban.getPunished()));

            preparedStatement.executeUpdate();

            banList.remove(ban);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
