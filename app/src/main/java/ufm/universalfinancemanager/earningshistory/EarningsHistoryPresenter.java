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

import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.db.entity.Category;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.util.EspressoIdlingResource;


/**
 * Created by Faiz on 2/26/2018.
 */

@ActivityScoped
public class EarningsHistoryPresenter implements EarningsHistoryContract.Presenter {
    private User mUser;
    private List<EarningsHistoryListItem> sortedList;
    private final UserRepository mTransactionRepository;

    @Nullable
    EarningsHistoryContract.View mEarningsHistoryView;

    @Inject
    EarningsHistoryPresenter(UserRepository transactionRepository, User user) {
        mTransactionRepository = transactionRepository;
        mUser = user;
    }

    public void editCategory(EarningsHistoryListItem category) {
       if(mEarningsHistoryView != null)
            mEarningsHistoryView.showEditCategory(category.getName());
    }

    @Override
    public void loadTransactionsInDateRange() {
        //Performing database/network call, forbid ui test activity until it's done
        EspressoIdlingResource.increment();

        Calendar cal = Calendar.getInstance();

        Date a = cal.getTime();
        long currentDate = a.getTime();

        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 0);

        Date b = cal.getTime();
        long pastDate = b.getTime();

        mTransactionRepository.getTransactionsInDateRange(pastDate, currentDate, new UserDataSource.LoadTransactionsCallback() {

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
                sortTransactions(new ArrayList<Transaction>());
            }
        });
    }

    public void sortTransactions(List<Transaction> transactions){

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, 0);
        Date lastMonthDate = cal.getTime();                                                             //Gets cutoff date between last month and current month

        Calendar cal2 = Calendar.getInstance();

        cal2.add(Calendar.MONTH, -1);
        cal2.set(Calendar.DAY_OF_MONTH, 0);
        Date cutoffDate = cal2.getTime();

        sortedList = new ArrayList<>();
        ArrayList<Category> incomeCategories = mUser.getIncomeCategories();                             //Get income categories list from user
        ArrayList<Category> expenseCategories = mUser.getExpenseCategories();                           //Get expense categories list from user

        if(incomeCategories.isEmpty() && expenseCategories.isEmpty())
            if(mEarningsHistoryView != null) {
                mEarningsHistoryView.showNoEarningsHistory();
                return;
            }

        double[] thisMonthCounter = new double[incomeCategories.size() + expenseCategories.size()];     //Will hold current-month amount totals for each category
        double[] lastMonthCounter = new double[incomeCategories.size() + expenseCategories.size()];     //Will hold last-month amount totals for each category
        int i = 0;

        for (Category c : incomeCategories) {                                                           //Loop through income categories list

            sortedList.add(new EarningsHistoryListItem(c.getName(), Flow.INCOME, 0, 0));         //Create new list item for each category

            for (Transaction t : transactions)  {                                                        //Loop through transactions

                if (t.getFlow() == Flow.INCOME) {                                                        //If transaction is an income type

                    if (t.getDate().before(lastMonthDate)) {                                            //If last month transaction

                        if(t.getDate().after(cutoffDate)){

                            if (t.getCategory().equals(c.getName()))                                         //If transaction's category matches current category
                                lastMonthCounter[i] += t.getAmount();                                        //Add transaction's amount to counter for this category
                        }
                    }
                    else {                                                                               //If current month transaction
                        if(t.getDate().before(Calendar.getInstance().getTime())){
                            if (t.getCategory().equals(c.getName()))                                         //If transaction's category matches current category
                                thisMonthCounter[i] += t.getAmount();                                        //Add transaction's amount to counter for this category
                        }

                    }
                }

            }

            i++;                                                                                         //Increment index when moving onto next category
        }

        for (Category c : expenseCategories) {                                                            //Repeat the above process for expense categories

            sortedList.add(new EarningsHistoryListItem(c.getName(), Flow.OUTCOME, 0, 0));

            for (Transaction t : transactions) {

                if (t.getFlow() == Flow.OUTCOME) {

                    if (t.getDate().before(lastMonthDate)) {

                        if (t.getCategory().equals(c.getName()))
                            lastMonthCounter[i] += t.getAmount();
                    }
                    else {

                        if (t.getCategory().equals(c.getName()))
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

        if(mEarningsHistoryView != null)
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
