package pl.bmstefanski.tools.impl.storage.callable;

import pl.bmstefanski.tools.storage.DatabaseCallable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExecuteQueryCallable implements DatabaseCallable<ResultSet> {

    private final PreparedStatement preparedStatement;

    public ExecuteQueryCallable(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    @Override
    public ResultSet call() throws Exception {
        return this.preparedStatement.executeQuery();
    }

}
