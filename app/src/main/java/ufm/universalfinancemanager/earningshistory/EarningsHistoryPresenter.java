package ufm.universalfinancemanager.earningshistory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.annotation.Nullable;
import javax.inject.Inject;

import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.atomic.Category;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.util.EspressoIdlingResource;


/**
 * Created by Faiz on 2/26/2018.
 */

@ActivityScoped
public class EarningsHistoryPresenter implements EarningsHistoryContract.Presenter {
    private final TransactionRepository mTransactionRepository;
    private User mUser;
    private List<EarningsHistoryListItem> sortedList;

    @Nullable
    EarningsHistoryContract.View mEarningsHistoryView;

    @Inject
    EarningsHistoryPresenter(TransactionRepository transactionRepository, User user) {
        mTransactionRepository = transactionRepository;
        mUser = user;
    }

    @Override
    public void loadTransactionsInDateRange() {
        //Performing database/network call, forbid ui test activity until it's done
        EspressoIdlingResource.increment();

        Calendar cal = Calendar.getInstance();

        Date a = cal.getTime();
        long currentDate = a.getTime();

        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        Date b = cal.getTime();
        long pastDate = b.getTime();


        mTransactionRepository.getTransactionsInDateRange(currentDate, pastDate, new TransactionDataSource.LoadTransactionsCallback() {

            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                sortTransactions(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });
    }

    public void sortTransactions(List<Transaction> transactions){

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date lastMonthDate = cal.getTime();                                                             //Gets cutoff date between last month and current month

        sortedList = new ArrayList<>();
        ArrayList<Category> incomeCategories = mUser.getIncomeCategories();                             //Get income categories list from user
        ArrayList<Category> expenseCategories = mUser.getExpenseCategories();                           //Get expense categories list from user

        double[] thisMonthCounter = new double[incomeCategories.size() + expenseCategories.size()];     //Will hold current-month amount totals for each category
        double[] lastMonthCounter = new double[incomeCategories.size() + expenseCategories.size()];     //Will hold last-month amount totals for each category
        int i = 0;

        for (Category c : incomeCategories) {                                                           //Loop through income categories list

            sortedList.add(new EarningsHistoryListItem(c.getName(), Flow.INCOME, 0, 0));         //Create new list item for each category

            for (Transaction t : transactions) {                                                        //Loop through transactions

                if (t.getFlow() == Flow.INCOME) {                                                       //If transaction is an income type

                    if (t.getDate().before(lastMonthDate)) {                                            //If last month transaction

                        if (t.getCategory() == c)                                                       //If transaction's category matches current category
                            lastMonthCounter[i] += t.getAmount();                                       //Add transaction's amount to counter for this category
                    }
                    else {                                                                              //If current month transaction

                        if (t.getCategory() == c)                                                       //If transaction's category matches current category
                            thisMonthCounter[i] += t.getAmount();                                       //Add transaction's amount to counter for this category
                    }
                }

            }

            i++;                                                                                        //Increment index when moving onto next category
        }

        for (Category c : expenseCategories) {                                                           //Repeat the above process for expense categories

            sortedList.add(new EarningsHistoryListItem(c.getName(), Flow.OUTCOME, 0, 0));

            for (Transaction t : transactions) {

                if (t.getFlow() == Flow.OUTCOME) {

                    if (t.getDate().before(lastMonthDate)) {

                        if (t.getCategory() == c)
                            lastMonthCounter[i] += t.getAmount();
                    }
                    else {

                        if (t.getCategory() == c)
                            thisMonthCounter[i] += t.getAmount();
                    }
                }
            }

            i++;
        }

        i = 0;                                                          //Reset the index

        for (EarningsHistoryListItem e : sortedList) {                  //This assigns all of the amount values in the arrays to the sorted list
            e.setLastMonthTotal(lastMonthCounter[i]);
            e.setThisMonthTotal(thisMonthCounter[i]);
            i++;
        }

        mEarningsHistoryView.showEarningsHistory(sortedList);

    }

    @Override
    public void takeView(EarningsHistoryContract.View view) {
        this.mEarningsHistoryView = view;
        loadTransactionsInDateRange();
    }

    @Override
    public void dropView() {
        this.mEarningsHistoryView = null;
    }

}
