package ufm.universalfinancemanager.transactionhistory;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by smh7 on 12/11/17.
 */
@Module
public abstract class TransactionHistoryModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract TransactionHistoryFragment transactionHistoryFragment();

    @ActivityScoped
    @Binds
    abstract TransactionHistoryContract.Presenter transactionHistoryPresenter(TransactionHistoryPresenter presenter);
}
