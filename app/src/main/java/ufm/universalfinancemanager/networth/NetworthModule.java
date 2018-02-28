package ufm.universalfinancemanager.networth;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by smh7 on 2/28/18.
 */

@Module
public abstract class NetworthModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract NetworthFragment networthFragment();

    @ActivityScoped
    @Binds
    abstract NetworthContract.Presenter networthPresenter(NetworthPresenter presenter);
}
