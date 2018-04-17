package ufm.universalfinancemanager.support.atomic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;

/**
 * Created by Areeba on 3/24/2018.
 */

public class Budget implements Parcelable, Serializable, ListItem {

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


    public Budget(String name, Category category, double amount, double current) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.currentValue= current;
        num_format = NumberFormat.getCurrencyInstance();
    }

    public Budget(String name, String category, double amount, double current) {
        this.name = name;
        this.cat = category;
        this.amount = amount;
        this.currentValue= current;
        num_format = NumberFormat.getCurrencyInstance();
    }

    /******************Getters**********************/
    public String getName() {return name;}
    public Category getCategory() {return category;}
    public double getAmount() {return amount;}
    public double getCurrentValue() {return currentValue;}
    //public boolean getFrequency() {return frequency;}

    /*****************Setters***********************/
    public void setName(String name) {this.name = name;}
    public void setCategory(Category category) {this.category = category;}
    public void setAmount(double amount) {this.amount = amount;}
    public void setCurrentValue(double current) {this.currentValue = current;}
    //public void setFrequency(Boolean frequency) {this.frequency = frequency;}


    protected Budget(Parcel in) {
        this.name = in.readString();
        this.amount = in.readDouble();
        this.category = in.readParcelable(Category.class.getClassLoader());
        //this.frequency = in.readByte() != 0;
        num_format = NumberFormat.getCurrencyInstance();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(this.amount);
        dest.writeParcelable(category,flags);
        //dest.writeByte((byte) (frequency ? 1 : 0));
    }


    @Override
    public int describeContents() {return 0;}

    public static final Creator<Budget> CREATOR = new Creator<Budget>() {
        @Override
        public Budget createFromParcel(Parcel in) {
            return new Budget(in);
        }

        @Override
        public Budget[] newArray(int size) {
            return new Budget[size];
        }
    };

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
        TextView overBudget = view.findViewById(R.id.over);
        TextView totalBudget = view.findViewById(R.id.total);
        TextView remainingBalance = view.findViewById(R.id.left); //set this textview

        categoryName.setText(this.cat);
        budgetName.setText(this.name);
        totalBudget.setText(num_format.format(this.amount));
        spentMoney.setText(num_format.format(this.currentValue));

        if(this.currentValue>this.amount) {
            overBudget.setText(num_format.format(this.currentValue - this.amount));
            remainingBalance.setText(num_format.format(0));
        }
        else{
            overBudget.setText(num_format.format(0));
            remainingBalance.setText(num_format.format(this.amount - this.currentValue));
        }


        return view;
    }
}
