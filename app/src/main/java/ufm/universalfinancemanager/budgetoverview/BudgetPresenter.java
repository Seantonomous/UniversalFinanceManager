package ufm.universalfinancemanager.budgetoverview;

import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.entity.Budget;
import ufm.universalfinancemanager.db.entity.Category;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.util.EspressoIdlingResource;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetPresenter implements BudgetContract.Presenter {
    @Nullable
    BudgetContract.View mBudgetView;
    private User mUser;
    private final UserRepository mUserRepository;
    private double spent;
    private ArrayList<Transaction> t;

    @Inject
    BudgetPresenter(User mUser, UserRepository userRepository){
        this.mUserRepository = userRepository;
        this.mUser = mUser;
    }

    @Override
    public void loadBudgets() {
        //ArrayList<Budget> budgets = mUser.getBudgets();
        //ArrayList<Budget> newBudgets = new ArrayList<>();
        this.t = new ArrayList<>();
        loadTransactions(new Date(0), new Date());
    }

    private void loadTransactions(Date s, Date e) {
        EspressoIdlingResource.increment();
        long currentDate = s.getTime();
        long pastDate = e.getTime();
        mUserRepository.getTransactionsInDateRange(currentDate, pastDate, new UserDataSource.LoadTransactionsCallback() {

            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }
                processTransactions(transactions);
                // processCategory(category);

            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });

    }

    public void processTransactions(List<Transaction> transactions) {
       // String category = cat.toString();
        for(Transaction trans: transactions) {
            if(trans.getCategory() == null)
                continue;
            else
                this.t.add(trans);
        }

        processBudgets();
    }

    public void processBudgets() {
        for(Budget b: mUser.getBudgets()) {
            double sum = 0.0;

            for (Transaction t : this.t) {
                if(t.getDate().getTime() > b.getStartDate().getTime() &&
                        t.getDate().getTime() < b.getEndDate().getTime() &&
                        t.getCategory().compareTo(b.getCat()) == 0)
                    sum += t.getAmount();
            }
            b.setCurrentValue(sum);
        }

        ArrayList<Budget> budgets = mUser.getBudgets();
        mBudgetView.showBudgets(budgets);
    }

    @Override
    public void addBudget(){
        if(mBudgetView != null) {
            mBudgetView.showAddEditBudgets();
        }
    }
    @Override
    public void editBudget(String budgetId) {
        this.mBudgetView.showAddEditBudget(budgetId);
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
