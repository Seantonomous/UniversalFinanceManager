package ufm.universalfinancemanager.home;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

@Module
public abstract class HomeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeFragmentChart1 homeFragment1();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeFragmentChart2 homeFragment2();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeFragmentChart3 homeFragment3();

    @ActivityScoped
    @Binds
    abstract HomeContract.Presenter homePresenter(HomePresenter presenter);
}
