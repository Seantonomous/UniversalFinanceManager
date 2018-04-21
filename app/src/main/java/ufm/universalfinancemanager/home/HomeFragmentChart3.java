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


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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

/* Aaron: This is the chart for the Cateogry Spend Pie Chart */
public class HomeFragmentChart3 extends DaggerFragment implements HomeContract.View {

    private PieChart mChart;
    private ArrayList<Float> yData;
    private ArrayList<String> xData;

    private List<Transaction> tlist;

    @Inject
    public HomePresenter mPresenter;

    @Inject
    public HomeFragmentChart3() {}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void populateList(List<Transaction> items) {
        tlist = items;
//        Toast.makeText(getContext(), "Category: " + items.get(0).getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getList(List<Transaction> items) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment_chart3, container, false);

        mChart = (PieChart) root.findViewById(R.id.chart3);

        ArrayList<HomeDataCategory> pieData;
        pieData = getData();

        HomeClassCategorySpend horizontalBarChart = new HomeClassCategorySpend(mChart, pieData);


        return root;
    }

    private ArrayList getData(){

        ArrayList<HomeDataCategory> tempPieData = new ArrayList<>();

        // Needs to be percentages that toal 100%
        tempPieData.add(new HomeDataCategory("Rent", 10.0f));
        tempPieData.add(new HomeDataCategory("Gas", 17.0f));
        tempPieData.add(new HomeDataCategory("Groceries", 16.0f));
        tempPieData.add(new HomeDataCategory("Household", 20.0f));
        tempPieData.add(new HomeDataCategory("Entertaiment", 20.0f));
        tempPieData.add(new HomeDataCategory("Savings", 7.0f));
        tempPieData.add(new HomeDataCategory("401k", 10.0f));

        return tempPieData;
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
