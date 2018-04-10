package ufm.universalfinancemanager.addeditcategory;

import android.support.annotation.Nullable;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
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

    @ActivityScoped
    @Provides
    @Nullable
    static String provideCategoryName(AddEditCategoryActivity addEditCategoryActivity) {
        return addEditCategoryActivity.getIntent().getStringExtra("CATEGORY_NAME");
    }
}
