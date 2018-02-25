package ufm.universalfinancemanager.addeditcategory;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.di.FragmentScoped;

/**
 * Created by smh7 on 2/7/18.
 */

@Module
public abstract class AddEditCategoryModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddEditCategoryFragment addEditCategoryFragment();

    @ActivityScoped
    @Binds
    abstract AddEditCategoryContract.Presenter addEditCategoryPresenter(AddEditCategoryPresenter presenter);
}
