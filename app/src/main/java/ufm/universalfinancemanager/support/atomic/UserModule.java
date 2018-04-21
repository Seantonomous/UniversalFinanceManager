package ufm.universalfinancemanager.support.atomic;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.Flow;

import static java.util.stream.IntStream.range;

/**
 * Created by smh7 on 1/7/18.
 */

@Module
public class UserModule {

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        testUser.addReminder(new Reminder("reminder1", new Time(10, 10, 10), new Date(), "hello this is a text note"));

        return testUser;
    }
}
