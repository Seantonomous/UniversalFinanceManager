package ufm.universalfinancemanager.reminderhistory;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by simranjeetkaur on 19/04/18.
 */

@Module
public abstract class ReminderHistoryModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract ReminderHistoryFragment reminderHistoryFragment();

    @ActivityScoped
    @Binds
    abstract ReminderHistoryContract.Presenter reminderHistoryPresenter(ReminderHistoryPresenter presenter);
}
