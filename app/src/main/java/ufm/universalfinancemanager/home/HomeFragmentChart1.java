package ufm.universalfinancemanager.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import ufm.universalfinancemanager.db.entity.Transaction;

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
    public void populateList(List<Transaction> items) {
        tlist = items;
//        Toast.makeText(getContext(), "Category: " + items.get(0).getName(), Toast.LENGTH_SHORT).show();

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

        ArrayList categories = getCategories();
        setData(mChart, categories.size(), 100);
        setAxis(mChart, categories);

        return root;
    }

    private ArrayList getCategories() {

        ArrayList categories = new ArrayList();

        categories.add("Rent");
        categories.add("Gas");
        categories.add("Groceries");
        categories.add("Household");
        categories.add("Entertainment");
        categories.add("Savings");
        categories.add("401K");

        return categories;
    }

    private void setData(HorizontalBarChart mChart, int numCategories, int range) {

        ArrayList<BarEntry> yVals = new ArrayList<>();

        float barWidth = 9f;
        float spaceForBar = 10f;

        // Populate with random data
        for (int i = 0; i < numCategories; i++) {
            float val1 = (float) (Math.random() * 100);
            float val2 = (float) (100);

            yVals.add(new BarEntry(i, new float[]{val1, val2 - val1}));
        }

        BarDataSet set1;

        ArrayList<Integer> colors  = getColors();

        set1 = new BarDataSet(yVals, "| Category Spend");
        set1.setStackLabels(new String[]{"% Spent", "Max Budget"});
        set1.setColors(colors);
        set1.setValueTextSize(14f);
        mChart.setDragDecelerationEnabled(true);

        BarData data = new BarData(set1);

        mChart.setData(data);
    }

    // Get array of colors for horizontal stacked bar chart, with 2nd value as light grey
    ArrayList<Integer> getColors() {

        ArrayList<Integer> tempColors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            tempColors.add(c);
            tempColors.add(Color.LTGRAY);
        }

        for (int c : ColorTemplate.JOYFUL_COLORS) {
            tempColors.add(c);
            tempColors.add(Color.LTGRAY);
        }

        for (int c : ColorTemplate.COLORFUL_COLORS) {
            tempColors.add(c);
            tempColors.add(Color.LTGRAY);
        }

        return tempColors;
    }
    public void setAxis(HorizontalBarChart mChart, ArrayList categories) {

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(18f);


        //X-axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisMaximum(categories.size());
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(categories));
        xAxis.setXOffset(-380f);
        xAxis.setTextSize(18f);

        //Y-axis
        mChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMaximum(100);
        leftAxis.setAxisMinimum(0);

        mChart.setFitBars(false);
        mChart.invalidate();

        mChart.getDescription().setEnabled(false);
    }
}
