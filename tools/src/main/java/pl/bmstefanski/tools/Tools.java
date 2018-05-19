package pl.bmstefanski.tools;

import org.bukkit.plugin.Plugin;
import pl.bmstefanski.commands.BukkitCommands;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.Database;
import pl.bmstefanski.tools.storage.Resource;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import java.util.concurrent.ExecutorService;

public interface Tools extends Plugin {

  PluginConfig getConfiguration();

  Database getDatabase();

  UserManager getUserManager();

  Messages getMessages();

  BukkitCommands getBukkitCommands();

  ExecutorService getExecutorService();

  Resource getResource();

}
