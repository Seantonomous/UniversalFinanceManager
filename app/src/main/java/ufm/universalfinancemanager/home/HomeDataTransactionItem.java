package ufm.universalfinancemanager.home;

public class HomeDataTransactionItem {
    enum AcctType {Asset, Liability;}
    enum TransactionType {Credit, Debit;}

    protected AcctType varAcctType;
    protected TransactionType varTransactionType;
    protected float transactionAmount;
    protected int monthInt;

    HomeDataTransactionItem(
            AcctType varAcctType, TransactionType varTransactionType,
            float transactionAmount, int monthInt) {

        this.varAcctType = varAcctType;
        this.varTransactionType = varTransactionType;
        this.transactionAmount = transactionAmount;
        this.monthInt = monthInt;
    }

}
