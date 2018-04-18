package ufm.universalfinancemanager.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import ufm.universalfinancemanager.db.entity.Transaction;

/* Aaron: This is the chart for the Cateogry Spend Pie Chart */
public class HomeFragmentChart3 extends DaggerFragment implements HomeContract.View {

    private PieChart mChart;
    private float[] yData = {10.0f, 17.0f, 16.0f, 20.0f, 20.0f, 7.0f, 10.0f};
    private String[] xData = {"Rent", "Gas", "Groceries", "Household", "Entertaiment", "Savings", "401k"};

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
        View root = inflater.inflate(R.layout.home_fragment_chart3, container, false);

        mChart = (PieChart) root.findViewById(R.id.chart3);

        // configure pie chart
        mChart.setUsePercentValues(true);
//        mChart.setDescription("Budget Summary");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(000000);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // add data
        addData();

        // customize legends
        Legend l = mChart.getLegend();
        // l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        return root;
    }

    void addData() {

        List<Entry> yVals1 = new ArrayList<Entry>();

        for (
                int i = 0;
                i < yData.length; i++)
            yVals1.add(new

                    Entry(yData[i], i));

        List<String> xVals = new ArrayList<String>();

        for (
                int i = 0;
                i < xData.length; i++)
            xVals.add(xData[i]);

//        // create pie data set
//        PieDataSet dataSet = new PieDataSet(yVals1, "Categories");
//        dataSet.setSliceSpace(3);
//        dataSet.setSelectionShift(5);

        List<PieEntry> entries = new ArrayList<>();

//        List categoryList = getCategories();

        for (int i = 0; i < xData.length - 1; i++) {
            entries.add(new PieEntry(yData[i], xData[i]));
        }

        PieDataSet set = new PieDataSet(entries, "Budget Summary");
        PieData data = new PieData(set);
        mChart.setData(data);
        mChart.invalidate(); // refresh

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (
                int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (
                int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (
                int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (
                int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (
                int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        set.setColors(colors);

        // instantiate pie data object now
//        PieData data = new PieData(xVals, set);
//        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }
}
