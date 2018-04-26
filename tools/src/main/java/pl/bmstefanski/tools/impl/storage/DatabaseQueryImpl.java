package pl.bmstefanski.tools.impl.storage;

import pl.bmstefanski.tools.impl.storage.callable.ExecuteQueryCallable;
import pl.bmstefanski.tools.impl.storage.callable.ExecuteUpdateCallable;
import pl.bmstefanski.tools.storage.DatabaseCallable;
import pl.bmstefanski.tools.storage.DatabaseQuery;
import pl.bmstefanski.tools.util.thread.Executor;
import pl.bmstefanski.tools.impl.util.thread.ExecutorInitializer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseQueryImpl implements DatabaseQuery {

    private final PreparedStatement preparedStatement;

    public DatabaseQueryImpl(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    @Override
    public int executeUpdate() {
        DatabaseCallable<Integer> databaseCallable = new ExecuteUpdateCallable(this.preparedStatement);
        Executor<Integer> executor = new ExecutorInitializer<Integer>().newExecutor(databaseCallable);

        return executor.execute();
    }

    @Override
    public ResultSet executeQuery() {
        DatabaseCallable<ResultSet> databaseCallable = new ExecuteQueryCallable(this.preparedStatement);
        Executor<ResultSet> executor = new ExecutorInitializer<ResultSet>().newExecutor(databaseCallable);

        return executor.execute();
    }

}
