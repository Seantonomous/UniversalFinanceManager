package ufm.universalfinancemanager.db.source.local;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.util.AppExecutors;

/**
 * Created by smh7 on 12/11/17.
 */

public class TransactionLocalDataSource implements TransactionDataSource {
    private final TransactionDao mTransactionDao;
    private final AppExecutors mAppExecutors;

    @Inject
    TransactionLocalDataSource(@NonNull AppExecutors executors, @NonNull TransactionDao transactionDao) {
        mTransactionDao = transactionDao;
        mAppExecutors = executors;
    }

    @Override
    public void getTransactions(@NonNull final LoadTransactionsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Transaction> transactions = mTransactionDao.getAll();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (transactions.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onTransactionsLoaded(transactions);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getTransaction(@NonNull final String transactionId, @NonNull final GetTransactionCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Transaction transaction = mTransactionDao.getTransactionById(transactionId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (transaction != null) {
                            callback.onTransactionLoaded(transaction);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveTransaction(@NonNull final Transaction transaction) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mTransactionDao.insert(transaction);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void refreshTransactions() {
        //...
    }

    @Override
    public void deleteAllTransactions() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mTransactionDao.deleteAllTransactions();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteTransaction(@NonNull final String transactionId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mTransactionDao.deleteById(transactionId);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }
}
