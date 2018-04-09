package ufm.universalfinancemanager.addeditbudget;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by Areeba on 2/23/2018.
 */

public class AddEditBudgetPresenter implements AddEditBudgetContract.Presenter{
    private User mUser;
    @Nullable
    private AddEditBudgetContract.View mAddEditBudgetview = null;

    @Override
    public void takeView(AddEditBudgetContract.View view) {
        mAddEditBudgetview = view;
    }

    @Override
    public void dropView() {
        mAddEditBudgetview = null;
    }

    @Inject
    AddEditBudgetPresenter(User user) {
        mUser = user;
    }
}
