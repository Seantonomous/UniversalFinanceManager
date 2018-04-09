package ufm.universalfinancemanager.db.source;

/**
 * Created by smh7 on 12/13/17.
 */

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.entity.Transaction;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
public class FakeTransactionRemoteDataSource implements TransactionDataSource {

    private static final Map<String, Transaction> TRANSACTIONS_SERVICE_DATA = new LinkedHashMap<>();

    @Inject
    public FakeTransactionRemoteDataSource() {}

    @Override
    public void getTransactions(@NonNull LoadTransactionsCallback callback) {
        callback.onTransactionsLoaded(new ArrayList<>(TRANSACTIONS_SERVICE_DATA.values()));
    }

    //
    @Override
    public void getTransactionsSearchByName(@NonNull LoadTransactionsCallback callback, @Nonnull String name) {
        callback.onTransactionsLoaded(new ArrayList<>(TRANSACTIONS_SERVICE_DATA.values()));
    }


    @Override
    public void getTransaction(@NonNull String taskId, @NonNull GetTransactionCallback callback) {
        Transaction transaction = TRANSACTIONS_SERVICE_DATA.get(taskId);
        callback.onTransactionLoaded(transaction);
    }

    @Override
    public void getTransactionsInDateRange(@NonNull final long date1, @NonNull final long date2,  @NonNull LoadTransactionsCallback callback){
        callback.onTransactionsLoaded(new ArrayList<>(TRANSACTIONS_SERVICE_DATA.values()));
    }

    @Override
    public void saveTransaction(@NonNull Transaction task) {
        TRANSACTIONS_SERVICE_DATA.put(task.getId(), task);
    }

    public void refreshTransactions() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteTransaction(@NonNull String taskId) {
        TRANSACTIONS_SERVICE_DATA.remove(taskId);
    }

    @Override
    public void deleteAllTransactions() {
        TRANSACTIONS_SERVICE_DATA.clear();
    }

    @VisibleForTesting
    public void addTransactions(Transaction... transactions) {
        for (Transaction transaction : transactions) {
            TRANSACTIONS_SERVICE_DATA.put(transaction.getId(), transaction);
        }
    }
}
