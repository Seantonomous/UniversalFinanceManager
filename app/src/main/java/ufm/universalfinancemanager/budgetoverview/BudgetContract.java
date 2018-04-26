package ufm.universalfinancemanager.budgetoverview;

import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.db.entity.Budget;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetContract {
    public interface Presenter extends BasePresenter<BudgetContract.View> {
        void loadBudgets();
       // void editBudget(String budgetId);
        void takeView(BudgetContract.View view);

        void dropView();
    }
    interface View extends BaseView<BudgetContract.Presenter> {
       void showBudgets(List<Budget> budgets);
      // void showAddEditBudgets();
      // void showAddEditBudget(String id);
    }
}
