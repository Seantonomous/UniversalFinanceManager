package ufm.universalfinancemanager.addeditbudget;

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

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter<AddEditBudgetContract.View> {
        void saveBudget(String name, String category, double amount, double currentValue);

        void deleteBudget();

        void takeView(AddEditBudgetContract.View v);

        void dropView();
    }
}
