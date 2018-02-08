package ufm.universalfinancemanager.addeditcategory;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.Category;
import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by smh7 on 2/7/18.
 */

public class AddEditCategoryPresenter implements AddEditCategoryContract.Presenter {

    private User mUser;

    @Nullable
    private AddEditCategoryContract.View mAddEditCategoryview = null;

    @Inject
    AddEditCategoryPresenter(User user) {
        mUser = user;
    }

    @Override
    public void saveCategory(String name, Flow flow) {
        if(name.length() > 25) {
            if(mAddEditCategoryview != null)
                mAddEditCategoryview.showMessage("Category name too long!");
        }else if(mUser.hasCategory(name))
            if(mAddEditCategoryview !=null)
                mAddEditCategoryview.showMessage("A category by that name already exists.");
        else {
            mUser.addCategory(new Category(name, flow));
            mAddEditCategoryview.showLastActivity(true);
        }
    }

    @Override
    public void takeView(AddEditCategoryContract.View view) {
        mAddEditCategoryview = view;
    }

    @Override
    public void dropView() {
        mAddEditCategoryview = null;
    }
}
