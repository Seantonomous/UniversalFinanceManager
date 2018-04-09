package ufm.universalfinancemanager.db;

/**
 * Created by smh7 on 12/11/17.
 */

import android.app.Application;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Room;


import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.source.FakeUserRemoteDataSource;
import ufm.universalfinancemanager.db.source.Local;
import ufm.universalfinancemanager.db.source.Remote;
import ufm.universalfinancemanager.db.source.local.AccountDao;
import ufm.universalfinancemanager.db.source.local.CategoryDao;
import ufm.universalfinancemanager.db.source.local.TransactionDao;
import ufm.universalfinancemanager.db.source.local.UserDatabase;
import ufm.universalfinancemanager.db.source.local.UserLocalDataSource;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.util.AppExecutors;
import ufm.universalfinancemanager.util.DiskIOThreadExecutor;

/**
 * This is used by Dagger to inject the required arguments into the .
 */
@Module
abstract public class UserRepositoryModule {

    private static final int THREAD_COUNT = 3;

    private UserRepository userRepository;

    @Singleton
    @Binds
    @Local
    abstract UserDataSource provideTransactionLocalDataSource(UserLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract UserDataSource provideTransactionRemoteDataSource(FakeUserRemoteDataSource dataSource);

    @Singleton
    @Provides
    static UserDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "user.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static TransactionDao provideTransactionDao(UserDatabase db) {
        return db.transactionDao();
    }

    @Singleton
    @Provides
    static AccountDao provideAccountsDao(UserDatabase db) { return db.accountDao(); }

    @Singleton
    @Provides
    static CategoryDao provideCategoryDao(UserDatabase db) {return db.categoryDao(); }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
