package pl.bmstefanski.tools.storage;

import java.util.concurrent.Callable;

public interface DatabaseCallable<T> extends Callable<T> {}
