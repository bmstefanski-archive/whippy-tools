package pl.bmstefanski.tools.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.manager.UserManagerImpl;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    @Mock
    private Tools plugin;

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
