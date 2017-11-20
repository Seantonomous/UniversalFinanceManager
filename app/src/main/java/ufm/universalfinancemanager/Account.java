/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/14/17
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

public class Account implements Parcelable, ListItem {

    private String name;
    AccountType type;
    private double balance;
    private Date OpeningDate;
    private String notes;

    private NumberFormat num_format = NumberFormat.getCurrencyInstance();

    public Account(String name, AccountType type, double balance, Date openingDate, String notes) {
        this.name = name;
        this.type = type;
        this.balance = balance;
        OpeningDate = openingDate;
        this.notes = notes;
    }

    public Account(String name, AccountType type, double balance, Date openingDate) {
        this.name = name;
        this.type = type;
        this.balance = balance;
        OpeningDate = openingDate;
        this.notes = "";
    }

    /******************Getters**********************/
    public String getName() {return name;}
    public AccountType getType() {return type;}
    public double getBalance() {return balance;}
    public Date getOpeningDate() {return OpeningDate;}
    public String getNotes() {return notes;}

    /*****************Setters***********************/
    public void setName(String name) {this.name = name;}
    public void setType(AccountType type) {this.type = type;}
    public void setBalance(double balance) {this.balance = balance;}
    public void setOpeningDate(Date openingDate) {OpeningDate = openingDate;}
    public void setNotes(String notes) {this.notes = notes;}

    public void registerTransaction(Transaction t) {
        switch(type) {
            case CHECKING:
                switch(t.getFlow()) {
                    case OUTCOME:
                        balance -= t.getAmount();
                        break;
                    case INCOME:
                        balance += t.getAmount();
                        break;
                }
                break;
            case CREDIT_CARD:
                break;
        }
    }

    public Account(Parcel in) {
        name = in.readString();
        type = AccountType.valueOf(in.readString());
        balance = in.readDouble();
        OpeningDate = new Date(in.readLong());
        notes = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(type.name());
        dest.writeDouble(this.balance);
        dest.writeLong(OpeningDate.getTime());
        dest.writeString(notes);
    }

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
        TextView accountName = (TextView)view.findViewById(R.id.networth_account);
        TextView accountBalance = (TextView)view.findViewById(R.id.account_balance);


        //Set the text of each textview based on its corresponding transaction attribute
        accountName.setText(this.name);
        if (this.getType() == AccountType.CREDIT_CARD) {
            accountBalance.setText(num_format.format(this.balance*(-1)));
        }
        else {
            accountBalance.setText(num_format.format(this.balance));
        }

        return view;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        public Account createFromParcel(Parcel p) {
            return new Account(p);
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public String toString() {
        return this.getName();
    }
}