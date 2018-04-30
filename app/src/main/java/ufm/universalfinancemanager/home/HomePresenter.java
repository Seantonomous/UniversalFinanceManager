package ufm.universalfinancemanager.home;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.entity.Category;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.db.entity.Budget;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.util.EspressoIdlingResource;

public class HomePresenter implements HomeContract.Presenter {

    private final UserRepository mTransactionRepository;
    private final User mUser;

    @Nullable
    private HomeContract.View mHomeView = null;

    private static final int CHART1 = 1;
    private static final int CHART2 = 2;
    private static final int CHART3 = 3;
    private int mCurrentChart;

    private Calendar calendar = Calendar.getInstance();

    @Inject
    public HomePresenter(UserRepository transactionRepository, User user) {
        mUser = user;
        mTransactionRepository = transactionRepository;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadTransactions() {
        //Performing database/network call, forbid ui test activity until it's done
        EspressoIdlingResource.increment();

        mTransactionRepository.getTransactions(new UserDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                processTransactions(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });
    }

    private void loadTransactionRange() {
        EspressoIdlingResource.increment();
        Calendar cal2 = (Calendar)calendar.clone();
        cal2.add(Calendar.DATE, -30);

        mTransactionRepository.getTransactionsInDateRange(cal2.getTimeInMillis(),
                calendar.getTimeInMillis(), new UserDataSource.LoadTransactionsCallback() {
                    @Override
                    public void onTransactionsLoaded(List<Transaction> transactions) {
                        processCategories(transactions);
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });

        EspressoIdlingResource.decrement();
    }

    private void processCategories(List<Transaction> transactions) {
        ArrayList<Category> categories = mUser.getExpenseCategories();
        ArrayList<HomeDataCategory> data = new ArrayList<>();
        int[] spending = new int[categories.size()];

        //No categories to report on
        if(categories.size() == 0)
            return;

        for(Transaction transaction : transactions) {
            if(transaction.getFlow() == Flow.OUTCOME) {
                int index = categories.indexOf(mUser.getCategory(transaction.getCategory()));
                spending[index] += transaction.getAmount();
            }
        }

        for(Category category : categories) {
            if(spending[categories.indexOf(category)] > 0)
                data.add(new HomeDataCategory(category.getName(),
                        spending[categories.indexOf(category)]));
        }

        if(mHomeView != null)
            mHomeView.populateCategories(data);
    }

    private void processBudgets() {
        ArrayList<Budget> budgets = mUser.getBudgets();
        ArrayList<HomeDataBudgetSpend> data = new ArrayList<>();

        for(Budget budget : budgets) {
            if (budget.getStartDate().getTime() < calendar.getTimeInMillis() &&
                    budget.getEndDate().getTime() > calendar.getTimeInMillis()) {

                data.add(new HomeDataBudgetSpend(
                        budget.getCat(),
                        (float) budget.getCurrentValue(),
                        (float) budget.getAmount()));
            }
        }

        if(mHomeView != null)
            mHomeView.populateBudgets(data);
    }

    //    @Override
    private void processTransactions(List<Transaction> transactions) {
        if(mHomeView != null)
            mHomeView.populateList(transactions);
    }

    @Override
    public void takeView(HomeContract.View v) {
        //Stub
    }

    @Override
    public void takeView(HomeContract.View v, int type) {
        if(v == null)
            return;

        mHomeView = v;
        mCurrentChart = type;

        if(mCurrentChart == CHART2)
            loadTransactions();
        else if(mCurrentChart == CHART3)
            loadTransactionRange();
        else if(mCurrentChart == CHART1)// chart 1
            processBudgets();
    }

    @Override
    public void dropView() {
        mHomeView = null;
    }

}