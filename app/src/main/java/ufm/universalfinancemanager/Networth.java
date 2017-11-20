/* Author: Aaron O'Connor
* Date Started: 11/19/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/

package ufm.universalfinancemanager;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Networth implements Parcelable, ListItem {
    private String name;
    private double totalAssets;
    private double totalLiabilities;
    private double totalNetWorth;
    private double amount;
    private Account account;
    private Date date;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
    private NumberFormat num_format = NumberFormat.getCurrencyInstance();

    public Networth(String name, double totalAssets, double totalLiabilities,
                    double totalNetWorth, double amount, Account account, Date date) {

        this.name = name;
        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.totalNetWorth = totalNetWorth;
        this.amount = amount;
        this.account = account;
        this.date = date;
    }

//    public Networth(String name, Flow flow, double amount, Category category,
//                    Account account, Date date) {
//        this.name = name;
//        this.flow = flow;
//        this.amount = amount;
//        this.category = category;
//        this.account = account;
//        this.date = date;
//    }

    public Networth(Parcel in) {
        name = in.readString();
        totalAssets = in.readDouble();
        totalLiabilities = in.readDouble();
        totalNetWorth = in.readDouble();
        amount = in.readDouble();
        account = in.readParcelable(Account.class.getClassLoader());
        date = new Date(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeDouble(this.totalAssets);
        dest.writeDouble(this.totalLiabilities);
        dest.writeDouble(this.totalNetWorth);
        dest.writeDouble(this.amount);
        dest.writeParcelable(this.account, flags);
        dest.writeLong(this.date.getTime());
    }

    //Needed for parcelable types
    public static final Creator<Networth> CREATOR = new Creator<Networth>() {
        public Networth createFromParcel(Parcel p) {
            return new Networth(p);
        }

        public Networth[] newArray(int size) {
            return new Networth[size];
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
            view = (View)inflater.inflate(R.layout.net_worth_list_item, null);
        else
            view = convertView;

        //Instantiate all the textviews from the layout
        TextView amountText = (TextView)view.findViewById(R.id.account_balance);
        TextView accountText = (TextView)view.findViewById(R.id.networth_account);
        // TextView categoryText = (TextView)view.findViewById(R.id.trans_category);

        //Set the text of each textview based on its corresponding transaction attribute
        amountText.setText(num_format.format(this.amount));
        accountText.setText(this.account.toString());
        // categoryText.setText(this.category.toString());

        return view;
    }

    /********Getters**********************/
    public String getName() {return name;}
    public double getAmount() {return amount;}
    public Account getAccount() {return account;}


    /********Setters*************************************/
    public void setName(String name) {this.name = name;}
    public void setAmount(double amount) {this.amount = amount;}
    public void setAccount(Account account) {this.account = account;}

}
