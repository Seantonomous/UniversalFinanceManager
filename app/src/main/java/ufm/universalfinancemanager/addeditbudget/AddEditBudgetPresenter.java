package ufm.universalfinancemanager.addeditbudget;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.Budget;
import ufm.universalfinancemanager.support.atomic.Category;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.util.EspressoIdlingResource;

/**
 * Created by Areeba on 2/23/2018.
 */

public class AddEditBudgetPresenter implements AddEditBudgetContract.Presenter{
    private User mUser;
    @Nullable
    private AddEditBudgetContract.View mAddEditBudgetview = null;
    private TransactionRepository mTransactionRepository;

    @Inject
    AddEditBudgetPresenter(User user, TransactionRepository transactionRepository) {
        mUser = user;
        mTransactionRepository = transactionRepository;
    }

    @Override
    public void loadTransactions(final String name, final String category, final double amount) {
        //Performing database/network call, forbid ui test activity until it's done
        EspressoIdlingResource.increment();

        mTransactionRepository.getTransactions(new TransactionDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                processTransactions(transactions, name, category,amount);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });
    }

    public void processTransactions(List<Transaction> transactions, String name, String categoryName, double amount) {
        List<Transaction> budgetTransactionList = new ArrayList<>();
        double sum = 0.0;
        for(Transaction t: transactions) {
            if((t.getCategory() == (categoryName == null ? null : mUser.getCategory(categoryName))) && t.getFlow() == Flow.INCOME) {
                budgetTransactionList.add(t);
                sum += t.getAmount();
            }
        }
        Budget budget = new Budget(name, categoryName == null ? null : mUser.getCategory(categoryName), amount, sum);
        mUser.addBudget(budget);
        if(mAddEditBudgetview != null) {
            mAddEditBudgetview.showMessage("Budget successfully saved.");
            mAddEditBudgetview.showLastActivity(true);
        }
    }
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
    public void makeBudget(String name, String categoryName, double amount) {
        double current = 0.0; //getTransactions(categoryName);
        Budget budget = new Budget(name, categoryName == null ? null : mUser.getCategory(categoryName), amount, current);
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

}
