/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/13/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/

package ufm.universalfinancemanager.support.atomic;

import java.io.Serializable;

import ufm.universalfinancemanager.db.entity.Account;
import ufm.universalfinancemanager.db.entity.Category;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;

import java.util.ArrayList;
import java.util.Locale;

public class User implements Serializable {
    private String username;

    //private ArrayList<Budget> budgets;
    private ArrayList<Category> incomeCategories;
    private ArrayList<Category> expenseCategories;

    //Later
    //private ArrayList<Reminder> reminders;

    public User(String username) {
        this.username = username;
        this.incomeCategories = new ArrayList<>();
        this.expenseCategories = new ArrayList<>();
    }

    public User() {
        this.incomeCategories = new ArrayList<>();
        this.expenseCategories = new ArrayList<>();
    }

    public User(String username, ArrayList<Category> categories) {
        this.username = username;
        this.incomeCategories = categories;
    }

    public void setUserName(String name) {
        username = name;
    }

    public String getUserName() {
        return username;
    }

    public boolean hasCategory(String name) {
        for(int i=0;i<incomeCategories.size();i++) {
            if(incomeCategories.get(i).getName().equals(name)) {
                return true;
            }
        }

        for(int i=0;i<expenseCategories.size();i++) {
            if(expenseCategories.get(i).getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    /*
    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    public boolean addBudget(Budget budget) {}
    public ArrayList<Budget> getBudgets() {}
    */

    public boolean addCategory(Category c) throws RuntimeException {
        if(c.getFlow() == Flow.INCOME) {
            for(int i=0;i<incomeCategories.size();i++)
                if(incomeCategories.get(i).getName().equals(c.getName()))
                    throw new RuntimeException("Category with same name already exists");
            incomeCategories.add(c);
        }else {
            for(int i=0;i<expenseCategories.size();i++)
                if(expenseCategories.get(i).getName().equals(c.getName()))
                    throw new RuntimeException("Category with same name already exists");
            expenseCategories.add(c);
        }

        return true;
    }

    public Category getCategory(String name) throws RuntimeException {
        for(int i=0;i<incomeCategories.size();i++)
            if(incomeCategories.get(i).getName().equals(name))
                return incomeCategories.get(i);

        for(int i=0;i<expenseCategories.size();i++)
            if(expenseCategories.get(i).getName().equals(name))
                return expenseCategories.get(i);

        throw new RuntimeException(String.format("Category %s not found", name));
    }

    public ArrayList<Category> getIncomeCategories() {
        return this.incomeCategories;
    }
    public ArrayList<Category> getExpenseCategories() { return this.expenseCategories; }

}
