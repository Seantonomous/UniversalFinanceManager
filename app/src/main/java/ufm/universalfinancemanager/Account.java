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

import java.util.Date;
import ufm.universalfinancemanager.Transaction;

public class Account {
    private enum AccountType {
        CREDIT, DEBIT, CASH
    }

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
}