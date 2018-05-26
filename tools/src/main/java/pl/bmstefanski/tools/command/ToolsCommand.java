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

package pl.bmstefanski.tools.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.type.MessageType;
import pl.bmstefanski.tools.impl.util.message.MessageBundle;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import javax.inject.Inject;

public class ToolsCommand implements CommandExecutor {

  private final Tools plugin;

  @Inject
  ToolsCommand(Tools plugin) {
    this.plugin = plugin;
  }

  @Command(name = "tools")
  @Permission("tools.command.tools")
  @GameOnly(false)
  @Override
  public void execute(CommandSender commandSender, CommandArguments commandArguments) {
    String message = "&e" + plugin.getDescription().getName() + " &7" + plugin.getDescription().getVersion() +
      " by &e" + plugin.getDescription().getAuthors();

    MessageBundle.create(message).sendTo(commandSender);
  }

}
