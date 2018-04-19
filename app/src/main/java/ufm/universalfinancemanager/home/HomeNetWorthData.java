package ufm.universalfinancemanager.home;

import java.text.DateFormatSymbols;

public class HomeNetWorthData {
    protected int monthInt;
    protected float totalAsset;
    protected float totalDebt;

    HomeNetWorthData() {

    }

    HomeNetWorthData(int monthInt, float totalAsset, float totalDebt) {
        this.monthInt = monthInt;
        this.totalAsset = totalAsset;
        this.totalDebt = totalDebt;
    }

    protected float getNetWorth() {
        return (totalAsset - totalDebt);
    }

    protected String getMonthName() {
        String mName = new DateFormatSymbols().getShortMonths()[monthInt];
        return mName;
    }



}
