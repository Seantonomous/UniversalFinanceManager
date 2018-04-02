package ufm.universalfinancemanager.db;

import android.support.annotation.NonNull;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ufm.universalfinancemanager.db.entity.Account;
import ufm.universalfinancemanager.db.entity.Transaction;

/**
 * Created by smh7 on 12/11/17.
 */

public interface UserDataSource {
    interface LoadTransactionsCallback {
        void onTransactionsLoaded(List<Transaction> transactions);
        void onDataNotAvailable();
    }

    interface GetTransactionCallback {
        void onTransactionLoaded(Transaction transaction);
        void onDataNotAvailable();
    }

    interface LoadAccountsCallback {
        void onAccountsLoaded(List<Account> accounts);
        void onDataNotAvailable();
    }

    interface GetAccountCallback {
        void onAccountLoaded(Account account);
        void onDataNotAvailable();
    }

    void saveAccount(@NonNull Account account);

    void deleteAccount(@NonNull String accountName);

    void getAccounts(@NonNull LoadAccountsCallback callback);

    void getAccount(@NonNull String accountName, @NonNull GetAccountCallback callback);

    void getTransactions(@NonNull LoadTransactionsCallback callback);

    void getTransaction(@NonNull String transactionId, @NonNull GetTransactionCallback callback);

    void getTransactionsInDateRange(@NonNull final long date1, @NonNull final long date2,  @NonNull LoadTransactionsCallback callback);

    void saveTransaction(@NonNull Transaction transaction);

    void refreshTransactions();

    void deleteAllTransactions();

    void deleteTransaction(String transactionId);
}
