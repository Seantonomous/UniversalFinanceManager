package ufm.universalfinancemanager.budgetoverview;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import ufm.universalfinancemanager.networth.NetworthContract;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetPresenter implements BudgetContract.Presenter {
    @Nullable
    BudgetContract.View mBudgetView;

    @Inject
    BudgetPresenter(){
    }


    @Override
    public void result() {

    }

    @Override
    public void loadBudgets() {

    }

    @Override
    public void takeView(BudgetContract.View view) {
        this.mBudgetView = view;
    }

    @Override
    public void dropView() {
        this.mBudgetView = null;
    }
}
