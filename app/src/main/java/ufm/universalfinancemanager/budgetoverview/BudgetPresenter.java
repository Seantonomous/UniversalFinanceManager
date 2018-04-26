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
        for(Budget b: mUser.getBudgets()) {
           // this.spent = 0.0;
            this.t = new ArrayList<>();
            loadTransactions(b.getStartDate(), b.getEndDate(),b.getCat());
            double sum = 0.0;
            for (Transaction t : this.t) {
                sum += t.getAmount();
            }
            b.setCurrentValue(sum);
        }
        ArrayList<Budget> budgets = mUser.getBudgets();
        mBudgetView.showBudgets(budgets);
    }

    private void loadTransactions(Date s, Date e, final String cat) {
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
                processTransactions(transactions,cat);
                // processCategory(category);

            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });

    }

    public void processTransactions(List<Transaction> transactions, String cat) {
       // String category = cat.toString();
        for(Transaction t: transactions) {
            if(t.getCategory().compareTo(cat) == 0 && t.getFlow().compareTo(Flow.OUTCOME) == 0) {
                this.t.add(t);
            }
        }
    }

   // @Override
    public void editBudget(String budgetId) {

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
