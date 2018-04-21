package ufm.universalfinancemanager.home;

public class HomeDataCategory {

    protected String categoryName;
    protected float categorySpend;


    HomeDataCategory() {};

    HomeDataCategory(String categoryName, float categorySpend) {
        this.categoryName = categoryName;
        this.categorySpend = categorySpend;
    }
}
