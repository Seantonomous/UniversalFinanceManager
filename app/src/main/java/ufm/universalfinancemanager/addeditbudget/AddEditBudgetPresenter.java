package ufm.universalfinancemanager.addeditbudget;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
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
    private final TransactionRepository mTransactionRepository;
    //private final String mBudgetId;
    private String budgetName;

    @Inject
    AddEditBudgetPresenter(User user, TransactionRepository transactionRepository){//, @Nullable String id) {
        mUser = user;
        mTransactionRepository = transactionRepository;
        //mBudgetId = id;
    }

    @Override
    public void loadTransactions(final String name, final String category, final double amount, final Date startdate, final Date enddate) {
        //Performing database/network call, forbid ui test activity until it's done
        EspressoIdlingResource.increment();
        budgetName = name;
        long currentDate = startdate.getTime();
        long pastDate = enddate.getTime();
        mTransactionRepository.getTransactionsInDateRange(currentDate, pastDate, new TransactionDataSource.LoadTransactionsCallback() {

            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                processTransactions(transactions, name, category,amount,startdate, enddate);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });
        /*mTransactionRepository.getTransactions(currentDate, pastDate, new TransactionDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                processTransactions(transactions, name, category,amount,startdate, enddate);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });*/
    }

    @Override
    public void getUpdatedCategories(Flow flow) {

    }

    public void processTransactions(List<Transaction> transactions, String name, String categoryName, double amount, Date startdate, Date enddate) {
        List<Transaction> budgetTransactionList = new ArrayList<>();
        double sum = 0.0;
        Category c = mUser.getCategory(categoryName);
        if(isNewBudget()) {
            if (name.length() > 25) {
                if (mAddEditBudgetview != null)
                    mAddEditBudgetview.showMessage("Budget name too long!");
            } else {
                try {
                    for (Transaction t : transactions) {
                        if ((t.getCategory() == c) &&
                                t.getFlow() == (Flow.OUTCOME)) {
                            // or its greater or close to the end date {
                            budgetTransactionList.add(t);
                            sum = sum + t.getAmount();

                        }
                    }
                    Budget budget = new Budget(name, c, amount, sum, startdate, enddate);
                    mUser.addBudget(budget);
                    if (mAddEditBudgetview != null) {
                        mAddEditBudgetview.showMessage("Budget successfully saved.");
                        mAddEditBudgetview.showLastActivity(true);
                    }
                } catch (RuntimeException e) {
                    if (mAddEditBudgetview != null)
                        mAddEditBudgetview.showMessage("Error saving budget, Budget with that name already exists!");
                    return;
                }
                if (mAddEditBudgetview != null)
                    mAddEditBudgetview.showLastActivity(true);
            }
        }
        else {
            if (name.length() > 25) {
                if (mAddEditBudgetview != null)
                    mAddEditBudgetview.showMessage("Budget name too long!");
            } else {
                try {
                    for (Transaction t : transactions) {
                        if ((t.getCategory() == c) &&
                                t.getFlow() == (Flow.OUTCOME)) {
                            // or its greater or close to the end date {
                            budgetTransactionList.add(t);
                            sum = sum + t.getAmount();

                        }
                    }
                    Budget budget = new Budget(name, c, amount, sum, startdate, enddate);
                    mUser.addBudget(budget);
                    if (mAddEditBudgetview != null) {
                        mAddEditBudgetview.showMessage("Budget successfully saved.");
                        mAddEditBudgetview.showLastActivity(true);
                       // mAddEditBudgetview.populateExistingFields(budget.getName(), budget.getCategory(), budget.getAmount(), budget.getCurrentValue(), budget.getStartDate(), budget.getEndDate());
                    }
                } catch (RuntimeException e) {
                    if (mAddEditBudgetview != null)
                        mAddEditBudgetview.showMessage("Error saving budget, Budget with that name already exists!");
                    return;
                }
                if (mAddEditBudgetview != null)
                    mAddEditBudgetview.showLastActivity(true);
            }
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
    public void deleteBudget() {
        mUser.deleteBudget(budgetName);
    }

    @Override
    public void takeView(AddEditBudgetContract.View view) {
        mAddEditBudgetview = view;
        mAddEditBudgetview.updateCategories(mUser.getExpenseCategories());
        if(view == null)
            return;
        if(isNewBudget()) {
            mAddEditBudgetview.setupFragmentContent(false);
        }
        else{
            mAddEditBudgetview.setupFragmentContent(true);
        }
    }

    @Override
    public void dropView() {
        mAddEditBudgetview = null;
    }

    public boolean isNewBudget() {
       // return mBudgetId == null;
        return true;
    }
}
