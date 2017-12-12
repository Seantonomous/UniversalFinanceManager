package ufm.universalfinancemanager.db.source;

/**
 * Created by smh7 on 12/11/17.
 */

import android.app.Application;
import android.arch.persistence.room.Room;


import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.source.local.TransactionDao;
import ufm.universalfinancemanager.db.source.local.TransactionDatabase;
import ufm.universalfinancemanager.db.source.local.TransactionLocalDataSource;
import ufm.universalfinancemanager.util.AppExecutors;
import ufm.universalfinancemanager.util.DiskIOThreadExecutor;

/**
 * This is used by Dagger to inject the required arguments into the .
 */
@Module
abstract public class TransactionRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Binds
    @Local
    abstract TransactionDataSource provideTransactionLocalDataSource(TransactionLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract TransactionDataSource provideTransactionRemoteDataSource(FakeTransactionRemoteDataSource dataSource);

    @Singleton
    @Provides
    static TransactionDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), TransactionDatabase.class, "Transactions.db")
                .build();
    }

    @Singleton
    @Provides
    static TransactionDao provideTasksDao(TransactionDatabase db) {
        return db.transactionDao();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
