package ufm.universalfinancemanager.home;

public class HomeDataNetWorth {
    protected int monthNumber;
    protected float totalAssets;
    protected float totalDebts;
    protected float netWorth;

    HomeDataNetWorth() {};

    HomeDataNetWorth(int monthNumber, float totalAssets, float totalDebts) {
        this.monthNumber = monthNumber;
        this.totalAssets = totalAssets;
        this.totalDebts = totalDebts;
        netWorth = getNetWorth();
    }

    private float getNetWorth() {
        return (totalAssets - totalDebts);
    }
}
