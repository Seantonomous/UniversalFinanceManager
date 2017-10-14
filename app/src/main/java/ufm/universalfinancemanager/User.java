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

public class User implements Serializable {
    private String username;

    //Placeholder, won't actually be string
    private String password;

    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private Arraylist<Budget> budgets;
    private ArrayList<Category> categories;

    //Later
    //private ArrayList<Reminder> reminders;

    public User(String name) {
        username = name;
    }

    public void setUserName(String name) {
        username = name;
        return;
    }

    public String getUserName() {
        return username;
    }

    public boolean addAccount(Account account) {}
    public Account getAccount(String name) {}
    public ArrayList<Account> getAccounts() {}

    public boolean addTransaction(Transaction transaction) {}
    public ArrayList<Transaction> getTransactions() {}

    public boolean addBudget(Budget budget) {}
    public ArrayList<Budget> getBudgets() {}

    public boolean addCategory(Category category) {}
    public Category getCategory(String name) {}
    public ArrayList<Category> getCategories() {}

}
