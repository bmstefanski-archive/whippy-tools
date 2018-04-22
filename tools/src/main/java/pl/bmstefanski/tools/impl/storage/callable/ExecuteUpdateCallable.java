package pl.bmstefanski.tools.impl.storage.callable;

import pl.bmstefanski.tools.storage.DatabaseCallable;

import java.sql.PreparedStatement;

public class ExecuteUpdateCallable implements DatabaseCallable<Integer> {

    private final PreparedStatement preparedStatement;

    public ExecuteUpdateCallable(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    @Override
    public Integer call() throws Exception {
        return this.preparedStatement.executeUpdate();
    }

}
