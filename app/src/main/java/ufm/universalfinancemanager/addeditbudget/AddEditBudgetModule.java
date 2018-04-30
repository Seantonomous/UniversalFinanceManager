package ufm.universalfinancemanager.addeditbudget;

import android.support.annotation.Nullable;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionFragment;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by Areeba on 2/23/2018.
 */

@Module
public abstract class AddEditBudgetModule {
    @Provides
    @ActivityScoped
    @Nullable
    static String provideBudgetName(AddEditBudgetActivity activity) {
        return activity.getIntent().getStringExtra("EDIT_BUDGET_ID");
    }

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddEditBudgetFragment addEditBudgetFragment();

    @ActivityScoped
    @Binds
    abstract AddEditBudgetContract.Presenter addEditBudgetPresenter(AddEditBudgetPresenter presenter);
}
