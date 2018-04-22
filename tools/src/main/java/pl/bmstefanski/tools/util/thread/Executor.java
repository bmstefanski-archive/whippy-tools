package pl.bmstefanski.tools.util.thread;

/*
 * @author bartzzdev
 * @source https://github.com/bartzzdev/LightLogin/blob/rewrite/src/main/java/net/bartzz/lightlogin/api/threads/Executor.java
 * */

public interface Executor<T> {

    T execute();

}
