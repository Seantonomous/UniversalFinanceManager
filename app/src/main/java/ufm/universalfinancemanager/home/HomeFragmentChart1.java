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
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountActivity;
import ufm.universalfinancemanager.addeditbudget.AddEditBudgetActivity;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryActivity;
import ufm.universalfinancemanager.addeditreminder.AddEditReminderActivity;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.db.entity.Transaction;

/* Aaron: This is the chart for the Budget Spend Horizontal Bar Graph */
public class HomeFragmentChart1 extends DaggerFragment implements HomeContract.View {
    @Inject
    public HomePresenter mPresenter;
    private List<Transaction> tlist;

    @Inject
    public HomeFragmentChart1() {}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void getList(List<Transaction> items) {

    }

    @Override
    public void populateList(List<Transaction> items) {
        tlist = items;
//        Toast.makeText(getContext(), "Category: " + items.get(0).getName(), Toast.LENGTH_SHORT).show();

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

//    @Override
//    public void showTransactions(List<Transaction> items) {
//        Toast.makeText(getContext(), "show Transactions " + items.get(0).getName(), Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment_chart1, container, false);


        HorizontalBarChart mChart;
        mChart = (HorizontalBarChart) root.findViewById(R.id.chart1);

        ArrayList<HomeDataBudgetSpend> budgetData;
        budgetData = getData();

        HomeClassBudgetSpend horizontalBarChart = new HomeClassBudgetSpend(mChart, budgetData);


        return root;
    }

    private ArrayList getData() {

        /* This is the data the horizontal bar graph is created from
            It needs 3 inputs:
            1. Category Name
            2. Amount Spent for the category (float)
            3. The total/(cap) amount of the budget (float)
                Note: the class will convert these numbers to percentages (100% based) to display accordingly
        */

        ArrayList<HomeDataBudgetSpend> tempBudgetData = new ArrayList<HomeDataBudgetSpend>();
        ArrayList<String> categories = new ArrayList<String>();

        categories.add("Rent");
        categories.add("Gas");
        categories.add("Groceries");
        categories.add("Household");
        categories.add("Entertainment");
        categories.add("Savings");
        categories.add("401K");

        for (int i = 0; i < categories.size(); i++) {
            float spent = (float) (Math.random() * 100);     // % of Amount Spent per Category
            float capAmount = (float) (100);                 // Category cap 100%

            // Add the Name of the category, the spent amount, and the cap amount)
            tempBudgetData.add(new HomeDataBudgetSpend(categories.get(i), spent, capAmount));
        }

        return tempBudgetData;
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
