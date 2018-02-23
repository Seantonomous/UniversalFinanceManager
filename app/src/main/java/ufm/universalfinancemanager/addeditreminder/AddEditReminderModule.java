package ufm.universalfinancemanager.addeditreminder;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryContract;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryFragment;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryPresenter;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by Areeba on 2/17/2018.
 */
@Module
public abstract class AddEditReminderModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddEditReminderFragment addEditReminderFragment();

    @ActivityScoped
    @Binds
    abstract AddEditReminderContract.Presenter addEditReminderPresenter(AddEditReminderPresenter presenter);
}
