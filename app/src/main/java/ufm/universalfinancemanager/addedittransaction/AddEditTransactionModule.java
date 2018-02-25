package ufm.universalfinancemanager.addedittransaction;

import android.support.annotation.Nullable;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by smh7 on 12/14/17.
 */

@Module
public abstract class AddEditTransactionModule {
    @Provides
    @ActivityScoped
    @Nullable
    static String provideTransactionId(AddEditTransactionActivity activity) {
        return activity.getIntent().getStringExtra(AddEditTransactionFragment.ARGUMENT_EDIT_TRANSACTION_ID);
    }
/*
    @Provides
    @ActivityScoped
    static boolean provideStatusDataMissing(AddEditTransactionActivity activity) {
        return activity.isDataMissing();
    }
*/
    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddEditTransactionFragment addEditTransactionFragment();

    @ActivityScoped
    @Binds
    abstract AddEditTransactionContract.Presenter transactionPresenter(AddEditTransactionPresenter presenter);
}
