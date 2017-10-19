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

import java.util.Date;

public class Account implements Parcelable {

    private String name;
    AccountType type;
    private double balance;
    private Date OpeningDate;
    private String notes;

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
            case DEBIT:
                break;
            case CREDIT:
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
        dest.writeString(name);
        dest.writeString(type.name());
        dest.writeDouble(balance);
        dest.writeLong(OpeningDate.getTime());
        dest.writeString(notes);
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
}