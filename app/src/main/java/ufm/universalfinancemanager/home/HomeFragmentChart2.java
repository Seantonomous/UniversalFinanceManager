package ufm.universalfinancemanager.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.SearchView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountActivity;
import ufm.universalfinancemanager.addeditbudget.AddEditBudgetActivity;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryActivity;
import ufm.universalfinancemanager.addeditreminder.AddEditReminderActivity;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.db.entity.Account;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.atomic.User;


/* Aaron: This is the chart for the Networth Bar Graph */
public class HomeFragmentChart2 extends DaggerFragment implements HomeContract.View {

    @Inject
    public HomePresenter mPresenter;

    @Inject
    public User mUser;

    @Inject
    public HomeFragmentChart2() {}

    private CombinedChart mChart;
    private View mTransactionsView;
    private SearchView mSearchView;


    List<Transaction> myList;
    HomeClassNetWorth mNetWorthChart;
    private float maxYVal = -1;

    @Override
    public void onResume() {
        super.onResume();

//         HomeClassNetWorth netWorthChart =  new HomeClassNetWorth(mChart);

        mPresenter.takeView(this, 2);
    }

    @Override
    public void getList(List<Transaction> items) {

    }


    @Override
    public void populateList(List<Transaction> items) {
        myList = items;

        if (myList.size() > 0)
            mNetWorthChart.setData(getData(items));

    }

    @Override
    public void populateCategories(ArrayList<HomeDataCategory> items) {
        //STUB
    }

    @Override
    public void populateBudgets(ArrayList<HomeDataBudgetSpend> items) {
        //STUB
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment_chart2, container, false);

        //        mPresenter.loadTransactions();
        //        getList(myList);

        mChart = root.findViewById(R.id.chart2);

        mNetWorthChart =  new HomeClassNetWorth(mChart);

//        mNetWorthChart.createNetWorthBarChart(4000f);

        setHasOptionsMenu(true);
        return root;
    }


    private ArrayList getData(List<Transaction> items){

       /* Required data
            1. yAxaxis max value (for chart cap)
            2. Assets broken down into 6 months
            3. Debts broken down into 6 months
         */

        myList = items;
        HomeDataTransactionItem tempItem;

        List<String> creditCardList = new ArrayList<>();
        List<HomeDataTransactionItem> allTransactions = new ArrayList<>();

        int thisMonth = -1;
        Date date = new Date();



        // Get current list of Accounts, both Types and the current balalnces
        for (Account acct : mUser.getAccounts()) {
            if (acct.getType() == AccountType.CREDIT_CARD) {
                creditCardList.add(acct.getName());
                mNetWorthChart.currentDebtTotal += acct.getBalance();
            }
            else {
                mNetWorthChart.currentAssetTotal += acct.getBalance();
            }
        }

        // Organize all transactions by month, Outcome and Income, totaling the income/outcome
        // Initialize array
        float[][] totalTransactionsMaster = new float[2][12];
        for (float[] tRow : totalTransactionsMaster) {
            for (float tCol : tRow) {
                tCol = 0.0f;
            }
        }


        // Add transactions to list of HomeDataTransaction Items (total list of transactions)
        for (Transaction item : myList) {
            tempItem = new HomeDataTransactionItem();

            // Assign thisMonth
            thisMonth = tempItem.getMonthInt(date);

            // Get Account Name and Flow
            if (item.getFlow() == Flow.INCOME) {
                tempItem.accountName = item.getToAccount();
                tempItem.varTransactionType = HomeDataTransactionItem.TransactionType.Credit;
            }
            else {
                tempItem.accountName = item.getFromAccount();
                tempItem.varTransactionType = HomeDataTransactionItem.TransactionType.Debit;
            }

            tempItem.varAcctType = tempItem.getAccountType(tempItem.accountName, creditCardList);  // Get Account Type
            tempItem.transactionAmount = (float) item.getAmount();     // Get Transaction amount
            tempItem.monthInt = tempItem.getMonthInt(item.getDate());  // Get Month Integer
            item.getAmount();

            allTransactions.add(tempItem);
        }

        totalTransactionsMaster[0][thisMonth] = mNetWorthChart.currentAssetTotal;
        totalTransactionsMaster[1][thisMonth] = mNetWorthChart.currentDebtTotal;

        // Organize all transactions by month, Outcome and Income, totaling the income/outcome
        // Initialize array
        float[][] totalTransactions = new float[2][12];
        for (float[] tRow : totalTransactions) {
            for (float tCol : tRow) {
                tCol = 0.0f;
            }
        }

        // Populate array with corresponding transacations
        for (HomeDataTransactionItem t : allTransactions) {
            if (t.varAcctType == HomeDataTransactionItem.AcctType.Asset
                    && t.varTransactionType == HomeDataTransactionItem.TransactionType.Credit) {
                totalTransactions[0][t.monthInt] = totalTransactions[0][t.monthInt] + t.transactionAmount;
            }
            else if (t.varAcctType == HomeDataTransactionItem.AcctType.Asset
                    && t.varTransactionType == HomeDataTransactionItem.TransactionType.Debit) {
                totalTransactions[0][t.monthInt] = totalTransactions[0][t.monthInt] - t.transactionAmount;
            }
            else if (t.varAcctType == HomeDataTransactionItem.AcctType.Liability
                    && t.varTransactionType == HomeDataTransactionItem.TransactionType.Credit) {
                totalTransactions[1][t.monthInt] = totalTransactions[1][t.monthInt] - t.transactionAmount;
            }
            else if (t.varAcctType == HomeDataTransactionItem.AcctType.Liability
                    && t.varTransactionType == HomeDataTransactionItem.TransactionType.Debit) {
                totalTransactions[1][t.monthInt] = totalTransactions[1][t.monthInt] + t.transactionAmount;
            }
        }


        // Next, todo: add transactions to the correct balances
        /*
        Backwards ajusted to previous month
        case 1: 	Outcome 	type = credit card,		-> totalDebt decreases previous month
        case 2: 	Outcome   	type != credit card, 	-> totalAssets increases previous month
        case 3: 	Income 		type = credit card, 	-> totalDebt increases previous month
        case 4: 	Income 		type != credit card, 	-> totalAssets decreases previous month
         */

        /* *** useAcctBal True is working backwards from current account balancd,
        useAcctBal False is working forward based on transactins w/out acct balance */
        boolean useAcctBal = true;

        float[][] blank;

        // Combine arrays to make master account balance chart
        int curMonth = thisMonth;
        int nextMonth = curMonth + 1;

        if (nextMonth == 12)
            nextMonth = 0;

        for (int i = 0; i < 7; i++) {

            if (useAcctBal) {
                curMonth--;
                nextMonth--;

                if (curMonth == -1  || nextMonth == -1) {
                    curMonth = 11;
                    nextMonth = 11;
                }

                totalTransactionsMaster[0][curMonth] = totalTransactionsMaster[0][nextMonth] - totalTransactions[0][nextMonth];
                totalTransactionsMaster[1][curMonth] = totalTransactionsMaster[1][nextMonth] - totalTransactions[1][nextMonth];

                // Don't allow negative balances for Assets
                if (totalTransactionsMaster[0][curMonth] < 0) {
                    totalTransactionsMaster[0][curMonth] = 0;
                }

                // Don't allow negative balances for Debts
                if (totalTransactionsMaster[1][curMonth] < 0) {
                    totalTransactionsMaster[1][curMonth] = 0;
                }
            }
            else {

                if (i == 0) {
                    blank = new float[2][12];
                    for (float[] tRow : blank) {
                        for (float tCol : tRow) {
                            tCol = 0.0f;
                        }
                    }
                    curMonth = curMonth - 5;
                    nextMonth = curMonth + 1;
                    totalTransactionsMaster = blank;
                }

                if (curMonth < 0  || nextMonth < 0) {
                    curMonth = curMonth + 12;
                    nextMonth = nextMonth + 12;
                }

                if (curMonth == 12)
                    curMonth = 0;

                if (nextMonth == 12)
                    nextMonth = 0;

                totalTransactionsMaster[0][nextMonth] = totalTransactions[0][nextMonth] + totalTransactions[0][curMonth];
                totalTransactionsMaster[1][nextMonth] = totalTransactions[1][nextMonth] + totalTransactions[1][curMonth];
                curMonth++;
                nextMonth++;
            }
        }


        ArrayList<HomeDataNetWorth> tempNWData = new ArrayList<>();
        int firstMonth = thisMonth - 5;

        if (firstMonth < 0)
            firstMonth = firstMonth + 12;

        for (int i = 0; i < 6; i++) {
            if (firstMonth == 12) {
                firstMonth = 0;
            }

            tempNWData.add(new HomeDataNetWorth(firstMonth, totalTransactionsMaster[0][firstMonth], -1*totalTransactionsMaster[1][firstMonth]));
            firstMonth++;
        }

        for (int i = 0; i < tempNWData.size(); i++){
            if (tempNWData.get(i).totalAssets > maxYVal)
                maxYVal = tempNWData.get(i).totalAssets;
            if (tempNWData.get(i).totalDebts > maxYVal)
                maxYVal = tempNWData.get(i).totalDebts;
        }

        // Static sample data
//        tempNWData.add(new HomeDataNetWorth(10, 50f, -50f));
//        tempNWData.add(new HomeDataNetWorth(11, 50f, -50f));
//        tempNWData.add(new HomeDataNetWorth(12, 55f, -20f));
//        tempNWData.add(new HomeDataNetWorth(1, 58f, -19f));
//        tempNWData.add(new HomeDataNetWorth(2, 62f, -18f));
//        tempNWData.add(new HomeDataNetWorth(3, 58f, -16f));
//        tempNWData.add(new HomeDataNetWorth(4, 70f, -5f));

        return tempNWData;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_transaction:
                startActivity(new Intent(getContext(), AddEditTransactionActivity.class));
                break;
            case R.id.action_add_account:
                startActivity(new Intent(getContext(), AddEditAccountActivity.class));
                break;
            case R.id.action_add_category:
                startActivity(new Intent(getContext(), AddEditCategoryActivity.class));
                break;
            case R.id.action_add_reminder:
                startActivity(new Intent(getContext(), AddEditReminderActivity.class));
                break;
            case R.id.action_add_budget:
                startActivity(new Intent(getContext(), AddEditBudgetActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
    }

    public interface TransactionClickListener {
        void onTransactionClicked(Transaction t);
    }
}