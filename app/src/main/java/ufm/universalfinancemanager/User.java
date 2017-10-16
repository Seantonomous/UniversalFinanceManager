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

import java.io.Serializable;
import ufm.universalfinancemanager.Transaction;
import java.util.ArrayList;
import java.util.Locale;

public class User implements Serializable {
    private String username;

    //Placeholder, won't actually be string
    private String password;

    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    //private ArrayList<Budget> budgets;
    private ArrayList<Category> categories;

    //Later
    //private ArrayList<Reminder> reminders;

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

}
