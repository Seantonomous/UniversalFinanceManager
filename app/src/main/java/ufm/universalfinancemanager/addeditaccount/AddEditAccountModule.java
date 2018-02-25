package ufm.universalfinancemanager.addeditaccount;

import dagger.Binds;
import dagger.Module;
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
}
