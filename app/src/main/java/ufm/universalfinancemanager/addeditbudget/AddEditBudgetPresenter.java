package ufm.universalfinancemanager.addeditbudget;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.entity.Category;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.db.entity.Budget;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.util.EspressoIdlingResource;

/**
 * Created by Areeba on 2/23/2018.
 */

public class AddEditBudgetPresenter implements AddEditBudgetContract.Presenter{
    private User mUser;
    @Nullable
    private AddEditBudgetContract.View mAddEditBudgetview = null;
    private final UserRepository mUserRepository;
    private Category category1;
    private String cat;
    //private Date d1;
    //private Date d2;
    final List<Category> expense = new ArrayList<>();
    List<Transaction> transactions;
    //private final String mBudgetId;
    private String budgetName;

    @Inject
    AddEditBudgetPresenter(User user, UserRepository transactionRepository){//,@Nullable String id) {
        mUser = user;
        mUserRepository = transactionRepository;
        //mBudgetId = id;
    }

    @Override
    public void saveBudget(String name, String category, double amount, Date startdate, Date enddate) {
        this.cat = category;
       // this.budgetName = name;
       // for(Budget b : mUser.getBudgets()) {
          // expense.add(b.getCategory());
        //}
            this.transactions = new ArrayList<>();
            loadTransactions(startdate, enddate, category);
            if (isNewBudget()) {
                createNewBudget(name, category, amount, startdate, enddate);
            }
            else {
                updateBudget(name, category, amount, startdate, enddate);
            }

            //}catch(RuntimeException e) {
       // }
        //}
        if (mAddEditBudgetview != null)
            mAddEditBudgetview.showLastActivity(true);

    }

    private void updateBudget(String name, String category, double amount, Date startdate, Date enddate) {
        this.budgetName = name;
        Budget b = mUser.getBudget(name);
        b.setAmount(amount);
        b.setCat(category);
        b.setStartDate(startdate);
        b.setEndDate(enddate);
        if (mAddEditBudgetview != null) {
            mAddEditBudgetview.showLastActivity(true);
        }
    }

    private void createNewBudget(String name, String category, double amount, Date startdate, Date enddate) {
        //this.budgetName = name;
        double sum = 0.0;
        for (Transaction t : this.transactions) {
            sum += t.getAmount();
        }
        Budget budget = new Budget(name, category, amount, sum, startdate, enddate);
        mUser.addBudget(budget);
        if (mAddEditBudgetview != null) {
            mAddEditBudgetview.showMessage("Budget successfully saved.");
            mAddEditBudgetview.showLastActivity(true);
        }
    }

    @Override
    public void loadTransactions(final Date startdate, final Date enddate, final String category) {
        //Performing database/network call, forbid ui test activity until it's done
        EspressoIdlingResource.increment();
        long currentDate = startdate.getTime();
        long pastDate = enddate.getTime();
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

    private void processTransactions(List<Transaction> transactions) {

        for(Transaction t: transactions) {
            if(t.getCategory().compareTo(this.cat) == 0 && t.getFlow().compareTo(Flow.OUTCOME) == 0) {
                this.transactions.add(t);
            }
        }
    }

    private void processCategory(String category1) {
        mUserRepository.getCategory(category1, new UserDataSource.GetCategoryCallback() {
            @Override
            public void onCategoryLoaded(Category category) {
                saveCategory(category);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void saveCategory(Category category) {
        this.category1 = category;
    }

    @Override
    public void getUpdatedCategories(Flow flow) {
       // mAddEditBudgetview.updateCategories(mUser.getIncomeCategories());
    }

   /* public void processTransactions(List<Transaction> transactions, String name, Category categoryName, double amount, Date startdate, Date enddate) {
        List<Transaction> budgetTransactionList = new ArrayList<>();
        double sum = 0.0;
        if(isNewBudget()) {
            if (name.length() > 25) {
                if (mAddEditBudgetview != null)
                    mAddEditBudgetview.showMessage("Budget name too long!");
            } else {
                try {
                    for (Transaction t : transactions) {
                        if ((t.getCategory() == categoryName.toString()) &&
                                t.getFlow() == (Flow.OUTCOME)) {
                            // or its greater or close to the end date {
                            budgetTransactionList.add(t);
                            sum = sum + t.getAmount();

                        }
                    }
                    Budget budget = new Budget(name, categoryName, amount, sum, startdate, enddate);
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
                        if ((t.getCategory() == categoryName.toString()) &&
                                t.getFlow() == (Flow.OUTCOME)) {
                            // or its greater or close to the end date {
                            budgetTransactionList.add(t);
                            sum = sum + t.getAmount();

                        }
                    }
                    Budget budget = new Budget(name, categoryName, amount, sum, startdate, enddate);
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
            populateBudget();
        }
    }

    @Override
    public void dropView() {
        mAddEditBudgetview = null;
    }

    public boolean isNewBudget() {
       return budgetName == null;
       // return true;
    }

    public void populateBudget() {
       Budget b =  mUser.getBudget(budgetName);
       mAddEditBudgetview.populateExistingFields(b.getName(), b.getCategory(), b.getAmount(), b.getStartDate(), b.getEndDate());
    }

}
