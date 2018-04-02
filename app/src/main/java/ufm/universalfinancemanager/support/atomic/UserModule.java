package ufm.universalfinancemanager.support.atomic;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ufm.universalfinancemanager.db.entity.Account;
import ufm.universalfinancemanager.db.entity.Category;
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
        testUser.addAccount(new Account("Checking", AccountType.CHECKING, 1089, new Date()));
        testUser.addAccount(new Account("Checking2", AccountType.CHECKING, 19309, new Date()));
        testUser.addAccount(new Account("CreditCard1", AccountType.CREDIT_CARD, 252, new Date()));
        testUser.addAccount(new Account("CreditCard2", AccountType.CREDIT_CARD, 3945, new Date()));
        testUser.addCategory(new Category("Test Category 1", Flow.OUTCOME));
        testUser.addCategory(new Category("Test Category 2", Flow.INCOME));

        return testUser;
    }
}
