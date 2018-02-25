package ufm.universalfinancemanager;

import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.di.DaggerAppComponent;

/**
 * Created by smh7 on 12/11/17.
 * This is the main application, this is responsible for pulling all of the dagger modules together
 */

public class ufmApplication extends DaggerApplication {
    @Inject
    TransactionRepository transactionRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @VisibleForTesting
    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }
}
