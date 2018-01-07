/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/13/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members: Daniel Karapetian
*/

package ufm.universalfinancemanager.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.RowType;
import ufm.universalfinancemanager.support.atomic.Account;
import ufm.universalfinancemanager.support.atomic.Category;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.db.source.local.converter.DateConverter;
import ufm.universalfinancemanager.db.source.local.converter.FlowConverter;

@Entity
public class Transaction implements ListItem, Serializable {

    @PrimaryKey
    @NonNull
    public String id;

    private String name;

    @TypeConverters(FlowConverter.class)
    @ColumnInfo(name = "transaction_flow")
    private Flow flow;

    private double amount;

    @Embedded
    private Category category;

    @Embedded(prefix = "from")
    private Account fromAccount;

    @Embedded(prefix = "to")
    private Account toAccount;

    private boolean frequency;

    @TypeConverters(DateConverter.class)
    private Date date;

    private String notes;

    @Ignore
    private static SimpleDateFormat dateFormat;
    @Ignore
    private NumberFormat num_format;

    @Ignore
    public Transaction(String name, Flow flow, double amount, Category category,
                       Account fromAccount, Account toAccount, Date date, String notes) {
        this(name, UUID.randomUUID().toString(),flow,amount,category,fromAccount,toAccount,date,notes);
    }

    @Ignore
    public Transaction(String name, Flow flow, double amount, Category category,
                       Account fromAccount, Account toAccount, Date date) {
        this(name, UUID.randomUUID().toString(),flow,amount,category,fromAccount,toAccount,date,"");
    }

    public Transaction(String name, String id,Flow flow, double amount, Category category,
                       Account fromAccount, Account toAccount, Date date, String notes) {
        this.name = name;
        this.id = id;
        this.flow = flow;
        this.amount = amount;
        this.category = category;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.date = date;
        this.notes = notes;

        dateFormat = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
        num_format = NumberFormat.getCurrencyInstance();
    }
/*
    public Transaction(Parcel in) {
        name = in.readString();
        flow = Flow.valueOf(in.readString());
        amount = in.readDouble();
        category = new Category(in.readString(), flow);
        account = in.readParcelable(Account.class.getClassLoader());
        date = new Date(in.readLong());

        dateFormat = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
        num_format = NumberFormat.getCurrencyInstance();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(flow.name());
        dest.writeDouble(this.amount);
        dest.writeString(this.category.toString());
        dest.writeParcelable(this.account, flags);
        dest.writeLong(this.date.getTime());
    }

    //Needed for parcelable types
    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        public Transaction createFromParcel(Parcel p) {
            return new Transaction(p);
        }

        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
*/
    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if(convertView == null)
            view = (View)inflater.inflate(R.layout.transaction_list_item, null);
        else
            view = convertView;

        //Instantiate all the textviews from the layout
        TextView dateText = view.findViewById(R.id.trans_date);
        TextView nameText = view.findViewById(R.id.trans_name);
        TextView amountText = view.findViewById(R.id.trans_amount);
        TextView accountText = view.findViewById(R.id.trans_account);
        TextView categoryText = view.findViewById(R.id.trans_category);

        //Set the text of each textview based on its corresponding transaction attribute
        dateText.setText(dateFormat.format(this.date));
        nameText.setText(this.name);
        amountText.setText(num_format.format(this.amount));

        if(fromAccount != null)
            accountText.setText(this.fromAccount.toString());
        else
            accountText.setText(this.toAccount.toString());
        categoryText.setText(this.category.toString());

        return view;
    }

    /********Getters**********************/
    public String getName() {return name;}
    public Flow getFlow() {return flow;}
    public double getAmount() {return amount;}
    public Category getCategory() {return category;}
    public Account getFromAccount() {return fromAccount;}
    public Account getToAccount() {return toAccount;}
    public boolean getFrequency() {return frequency;}
    public Date getDate() {return date;}
    public String getNotes() {return notes;}
    public String getId() {return this.id;}

    /********Setters*************************************/
    public void setName(String name) {this.name = name;}
    public void setFlow(Flow flow) {this.flow = flow;}
    public void setAmount(double amount) {this.amount = amount;}
    public void setCategory(Category category) {this.category = category;}
    public void setFromAccount(Account account) {this.fromAccount = account;}
    public void setToAccount(Account account) {this.toAccount = account;}
    public void setFrequency(boolean frequency) {this.frequency = frequency;}
    public void setDate(Date date) {this.date = date;}
    public void setNotes(String notes) {this.notes = notes;}
    public void setId(String id) {this.id = id;}
}
