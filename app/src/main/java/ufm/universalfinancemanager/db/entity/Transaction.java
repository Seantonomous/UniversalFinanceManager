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
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Color;
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
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.db.source.local.converter.DateConverter;
import ufm.universalfinancemanager.db.source.local.converter.FlowConverter;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

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

    @ColumnInfo(name = "category_name")
    private String category;

    private String fromAccount;

    private String toAccount;

    private boolean frequency;

    @TypeConverters(DateConverter.class)
    private Date date;

    private String notes;

    @Ignore
    private static SimpleDateFormat dateFormat;
    @Ignore
    private NumberFormat num_format;

    @Ignore
    public Transaction(String name, Flow flow, double amount, String category,
                       String fromAccount, String toAccount, Date date, String notes) {
        this(name, UUID.randomUUID().toString(),flow,amount,category,fromAccount,toAccount,date,notes);
    }

    @Ignore
    public Transaction(String name, Flow flow, double amount, String category,
                       String fromAccount, String toAccount, Date date) {
        this(name, UUID.randomUUID().toString(),flow,amount,category,fromAccount,toAccount,date,"");
    }

    public Transaction(String name, String id,Flow flow, double amount, String category,
                       String fromAccount, String toAccount, Date date, String notes) {
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

    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if(convertView == null)
            view = inflater.inflate(R.layout.transaction_list_item, null);
        else
            view = convertView;

        //Instantiate all the textviews from the layout
        TextView nameText = view.findViewById(R.id.trans_name);
        TextView amountText = view.findViewById(R.id.trans_amount);
        TextView accountText = view.findViewById(R.id.trans_account);
        TextView categoryText = view.findViewById(R.id.trans_category);

        //Set the text of each textview based on its corresponding transaction attribute
        nameText.setText(this.name);
        amountText.setText(num_format.format(this.amount));

        //set text color based on flow type
        if(flow == Flow.OUTCOME)
            amountText.setTextColor(Color.rgb(183, 71, 71)); //red
        else if(flow == Flow.INCOME)
            amountText.setTextColor(Color.rgb(84, 175, 120)); //green
        else if(flow == Flow.TRANSFER)
            amountText.setTextColor(Color.rgb(71, 138, 183)); //blue

        if(fromAccount != null)
            accountText.setText(this.fromAccount);
        else
            accountText.setText(this.toAccount);

        if(category != null)
            categoryText.setText(this.category);
        else
            categoryText.setText("No Category");

        return view;
    }

    /********Getters**********************/
    public String getName() {return name;}
    public Flow getFlow() {return flow;}
    public double getAmount() {return amount;}
    public String getCategory() {return category;}
    public String getFromAccount() {return fromAccount;}
    public String getToAccount() {return toAccount;}
    public boolean getFrequency() {return frequency;}
    public Date getDate() {return date;}
    public String getNotes() {return notes;}
    public String getId() {return this.id;}

    /********Setters*************************************/
    public void setName(String name) {this.name = name;}
    public void setFlow(Flow flow) {this.flow = flow;}
    public void setAmount(double amount) {this.amount = amount;}
    public void setCategory(String category) {this.category = category;}
    public void setFromAccount(String account) {this.fromAccount = account;}
    public void setToAccount(String account) {this.toAccount = account;}
    public void setFrequency(boolean frequency) {this.frequency = frequency;}
    public void setDate(Date date) {this.date = date;}
    public void setNotes(String notes) {this.notes = notes;}
    public void setId(String id) {this.id = id;}
}
