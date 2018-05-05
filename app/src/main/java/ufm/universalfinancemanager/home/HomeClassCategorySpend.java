package ufm.universalfinancemanager.home;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import ufm.universalfinancemanager.R;

public class HomeClassCategorySpend {

    PieChart mChart;
    private ArrayList<String> xData;
    private ArrayList<Float> yData;
    protected ArrayList<HomeDataCategory> pieData;
    private float totalCategorySpend;

    HomeClassCategorySpend() {};

    public HomeClassCategorySpend(PieChart mChart, ArrayList<HomeDataCategory> pieData) {

        this.mChart = mChart;
        this.pieData = pieData;
        totalCategorySpend = getTotalSpent(pieData);
        xData = createXDataArray();
        yData = createYDataArray();

        createPieChart();
    }

    public HomeClassCategorySpend(PieChart mChart) {
        this.mChart = mChart;
    }

    public void setData(ArrayList<HomeDataCategory> data) {
        this.pieData = data;
        this.totalCategorySpend = getTotalSpent(pieData);
        xData = createXDataArray();
        yData = createYDataArray();

        createPieChart();
    }

    protected void createPieChart() {

        // configure pie chart
        mChart.setUsePercentValues(true);

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
    }

    protected void addData() {

        List<Entry> yVals1 = new ArrayList<Entry>();

        for (
                int i = 0;
                i < yData.size(); i++)
            yVals1.add(new

                    Entry(yData.get(i), i));

        List<String> xVals = new ArrayList<String>();

        for (
                int i = 0;
                i < xData.size(); i++)
            xVals.add(xData.get(i));



        List<PieEntry> entries = new ArrayList<>();


        // Adding entries to PieChart, and dividing float amounts by total spend * 100 for percent
        for (int i = 0; i < xData.size(); i++) {
            entries.add(new PieEntry(yData.get(i)/totalCategorySpend*100, xData.get(i)));
        }

        PieDataSet set = new PieDataSet(entries, "Budget Summary");
        PieData data = new PieData(set);
        mChart.setData(data);
        mChart.invalidate(); // refresh

        // Adding colors
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

    private ArrayList<String> createXDataArray() {
        ArrayList<String> tempXData = new ArrayList<>();

        for (int i = 0; i < pieData.size(); i++){
            tempXData.add(pieData.get(i).categoryName);
        }

        return tempXData;
    }

    private ArrayList<Float> createYDataArray() {
        ArrayList<Float> tempYData = new ArrayList<>();

        for (int i = 0; i < pieData.size(); i++){
            tempYData.add(pieData.get(i).categorySpend);
        }
        return tempYData;
    }



    protected float getTotalSpent(ArrayList<HomeDataCategory> categoryData){
        /* This iterates through each category to get a total amount spent,
           to be used for percentages of each category
        */

        float tempTotal = 0;

        for (int i = 0; i < categoryData.size(); i++) {
            tempTotal = tempTotal + categoryData.get(i).categorySpend;
        }

        return tempTotal;
    }

}
