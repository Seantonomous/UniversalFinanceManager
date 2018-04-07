package ufm.universalfinancemanager.addeditbudget;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.Budget;
import ufm.universalfinancemanager.support.atomic.Category;
import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by Areeba on 2/23/2018.
 */

public class AddEditBudgetPresenter implements AddEditBudgetContract.Presenter{
    private User mUser;
    @Nullable
    private AddEditBudgetContract.View mAddEditBudgetview = null;

   /* @Override
    public void saveBudget(String name, Category category, double amount) {
        if(name.length() > 25) {
            if (mAddEditBudgetview != null)
                mAddEditBudgetview.showMessage("Budget name too long!");
        }
        else {
            try{
                mUser.addBudget(new Budget(name, category, amount));
                if(mAddEditBudgetview != null)
                    mAddEditBudgetview.showMessage("Budget successfully saved.");
            }
            catch(RuntimeException e){
                if(mAddEditBudgetview != null)
                    mAddEditBudgetview.showMessage("Error saving budget, Budget with that name already exists!");
                return;
            }
            if(mAddEditBudgetview != null)
                mAddEditBudgetview.showLastActivity(true);
        }
    }*/
    @Override
    public void saveBudget(String name, String categoryName, double amount) {
        Budget budget = new Budget(name, categoryName == null ? null : mUser.getCategory(categoryName), amount);
        mUser.addBudget(budget);
        if(mAddEditBudgetview != null) {
            mAddEditBudgetview.showMessage("Budget successfully saved.");
            mAddEditBudgetview.showLastActivity(true);
        }
    }
    @Override
    public void deleteBudget() {
    }

    @Override
    public void takeView(AddEditBudgetContract.View view) {
        mAddEditBudgetview = view;
        //get stuff from database
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
