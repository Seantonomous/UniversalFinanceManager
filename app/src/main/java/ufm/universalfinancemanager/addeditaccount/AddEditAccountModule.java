package ufm.universalfinancemanager.addeditaccount;

import android.support.annotation.Nullable;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by smh7 on 1/23/18.
 */

@Module
public abstract class AddEditAccountModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddEditAccountFragment addEditAccountFragment();

    @ActivityScoped
    @Binds
    abstract AddEditAccountContract.Presenter addEditAccountPresenter(AddEditAccountPresenter presenter);

    @ActivityScoped
    @Provides
    @Nullable
    static String provideAccountName(AddEditAccountActivity activity) {
        return activity.getIntent().getStringExtra("ACCOUNT_NAME");
    }
}
