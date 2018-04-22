package pl.bmstefanski.tools.util.thread;

/*
 * @author bartzzdev
 * @source https://github.com/bartzzdev/LightLogin/blob/rewrite/src/main/java/net/bartzz/lightlogin/api/threads/ExecutorInitializer.java
 * */

import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.ToolsImpl;
import pl.bmstefanski.tools.storage.DatabaseCallable;

public class ExecutorInitializer<T> {

    private final Tools plugin = ToolsImpl.getInstance();

    public Executor<T> newExecutor(DatabaseCallable<T> callable) {
        return () -> {
            try {
                return plugin.getExecutorService().submit(callable).get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        };
    }

}
