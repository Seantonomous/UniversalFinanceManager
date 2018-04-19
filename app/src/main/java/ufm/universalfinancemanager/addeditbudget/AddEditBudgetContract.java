package ufm.universalfinancemanager.addeditbudget;

import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.db.entity.Account;
import ufm.universalfinancemanager.db.entity.Category;

/**
 * Created by Areeba on 2/23/2018.
 */

public interface AddEditBudgetContract {
    interface View extends BaseView<AddEditBudgetContract.Presenter> {

        void showLastActivity(boolean success);
        void setupFragmentContent(boolean editing);
        void updateCategories(@Nullable List<Category> categories);
        void populateExistingFields(String name, Category category, Double amount, Date startDate, Date endDate);
        void showMessage(String message);

    }

    interface Presenter extends BasePresenter<AddEditBudgetContract.View> {
        void saveBudget(String name, String category, double amount, Date startdate, Date enddate);
        void loadTransactions(Date date1, Date date2, String str);
        void getUpdatedCategories(Flow flow);
        void deleteBudget();
        void takeView(AddEditBudgetContract.View v);
        void dropView();

    }
}
