package ufm.universalfinancemanager.home;

import android.graphics.Color;

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

public class HomeClassBudgetSpend {

    HorizontalBarChart mChart;
    ArrayList<String> categories;
    ArrayList<HomeDataBudgetSpend> budgetData;      // This is (Category Name, spend, capAmount)

    HomeClassBudgetSpend() {}

    HomeClassBudgetSpend(HorizontalBarChart mChart, ArrayList<HomeDataBudgetSpend> budgetData){
        this.mChart = mChart;
        this.budgetData = budgetData;


        /* What is need to generate this chart?
            1. HorizonangalBarChart
            2. List of categories with "Budget Total" amounts
            3. Amount of Budet spent
         */

        categories = getCategoryNames();
        setData(mChart, budgetData);
        setAxis(mChart, categories);
    }


    protected void setData(HorizontalBarChart mChart, ArrayList<HomeDataBudgetSpend> budgetData) {

        ArrayList<BarEntry> yVals = new ArrayList<>();

        float barWidth = 9f;
        float spaceForBar = 10f;

        // Add Bar Entries for each category ("Name, % spent, 100f
        for (int i = 0; i < budgetData.size(); i++) {
            yVals.add(new BarEntry(i, new float[]{budgetData.get(i).percentSpent*100f, 100f}));
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

    protected void setAxis(HorizontalBarChart mChart, ArrayList<String> categories) {

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

    private ArrayList<Integer> getColors() {

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

    private ArrayList<String> getCategoryNames() {
        ArrayList<String> tempCategories = new ArrayList<String>();

        for (int i = 0; i < budgetData.size(); i++) {
            tempCategories.add(budgetData.get(i).categoryName);
        }

        return tempCategories;
    }
}
