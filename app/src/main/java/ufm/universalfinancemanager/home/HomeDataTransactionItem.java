package ufm.universalfinancemanager.home;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeDataTransactionItem {
    enum AcctType {Asset, Liability;}
    enum TransactionType {Credit, Debit;}

    protected String accountName;
    protected AcctType varAcctType;
    protected TransactionType varTransactionType;
    protected float transactionAmount;
    protected int monthInt;
    protected float monthEndAssetBalance;
    protected float monthEndDebtBalance;

    HomeDataTransactionItem() {};

    HomeDataTransactionItem(
            String accountName, AcctType varAcctType,TransactionType varTransactionType,
            float transactionAmount, int monthInt) {

        this.accountName = accountName;
        this.varAcctType = varAcctType;
        this.varTransactionType = varTransactionType;
        this.transactionAmount = transactionAmount;
        this.monthInt = monthInt;
        monthEndAssetBalance = 0;
        monthEndDebtBalance = 0;
    }

    protected int getMonthInt(Date transactionDate) {
        int month;
        Calendar cal = Calendar.getInstance();

        // Date date = new Date();
        cal.setTimeInMillis(transactionDate.getTime());
        month = cal.get(Calendar.MONTH);

        return month;
    }

    protected AcctType getAccountType(String acctName, List<String> creditCardList) {

        for (int i = 0; i < creditCardList.size(); i++) {
            if (acctName.equals(creditCardList.get(i))) {
                return AcctType.Liability;
            }
        }

        return varAcctType.Asset;
    }
}
