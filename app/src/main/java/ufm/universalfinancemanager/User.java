/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/13/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/

package ufm.universalfinancemanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import ufm.universalfinancemanager.Transaction;
import java.util.ArrayList;
import java.util.Locale;

public class User implements Parcelable {
    private String username;

    //Placeholder, won't actually be string
    private String password;

    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    //private ArrayList<Budget> budgets;
    private ArrayList<Category> categories;

    //Later
    //private ArrayList<Reminder> reminders;

    public User(String username) {
        this.username = username;
        this.password = "";
        this.accounts = new ArrayList<Account>();
        this.transactions = new ArrayList<Transaction>();
        this.categories = new ArrayList<Category>();
    }

    public User() {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public User(Parcel in) {
        this();
        this.username = in.readString();
        this.password = in.readString();
        in.readTypedList(this.accounts, Account.CREATOR);
        in.readTypedList(this.transactions, Transaction.CREATOR);
        in.readTypedList(this.categories, Category.CREATOR);
    }

    public User(String username, String password, ArrayList<Account> accounts,
                ArrayList<Transaction> transactions, ArrayList<Category> categories) {
        this.username = username;
        this.password = password;
        this.accounts = accounts;
        this.transactions = transactions;
        this.categories = categories;
    }

    public void setUserName(String name) {
        username = name;
    }

    public String getUserName() {
        return username;
    }

    public boolean addAccount(Account account) throws RuntimeException {
        for(int i=0;i<accounts.size();i++)
            if(accounts.get(i).getName().equals(account.getName())) {
                throw new RuntimeException("Account with same name already exists");
            }

        accounts.add(account);
        return true;
    }

    public Account getAccount(String name) throws RuntimeException {
        for(int i=0;i<accounts.size();i++)
            if(accounts.get(i).getName().equals(name))
                return accounts.get(i);

        throw new RuntimeException(String.format(Locale.getDefault(), "Account %s not found", name));
    }

    public boolean hasAccount(String name) {
        for(int i=0;i<accounts.size();i++) {
            if(accounts.get(i).getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    public boolean addTransaction(Transaction t) {
        transactions.add(t);
        t.getAccount().registerTransaction(t);
        return true;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    /*
    public boolean addBudget(Budget budget) {}
    public ArrayList<Budget> getBudgets() {}
    */

    public boolean addCategory(Category c) throws RuntimeException {
        for(int i=0;i<categories.size();i++)
            if(categories.get(i).getName().equals(c.getName()))
                throw new RuntimeException("Category with same name already exists");

        categories.add(c);
        return true;
    }

    public Category getCategory(String name) throws RuntimeException {
        for(int i=0;i<categories.size();i++)
            if(categories.get(i).getName().equals(name))
                return categories.get(i);

        throw new RuntimeException(String.format("Category %s not found", name));
    }

    public ArrayList<Category> getCategories() {
        return this.categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeTypedList(this.accounts);
        dest.writeTypedList(this.transactions);
        dest.writeTypedList(this.categories);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel p) {
            return new User(p);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
