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
        mNetWorthChart.setData(getData());
    }

    @Override
    public void populateCategories(ArrayList<HomeDataCategory> items) {
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

        setHasOptionsMenu(true);
        return root;
    }


    private ArrayList getData(){

        /* Required data
            1. yAxaxis max value (for chart cap)
            2. Assets broken down into 6 months
            3. Debts broken down into 6 months
         */

        ArrayList<HomeDataNetWorth> tempNWData = new ArrayList<>();

        tempNWData.add(new HomeDataNetWorth(11, 50f, 20f));
        tempNWData.add(new HomeDataNetWorth(12, 55f, 20f));
        tempNWData.add(new HomeDataNetWorth(1, 58f, 19f));
        tempNWData.add(new HomeDataNetWorth(2, 62f, 18f));
        tempNWData.add(new HomeDataNetWorth(3, 58f, 16f));
        tempNWData.add(new HomeDataNetWorth(4, 70f, 15f));

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

