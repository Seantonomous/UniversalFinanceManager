package ufm.universalfinancemanager.addeditbudget;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by Areeba on 2/23/2018.
 */

@Module
public abstract class AddEditBudgetModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddEditBudgetFragment addEditBudgetFragment();

    @ActivityScoped
    @Binds
    abstract AddEditBudgetContract.Presenter addEditBudgetPresenter(AddEditBudgetPresenter presenter);
}
