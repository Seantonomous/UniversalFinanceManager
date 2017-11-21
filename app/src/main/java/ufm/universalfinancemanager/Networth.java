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

    public double totalAssets;
    public double totalLiabilities;
    public double totalNetWorth;
    private double amount;
    private Date date;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
    private NumberFormat num_format = NumberFormat.getCurrencyInstance();

    public Networth(double totalAssets, double totalLiabilities,
                    double totalNetWorth, double amount, Date date) {


        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.totalNetWorth = totalNetWorth;
        this.amount = amount;
        this.date = date;
    }


    public Networth(Parcel in) {
        totalAssets = in.readDouble();
        totalLiabilities = in.readDouble();
        totalNetWorth = in.readDouble();
        amount = in.readDouble();
        date = new Date(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.totalAssets);
        dest.writeDouble(this.totalLiabilities);
        dest.writeDouble(this.totalNetWorth);
        dest.writeDouble(this.amount);
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
//        accountText.setText(this.account.toString());
        // categoryText.setText(this.category.toString());

        return view;
    }

    /********Getters**********************/
    public double getAmount() {return amount;}
    public double getTotalAssets() {return totalAssets;}
    private double getTotalLiabilities() {return totalLiabilities;}
    private double getTotalNetowrth() { return totalNetWorth;}
//    private Date date;
//    public Account getAccount() {return account;}


    /********Setters*************************************/
    public void setAmount(double amount) {this.amount = amount;}
    public void setTotalAssets(double totalAssets) {this.totalAssets = totalAssets;}
    public void setTotalLiabilities(double totalLiabilities) {this.totalLiabilities = totalLiabilities;}
    public void setTotalNetWorth(double totalNetWorth) {this.totalNetWorth = totalNetWorth;}
//    public void setAccount(Account account) {this.account = account;}

}
