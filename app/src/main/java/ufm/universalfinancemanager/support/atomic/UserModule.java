package ufm.universalfinancemanager.support.atomic;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.Flow;

/**
 * Created by smh7 on 1/7/18.
 */

@Module
public class UserModule {

    @Singleton
    @Provides
    static User provideUser() {
        User testUser = new User("TestUser");
        testUser.addAccount(new Account("Checking", AccountType.CHECKING, 0, new Date()));
        testUser.addCategory(new Category("Test Category 1", Flow.OUTCOME));
        testUser.addCategory(new Category("Test Category 2", Flow.INCOME));

        return testUser;
    }
}
