package pl.bmstefanski.tools.impl.manager;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

  @Mock private Tools plugin;
  @Mock private PluginConfig config;
  @Mock private Messages messages;
  @Mock private Server server;

  @BeforeClass
  public static void initialize() {
    ServerMock serverMock = new ServerMock();
    Bukkit.setServer(serverMock);
  }

//  @Test(expected = UnimplementedOperationException.class)
//  // waiting for implementation https://github.com/seeseemelk/MockBukkit/blob/master/src/main/java/be/seeseemelk/mockbukkit/ServerMock.java#L876
//  public void userNameAndUniqueIdCannotBeNullWhenCalledByUniqueId() {
//    UUID uniqueId = UUID.randomUUID();
//    User user = new UserManagerImpl(this.plugin, this.messages, this.config, this.server).getUser(uniqueId).get();
//
//    assertNotNull(user.getUniqueId());
//    assertNotNull(user.getName());
//  }
//
//  @Test(expected = UnimplementedOperationException.class)
//  // waiting for implementation https://github.com/seeseemelk/MockBukkit/blob/master/src/main/java/be/seeseemelk/mockbukkit/ServerMock.java#L876
//  public void userNameAndUniqueIdCannotBeNullWhenCalledByNickname() {
//    User user = new UserManagerImpl(this.plugin, this.messages, this.config, this.server).getUser("examplePlayer").get();
//
//    assertNotNull(user.getUniqueId());
//    assertNotNull(user.getName());
//  }

  @Test(expected = NullPointerException.class)
  public void addUserMethodShouldThrowNullPointerExceptionWhenUserIsNull() {
    new UserManagerImpl(this.plugin, this.messages, this.config, this.server).addUser(null);
  }

  @Test(expected = NullPointerException.class)
  public void invalidateUserMethodShouldThrowNullPointerExceptionWhenUserIsNull() {
    new UserManagerImpl(this.plugin, this.messages, this.config, this.server).removeUser(null);
  }

  @Test(expected = NullPointerException.class)
  public void getUserMethodShouldThrowNullPointerExceptionWhenPlayerNameIsNull() {
    new UserManagerImpl(this.plugin, this.messages, this.config, this.server).getUser((String) null).get();
  }

  @Test(expected = NullPointerException.class)
  public void getUserMethodShouldThrowNullPointerExceptionWhenUniqueIdIsNull() {
    new UserManagerImpl(this.plugin, this.messages, this.config, this.server).getUser((UUID) null).get();
  }

}
