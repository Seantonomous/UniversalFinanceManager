package ufm.universalfinancemanager.earningshistory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;

/**
 * Created by Faiz on 4/15/2018.
 */

public class EarningsHistoryListItem implements ListItem {

    private String categoryName;
    private Flow flow;
    private double thisMonthTotal;
    private double lastMonthTotal;
    private NumberFormat num_format;

    public EarningsHistoryListItem(String name, Flow f, double thisMonth, double lastMonth){

        categoryName = name;
        flow = f;
        thisMonthTotal = thisMonth;
        lastMonthTotal = lastMonth;
        num_format = NumberFormat.getCurrencyInstance();
    }

    /******************Getters**********************/
    public String getName() {return categoryName;}
    public Flow getFlow() {return flow;}
    public double getThisMonthTotal() {return thisMonthTotal;}
    public double getLastMonthTotal() {return lastMonthTotal;}

    /*****************Setters***********************/
    public void setName(String name) {this.categoryName = name;}
    public void setFlow(Flow f) {this.flow = f;}
    public void setThisMonthTotal(double amount) {this.thisMonthTotal = amount;}
    public void setLastMonthTotal(double amount) {this.lastMonthTotal = amount;}

    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {

        View view;
        if(convertView == null)
            view = inflater.inflate(R.layout.earnings_list_item, null);
        else
            view = convertView;

        //Instantiate all the textviews from the layout
        TextView categoryName = view.findViewById(R.id.category_name);
        TextView lastMonthBalance = view.findViewById(R.id.lastMonth_balance);
        TextView thisMonthBalance = view.findViewById(R.id.thisMonth_balance);

        //Set the text of each textview based on its corresponding transaction attribute
        categoryName.setText(this.categoryName);
        lastMonthBalance.setText(num_format.format(this.lastMonthTotal));
        thisMonthBalance.setText(num_format.format(this.thisMonthTotal));

        return view;
    }
}
