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

import java.util.Date;

public class Transaction {
    private String name;
    private int flow;
    private double amount;
    private Category category;
    private Account account;
    private boolean frequency;
    private Date date;
    private String notes;

    public Transaction(String name, int flow, double amount, Category category,
                       Account account, Date date, String notes) {

        this.name = name;
        this.flow = flow;
        this.amount = amount;
        this.category = category;
        this.account = account;
        this.date = date;
        this.notes = notes;
    }

    public Transaction(String name, int flow, double amount, Category category,
                       Account account, Date date) {
        this.name = name;
        this.flow = flow;
        this.amount = amount;
        this.category = category;
        this.account = account;
        this.date = date;
    }

    /********Getters**********************/
    public String getName() {return name;}
    public int getFlow() {return flow;}
    public double getAmount() {return amount;}
    public Category getCategory() {return category;}
    public Account getAccount() {return account;}
    public boolean getFrequency() {return frequency;}
    public Date getDate() {return date;}
    public String getNotes() {return notes;}

    /********Setters*************************************/
    public void setName(String name) {this.name = name;}
    public void setFlow(int flow) {this.flow = flow;}
    public void setAmount(double amount) {this.amount = amount;}
    public void setCategory(Category category) {this.category = category;}
    public void setAccount(Account account) {this.account = account;}
    public void setFrequency(boolean frequency) {this.frequency = frequency;}
    public void setDate(Date date) {this.date = date;}
    public void setNotes(String notes) {this.notes = notes;}
}
