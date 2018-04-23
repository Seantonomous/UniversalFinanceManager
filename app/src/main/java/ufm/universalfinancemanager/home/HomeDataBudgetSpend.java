package ufm.universalfinancemanager.home;

public class HomeDataBudgetSpend {
    protected String categoryName;
    protected float categorySpent;
    protected float categoryTotal;
    protected float percentSpent;

    HomeDataBudgetSpend() {};

    HomeDataBudgetSpend(String categoryName, float categorySpent, float categoryTotal) {
        this.categoryName = categoryName;
        this.categorySpent = categorySpent;
        this.categoryTotal = categoryTotal;
        percentSpent = getPercentSpent(categorySpent, categoryTotal);
    }

    protected float getPercentSpent(float spent, float capAmout) {
        return (spent/capAmout);
    }

}
