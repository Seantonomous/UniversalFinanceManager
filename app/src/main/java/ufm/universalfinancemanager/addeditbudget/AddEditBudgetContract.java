package ufm.universalfinancemanager.addeditbudget;

import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.Category;

/**
 * Created by Areeba on 2/23/2018.
 */

public interface AddEditBudgetContract {
    interface View extends BaseView<AddEditBudgetContract.Presenter> {

        void showLastActivity(boolean success);
        void updateCategories(@Nullable List<Category> categories);
        void showMessage(String message);

    }

    interface Presenter extends BasePresenter<AddEditBudgetContract.View> {
        void loadTransactions(String name, String category, double amount, Date startdate, Date enddate);
        void deleteBudget();
        void takeView(AddEditBudgetContract.View v);
        void dropView();

    }
}
