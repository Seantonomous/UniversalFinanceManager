package ufm.universalfinancemanager.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.db.source.local.converter.DateConverter;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;

/**
 * Created by Areeba on 3/24/2018.
 */

public class Budget implements Serializable, ListItem {

    @ColumnInfo(name = "budget_name")
    private String name;

    @Embedded
    private Category category;

    @ColumnInfo(name = "amount")
    private double amount;
    private double currentValue;
    private String cat;
    @Ignore
    private NumberFormat num_format;
    @TypeConverters(DateConverter.class)
    private Date startDate;
    private Date endDate;


    public Budget(String name, String category, double amount, double current, Date date, Date endDate) {
        this.name = name;
        this.cat = category;
        this.amount = amount;
        this.currentValue= current;
        num_format = NumberFormat.getCurrencyInstance();
        this.startDate = date;
        this.endDate = endDate;

    }

    /******************Getters**********************/
    public String getName() {return name;}
    public Category getCategory() {return category;}
    public String getCat() {return cat;}
    public double getAmount() {return amount;}
    public double getCurrentValue() {return currentValue;}
    public Date getStartDate() {return startDate;}
    public Date getEndDate() {return endDate;}
    //public boolean getFrequency() {return frequency;}

    /*****************Setters***********************/
    public void setName(String name) {this.name = name;}
    public void setCategory(Category category) {this.category = category;}
    public void setCat(String name) {this.cat = name;}
    public void setAmount(double amount) {this.amount = amount;}
    public void setCurrentValue(double current) {this.currentValue = current;}
    public void setStartDate(Date date) {this.startDate = date;}
    public void setEndDate(Date date) {this.endDate = date;}
    //public void setFrequency(Boolean frequency) {this.frequency = frequency;}


    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {

        View view;
        if(convertView == null)
            view = inflater.inflate(R.layout.budget_overview_list_item, null);
        else
            view = convertView;

        TextView categoryName = view.findViewById(R.id.category);
        TextView budgetName = view.findViewById(R.id.name);
        TextView spentMoney = view.findViewById(R.id.spent); //set this textview
        TextView remainingBalance = view.findViewById(R.id.left); //set this textview
        TextView endD = view.findViewById(R.id.endDate);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());

        categoryName.setText("Category:  " + this.cat);
        budgetName.setText(this.name);
        spentMoney.setText(num_format.format(this.currentValue) + " / " + num_format.format(this.amount));
        endD.setText(dateFormatter.format(startDate) + " - " + dateFormatter.format(endDate));

        remainingBalance.setText(num_format.format(this.amount - this.currentValue));

        if(this.currentValue>this.amount)
            remainingBalance.setTextColor(Color.rgb(183, 71, 71));
        else
            remainingBalance.setTextColor(Color.rgb(84, 175, 120));


        return view;
    }
}
