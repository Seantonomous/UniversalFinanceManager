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
import com.github.mikephil.charting.components.Description;
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
import ufm.universalfinancemanager.db.source.local.CategoryDao;

/* Aaron: This is the chart for the Budget Spend Horizontal Bar Graph */
public class HomeFragmentChart1 extends DaggerFragment implements HomeContract.View {
    @Inject
    public HomePresenter mPresenter;
    HomeClassBudgetSpend horizontalBarChart;

    private View mChartView;
    private View mNoChartView;
    int budgetCount;
    @Inject
    public HomeFragmentChart1() {}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this, 1);
    }

    @Override
    public void getList(List<Transaction> items) {

    }

    @Override
    public void showNoChart() {
        //STUB
    }

    @Override
    public void populateList(List<Transaction> items) {
        //STUB
    }

    @Override
    public void populateCategories(ArrayList<HomeDataCategory> items) {
        //STUB
    }

    @Override
    public void populateBudgets(ArrayList<HomeDataBudgetSpend> data) {
        budgetCount = data.size();
        if(data.isEmpty()) {
            mNoChartView.setVisibility(View.VISIBLE);
            mChartView.setVisibility(View.GONE);
        }else {
            mNoChartView.setVisibility(View.GONE);
            mChartView.setVisibility(View.VISIBLE);
            horizontalBarChart.setData(data);
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment_chart1, container, false);
        mChartView = root.findViewById(R.id.chartLayout);
        mNoChartView = root.findViewById(R.id.noChartLayout);
        mNoChartView.setVisibility(View.GONE);

        HorizontalBarChart mChart;
        mChart = root.findViewById(R.id.chart1);
       // mChart.setPinchZoom(false);
       // mChart.setScaleEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        horizontalBarChart = new HomeClassBudgetSpend(mChart);


        setHasOptionsMenu(true);
        
        return root;
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
}
