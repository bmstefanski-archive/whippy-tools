package pl.bmstefanski.tools.impl.storage;

import pl.bmstefanski.tools.impl.storage.callable.ExecuteQueryCallable;
import pl.bmstefanski.tools.impl.storage.callable.ExecuteUpdateCallable;
import pl.bmstefanski.tools.storage.DatabaseQuery;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DatabaseQueryImpl implements DatabaseQuery {

  private final ExecutorService executorService;
  private final PreparedStatement preparedStatement;

  public DatabaseQueryImpl(ExecutorService executorService, PreparedStatement preparedStatement) {
    this.executorService = executorService;
    this.preparedStatement = preparedStatement;
  }

  @Override
  public int executeUpdate() {
    Callable<Integer> executeUpdateCallable = new ExecuteUpdateCallable(this.preparedStatement);
    Future<Integer> future = this.executorService.submit(executeUpdateCallable);

    Integer result = 0;
    try {
      result = future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return result;
  }

  @Override
  public ResultSet executeQuery() {
    Callable<ResultSet> executeQueryCallable = new ExecuteQueryCallable(this.preparedStatement);
    Future<ResultSet> future = this.executorService.submit(executeQueryCallable);

    ResultSet result = null;
    try {
      result = future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return result;
  }

}
