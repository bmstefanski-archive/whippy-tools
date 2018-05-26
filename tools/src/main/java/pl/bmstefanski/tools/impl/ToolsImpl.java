/*
 MIT License

 Copyright (c) 2018 Whippy ToolsImpl

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

package pl.bmstefanski.tools.impl;

import ch.jalu.injector.Injector;
import ch.jalu.injector.InjectorBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.diorite.config.ConfigManager;
import pl.bmstefanski.commands.BukkitCommands;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.*;
import pl.bmstefanski.tools.impl.manager.UserManagerImpl;
import pl.bmstefanski.tools.impl.storage.DatabaseFactory;
import pl.bmstefanski.tools.impl.storage.resource.UserResourceImpl;
import pl.bmstefanski.tools.listener.*;
import pl.bmstefanski.tools.manager.UserManager;
import pl.bmstefanski.tools.storage.Database;
import pl.bmstefanski.tools.storage.Resource;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ToolsImpl extends JavaPlugin implements Tools {

  private final File pluginConfigFile = new File(getDataFolder(), "config.yml");
  private final File messagesFile = new File(getDataFolder(), "messages.yml");

  private static ToolsImpl instance;

  private ExecutorService executorService;
  private BukkitCommands bukkitCommands;
  private PluginConfig pluginConfig;
  private UserManager userManager;
  private Messages messages;
  private Database database;
  private Resource resource;
  private Injector injector;

  public ToolsImpl() {
    instance = this;
  }

  @Override
  public void onEnable() {
    this.initialize();
  }

  private void initialize() {
    this.injector = new InjectorBuilder().addDefaultHandlers("pl.bmstefanski.tools").create();
    this.pluginConfig = ConfigManager.createInstance(PluginConfig.class);
    this.messages = ConfigManager.createInstance(Messages.class);

    this.pluginConfig.bindFile(pluginConfigFile);
    this.messages.bindFile(messagesFile);

    this.pluginConfig.load();
    this.pluginConfig.save();
    this.messages.load();
    this.messages.save();

    this.executorService = Executors.newCachedThreadPool();
    this.database = DatabaseFactory.getDatabase("mysql");
    this.injector.register(Database.class, this.database);

    this.injector.register(Tools.class, this);
    this.injector.register(PluginConfig.class, this.pluginConfig);
    this.injector.register(Messages.class, this.messages);
    this.injector.register(ExecutorService.class, this.executorService);
    this.injector.register(Server.class, this.getServer());

    this.userManager = this.injector.getSingleton(UserManagerImpl.class);
    this.injector.register(UserManager.class, this.userManager);

    this.resource = this.injector.getSingleton(UserResourceImpl.class);
    this.injector.register(Resource.class, this.resource);

    this.resource.checkTable();
    this.resource.load();

    this.bukkitCommands = new BukkitCommands(this);

    Bukkit.getMessenger().registerIncomingPluginChannel(this, "MC|CPack", this.injector.getSingleton(BlazingPackMessageReceivedListener.class));

    this.registerListeners(
      PlayerCommandPreprocessListener.class,
      PlayerJoinListener.class,
      PlayerQuitListener.class,
      PlayerMoveListener.class,
      EntityDamageListener.class,
      PlayerDeathListener.class,
      PlayerLoginListener.class,
      PlayerInteractListener.class,
      EntityPickupItemListener.class,
      PlayerPreLoginListener.class
    );

    this.registerCommands(
      AfkCommand.class,
      BackCommand.class,
      BroadcastCommand.class,
      ClearCommand.class,
      DayCommand.class,
      DisableCommand.class,
      EnderchestCommand.class,
      FeedCommand.class,
      FlyCommand.class,
      GamemodeCommand.class,
      GodCommand.class,
      HatCommand.class,
      HealCommand.class,
      InvseeCommand.class,
      KickAllCommand.class,
      KickCommand.class,
      LightningCommand.class,
      ListCommand.class,
      MarkCommand.class,
      NicknameCommand.class,
      NightCommand.class,
      RealnameCommand.class,
      ReloadCommand.class,
      RepairCommand.class,
      SkullCommand.class,
      ToolsCommand.class,
      TpAllCommand.class,
      TpCommand.class,
      TpHereCommand.class,
      TpPosCommand.class,
      WhoisCommand.class,
      WorkbenchCommand.class
    );
  }

  @Override
  public void onDisable() {
    this.pluginConfig.save();
    this.messages.save();
  }

  @SafeVarargs
  private final void registerCommands(Class<? extends CommandExecutor>... executors) {
    for (Class<? extends CommandExecutor> commandExecutor : executors) {
      this.bukkitCommands.register(this.injector.getSingleton(commandExecutor));
      this.bukkitCommands.unregisterBlockedCommands(this.injector.getSingleton(commandExecutor), this.pluginConfig.getBlockedCommands());
    }
  }

  @SafeVarargs
  private final void registerListeners(Class<? extends Listener>... listeners) {
    for (Class<? extends Listener> listener : listeners) {
      Bukkit.getPluginManager().registerEvents(this.injector.getSingleton(listener), this);
    }
  }

  @Override
  public PluginConfig getPluginConfig() {
    return this.pluginConfig;
  }

  @Override
  public Messages getMessages() {
    return this.messages;
  }

  @Override
  public Database getDatabase() {
    return this.database;
  }

  @Override
  public ExecutorService getExecutorService() {
    return this.executorService;
  }

  public static ToolsImpl getInstance() {
    return instance;
  }

}
