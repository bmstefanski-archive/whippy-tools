package pl.bmstefanski.tools;

import org.bukkit.plugin.Plugin;
import pl.bmstefanski.tools.storage.Database;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import java.util.concurrent.ExecutorService;

public interface Tools extends Plugin {

  PluginConfig getPluginConfig();

  Messages getMessages();

  Database getDatabase();

  ExecutorService getExecutorService();

}
