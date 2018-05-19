package pl.bmstefanski.tools.manager;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.Bukkit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.impl.manager.UserManagerImpl;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

  @Mock
  private Tools plugin;

  @BeforeClass
  public static void initialize() {
    ServerMock serverMock = new ServerMock();
    Bukkit.setServer(serverMock);
  }

  @Test(expected = UnimplementedOperationException.class)
  // waiting for implementation https://github.com/seeseemelk/MockBukkit/blob/master/src/main/java/be/seeseemelk/mockbukkit/ServerMock.java#L876
  public void userNameAndUniqueIdCannotBeNullWhenCalledByUniqueId() {
    UUID uniqueId = UUID.randomUUID();
    User user = new UserManagerImpl(this.plugin).getUser(uniqueId);

    assertNotNull(user.getUUID());
    assertNotNull(user.getName());
  }

  @Test(expected = UnimplementedOperationException.class)
  // waiting for implementation https://github.com/seeseemelk/MockBukkit/blob/master/src/main/java/be/seeseemelk/mockbukkit/ServerMock.java#L876
  public void userNameAndUniqueIdCannotBeNullWhenCalledByNickname() {
    User user = new UserManagerImpl(this.plugin).getUser("examplePlayer");

    assertNotNull(user.getUUID());
    assertNotNull(user.getName());
  }

  @Test(expected = NullPointerException.class)
  public void addUserMethodShouldThrowNullPointerExceptionWhenUserIsNull() {
    new UserManagerImpl(this.plugin).addUser(null);
  }

  @Test(expected = NullPointerException.class)
  public void invalidateUserMethodShouldThrowNullPointerExceptionWhenUserIsNull() {
    new UserManagerImpl(this.plugin).invalidateUser(null);
  }

  @Test(expected = NullPointerException.class)
  public void getUserMethodShouldThrowNullPointerExceptionWhenPlayerNameIsNull() {
    new UserManagerImpl(this.plugin).getUser((String) null);
  }

  @Test(expected = NullPointerException.class)
  public void getUserMethodShouldThrowNullPointerExceptionWhenUniqueIdIsNull() {
    new UserManagerImpl(this.plugin).getUser((UUID) null);
  }

}
