package ufm.universalfinancemanager.budgetoverview;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import ufm.universalfinancemanager.networth.NetworthContract;
import ufm.universalfinancemanager.support.atomic.Budget;
import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetPresenter implements BudgetContract.Presenter {
    @Nullable
    BudgetContract.View mBudgetView;
    private User mUser;

    @Inject
    BudgetPresenter(User mUser){
        this.mUser = mUser;
    }

    @Override
    public void loadBudgets() {
        mBudgetView.showBudgets(mUser.getBudgets());
    }

    @Override
    public void takeView(BudgetContract.View view) {
        this.mBudgetView = view;
        loadBudgets();
    }

    @Override
    public void dropView() {
        this.mBudgetView = null;
    }

}
