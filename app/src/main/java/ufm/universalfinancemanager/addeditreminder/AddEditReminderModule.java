package ufm.universalfinancemanager.addeditreminder;

import android.support.annotation.Nullable;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryContract;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryFragment;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryPresenter;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;
import android.support.annotation.Nullable;


/**
 * Created by Areeba on 2/17/2018.
 */
@Module
public abstract class AddEditReminderModule {

    @Provides
    @ActivityScoped
    @Nullable
    static String provideReminderName(AddEditReminderActivity activity) {
        return activity.getIntent().getStringExtra("EDIT_REMINDER_ID");
    }

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddEditReminderFragment addEditReminderFragment();

    @ActivityScoped
    @Binds
    abstract AddEditReminderContract.Presenter addEditReminderPresenter(AddEditReminderPresenter presenter);
}