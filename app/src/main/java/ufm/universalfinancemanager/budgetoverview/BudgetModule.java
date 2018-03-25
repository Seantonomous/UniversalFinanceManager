package ufm.universalfinancemanager.budgetoverview;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;


/**
 * Created by Areeba on 3/24/2018.
 */
@Module
public abstract class BudgetModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract BudgetFragment budgetFragment();

    @ActivityScoped
    @Binds
    abstract BudgetContract.Presenter budgetPresenter(BudgetPresenter presenter);
}
