package ufm.universalfinancemanager.support.atomic;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ufm.universalfinancemanager.db.UserRepository;
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
    static User provideUser(UserRepository userRepository) {
        User testUser = new User("user", userRepository);
/*
        if(!testUser.hasAccount("Checking"))
            testUser.addAccount(new Account("Checking", AccountType.CHECKING, 1089, new Date()));
        if(!testUser.hasAccount("Checking2"))
            testUser.addAccount(new Account("Checking2", AccountType.CHECKING, 19309, new Date()));
        if(!testUser.hasAccount("CreditCard1"))
            testUser.addAccount(new Account("CreditCard1", AccountType.CREDIT_CARD, 252, new Date()));
        if(!testUser.hasAccount("CreditCard2"))
            testUser.addAccount(new Account("CreditCard2", AccountType.CREDIT_CARD, 3945, new Date()));
        if(!testUser.hasCategory("Test Category 1"))
            testUser.addCategory(new Category("Test Category 1", Flow.OUTCOME));
        if(!testUser.hasCategory("Test Category 2"))
            testUser.addCategory(new Category("Test Category 2", Flow.INCOME));
        */
        return testUser;
    }
}
