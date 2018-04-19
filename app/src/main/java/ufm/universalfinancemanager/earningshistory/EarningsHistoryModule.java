package ufm.universalfinancemanager.earningshistory;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by Faiz on 3/27/2018.
 */

@Module
public abstract class EarningsHistoryModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract EarningsHistoryFragment earningsHistoryFragment();

    @ActivityScoped
    @Binds
    abstract EarningsHistoryContract.Presenter earningsHistoryPresenter(EarningsHistoryPresenter presenter);
}
