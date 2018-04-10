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

import android.arch.persistence.room.Insert;

import java.io.Serializable;

import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.entity.Account;
import ufm.universalfinancemanager.db.entity.Category;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class User implements Serializable {
    private String username;

    private ArrayList<Account> accounts;
    //private ArrayList<Budget> budgets;
    private ArrayList<Category> incomeCategories;
    private ArrayList<Category> expenseCategories;
    //private ArrayList<Reminder> reminders;

    UserRepository mUserRepository;

    public User(String username, UserRepository userRepository) {
        this.username = username;
        this.accounts = new ArrayList<>();
        this.incomeCategories = new ArrayList<>();
        this.expenseCategories = new ArrayList<>();

        mUserRepository = userRepository;

        refreshAccounts();
        refreshCategories();
    }

    public User() {
        this.accounts = new ArrayList<>();
        this.incomeCategories = new ArrayList<>();
        this.expenseCategories = new ArrayList<>();
    }

    public User(String username, ArrayList<Account> accounts,
                ArrayList<Category> categories) {
        this.username = username;
        this.accounts = accounts;
        this.incomeCategories = categories;
    }

    public void setUserName(String name) {
        username = name;
    }

    public String getUserName() {
        return username;
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
    public ArrayList<Category> getIncomeCategories() {
        return this.incomeCategories;
    }
    public ArrayList<Category> getExpenseCategories() { return this.expenseCategories; }

    public void addAccount(Account account) throws RuntimeException {
        for(Account c_account: accounts)
            if(c_account.getName().equals(account.getName()))
                throw new RuntimeException("Account with same name already exists: " +
                        account.toString());

        accounts.add(account);
        mUserRepository.saveAccount(account);
    }

    public void deleteAccount(Account account) {
        accounts.remove(account);
        mUserRepository.deleteAccount(account.getName());
    }

    public void editAccountName(String oldName, String newName) {
        Account toEdit = getAccount(oldName);
        toEdit.setName(newName);

        mUserRepository.saveAccount(toEdit);
    }

    public Account getAccount(String name) throws RuntimeException {
        for(Account account : accounts)
            if(account.getName().equals(name))
                return account;

        throw new RuntimeException(String.format(Locale.getDefault(), "Account %s not found", name));
    }

    public boolean hasAccount(String name) {
        for(Account account : accounts)
            if(account.getName().equals(name))
                return true;

        return false;
    }

    /*
    public boolean addBudget(Budget budget) {}
    public ArrayList<Budget> getBudgets() {}
    */

    public boolean addCategory(Category c) throws RuntimeException {
        if(c.getFlow() == Flow.INCOME) {
            for(Category category : incomeCategories)
                if(category.getName().equals(c.getName()))
                    throw new RuntimeException("Category with same name already exists");
            incomeCategories.add(c);
        }else {
            for(Category category : expenseCategories)
                if(category.getName().equals(c.getName()))
                    throw new RuntimeException("Category with same name already exists");
            expenseCategories.add(c);
        }

        mUserRepository.saveCategory(c);

        return true;
    }

    public boolean hasCategory(String name) {
        for(Category category : incomeCategories)
            if(category.getName().equals(name))
                return true;

        for(Category category : expenseCategories)
            if(category.getName().equals(name))
                return true;

        return false;
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

    public void deleteCategory(Category category) {
        if(category.getFlow() == Flow.INCOME)
            incomeCategories.remove(category);
        else
            expenseCategories.remove(category);

        mUserRepository.deleteAccount(category.getName());
    }

    public void refresh() {
        refreshAccounts();
    }

    public void refreshAccounts() {
        mUserRepository.getAccounts(new UserDataSource.LoadAccountsCallback() {
            @Override
            public void onAccountsLoaded(List<Account> loaded_accounts) {
                accounts.clear();
                accounts = (ArrayList<Account>)loaded_accounts;
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void refreshCategories() {
        mUserRepository.getCategories(new UserDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<Category> loaded_categories) {
                ArrayList<Category> tempCategories = (ArrayList<Category>)loaded_categories;

                incomeCategories.clear();
                expenseCategories.clear();

                for(Category category : tempCategories)
                    if(category.getFlow() == Flow.OUTCOME)
                        expenseCategories.add(category);
                    else if(category.getFlow() == Flow.INCOME)
                        incomeCategories.add(category);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
