package ufm.universalfinancemanager.home;

import android.graphics.Color;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HomeClassNetWorth {

    CombinedChart mChart;
    protected int numMonths = 7;
    protected float maxFloatNum; // Used to create max amount for assets and debits
    protected List<String> mMonths = new ArrayList<String>();
    protected float arrData[][];  // [0][month] is Assets, [1][month] is Debt, [2][month] is net Worth
    protected ArrayList<HomeDataNetWorth> nwData;
    protected float currentAssetTotal;
    protected float currentDebtTotal;
    private float netWorthTextSize = 12f;  // Originally 10
    private float xAxisLegendTextSize = 12f;
    private float yAxisLegendTextSize = 10f;


    HomeClassNetWorth(CombinedChart chart) {
        this.mChart = chart;
        createMonthLegend();
        createNetWorthBarChart();
        currentAssetTotal = 0;
        currentDebtTotal = 0;
    }

    public void createMonthLegend() {
        int month;
        Calendar cal = Calendar.getInstance();

        Date date = new Date();
        cal.setTime(date);
        month = cal.get(Calendar.MONTH);

        String strMonth;
        month = (month + 6)%12;

        for (int i = 0; i < 7; i++) {
            if (month == 12)
                month = 0;

            strMonth = getMonth(month++);
            mMonths.add(strMonth);
        }
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getShortMonths()[month];
    }

    protected void getRandomData() {

        // Populate double Array
        // Set starting data for net worth (Assets[0][], Liabilities[1][], NetWorth[2][]
        arrData[0][0] = 60f;
        arrData[1][0] = -30f;
        arrData[2][0]= 30f;

        //Generate random data
        Random rand = new Random();

        // Loop through bars 2 through 6 with random-ish data
        for (int i = 1; i < 6; i++) {
            int randomNum1 = rand.nextInt((15 - 3) + 1) + 3;
            arrData[0][i] = arrData[0][i-1]*(1+(float)randomNum1/100);

            int randomNum2 = rand.nextInt((15 - 1) + 1) + 3;
            arrData[1][i] = arrData[1][i-1]*(1+(float)randomNum2/100);

            arrData[2][i] = arrData[0][i] + arrData[1][i];
        }

    }

    private float getMaxYValue() {

        float yMax = -1f;

        for (int i = 0; i < nwData.size(); i++){
            if (nwData.get(i).totalAssets > yMax)
                yMax = nwData.get(i).totalAssets;
            if (nwData.get(i).totalDebts > yMax)
                yMax = nwData.get(i).totalDebts;
        }

        return yMax;
    }

    protected void createNetWorthBarChart() {

        // float maxYVal = getMaxYValue();
        float maxYVal = 4000f;

        mChart.getDescription().setText("Net Worth");
        mChart.setDrawGridBackground(true);
        mChart.setDrawBarShadow(true);
        mChart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.LINE
        });

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(-maxYVal); // start at -100
        rightAxis.setAxisMaximum(maxYVal); // the axis maximum is 100
        rightAxis.setTextSize(yAxisLegendTextSize);
//        rightAxis.setTextColor(Color.rgb(250,250,250));

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(-maxYVal);
        leftAxis.setAxisMaximum(maxYVal);
        leftAxis.setTextSize(yAxisLegendTextSize);
//        leftAxis.setTextColor(Color.rgb(250,250,250));

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(xAxisLegendTextSize);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMonths.get((int)value);
            }
        });



    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries = getLineEntriesData(entries);

        LineDataSet set = new LineDataSet(entries, "Net Worth");
        set.setColor(Color.rgb(240, 238, 70));
        set.setColor(Color.DKGRAY);
        // set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(netWorthTextSize);
        set.setValueTextColor(Color.rgb(159, 64, 120));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;

    }

    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries){

        // Get data by month, subtotaled by flowType

//        entries.add(new Entry(1, arrData[2][0]));
//        entries.add(new Entry(2, arrData[2][1]));
//        entries.add(new Entry(3, arrData[2][2]));
//        entries.add(new Entry(4, arrData[2][3]));
//        entries.add(new Entry(5, arrData[2][4]));
//        entries.add(new Entry(6, arrData[2][5]));
//
//        ArrayList<BarEntry> yVals = new ArrayList<>();

//        float barWidth = 9f;
//        float spaceForBar = 10f;

//        for (int i = 0; i < 7; i++) {
//            float val1 = (float) (Math.random() * 100);
//            float val2 = (float) (100);
//
//            yVals.add(new BarEntry(i, new float[]{val1, val2 - val1}));
//        }

        for (int i = 0; i < nwData.size(); i++){
            entries.add(new BarEntry(i + 1, nwData.get(i).netWorth));
        }

        return entries;

    }

    private ArrayList<BarEntry> getBarEnteries(ArrayList<BarEntry> entries){

//        entries.add(new BarEntry(1,arrData[0][0]));
//        entries.add(new BarEntry(1,arrData[1][0]));
//        entries.add(new BarEntry(2,arrData[0][1]));
//        entries.add(new BarEntry(2,arrData[1][1]));
//        entries.add(new BarEntry(3,arrData[0][2]));
//        entries.add(new BarEntry(3,arrData[1][2]));
//        entries.add(new BarEntry(4,arrData[0][3]));
//        entries.add(new BarEntry(4,arrData[1][3]));
//        entries.add(new BarEntry(5,arrData[0][4]));
//        entries.add(new BarEntry(5,arrData[1][4]));
//        entries.add(new BarEntry(6,arrData[0][5]));
//        entries.add(new BarEntry(6,arrData[1][5]));

        for (int i = 0; i < nwData.size(); i++){
            entries.add(new BarEntry(i + 1, nwData.get(i).totalAssets));
            entries.add(new BarEntry(i + 1, nwData.get(i).totalDebts));
        }

        return  entries;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries = getBarEnteries(entries);

        BarDataSet set1 = new BarDataSet(entries, "Assets/Liabilities");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setColors(ColorTemplate.COLORFUL_COLORS);
        set1.setValueTextColor(Color.rgb(215, 215, 215));
        //set1.setValueTextSize(netWorthTextSize);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);


        ArrayList<Integer> colors  = getColors();
        set1.setColors(colors);

        float barWidth = 0.45f; // x2 dataset


        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);


        return d;
    }

    ArrayList<Integer> getColors() {

        ArrayList<Integer> tempColors = new ArrayList<Integer>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
//            tempColors.add(c);
//            tempColors.add(Color.LTGRAY);
//        }
        tempColors.add(Color.GREEN);
        tempColors.add(Color.RED);

        return tempColors;
    }

    public void setData(ArrayList<HomeDataNetWorth> newData) {
        nwData = newData;

        CombinedData data = new CombinedData();

        float maxYVal = getMaxYValue() * 1.5f;

//        maxYVal = 100f;

        mChart.getAxisRight().setAxisMinimum(-maxYVal); // start at -100
        mChart.getAxisRight().setAxisMaximum(maxYVal); // the axis maximum is 100

        mChart.getAxisLeft().setAxisMinimum(-maxYVal);
        mChart.getAxisLeft().setAxisMaximum(maxYVal);

        data.setData(generateLineData());
        data.setData(generateBarData());

        mChart.getXAxis().setAxisMaximum(data.getXMax() + 0.25f);
        mChart.setData(data);
        mChart.invalidate();
    }

    public void setRealData(ArrayList<HomeDataNetWorth> realData) {
        nwData = realData;

        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData());

        mChart.getXAxis().setAxisMaximum(data.getXMax() + 0.25f);
        mChart.setData(data);
        mChart.invalidate();
    }

}