package ufm.universalfinancemanager.db.source.local;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.entity.Account;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.util.AppExecutors;

/**
 * Created by smh7 on 12/11/17.
 */

public class UserLocalDataSource implements UserDataSource {
    private final TransactionDao mTransactionDao;
    private final AccountDao mAccountDao;
    private final AppExecutors mAppExecutors;

    @Inject
    UserLocalDataSource(@NonNull AppExecutors executors, @NonNull TransactionDao transactionDao,
                        @NonNull AccountDao accountDao) {
        mTransactionDao = transactionDao;
        mAccountDao = accountDao;
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
    public void getTransactionsInDateRange(@NonNull final long date1, @NonNull final long date2, @NonNull final LoadTransactionsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Transaction> transactions = mTransactionDao.getTransactionsInDateRange(date1, date2);

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

    @Override
    public void getAccounts(@NonNull final LoadAccountsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Account> accounts = mAccountDao.getAll();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (accounts.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onAccountsLoaded(accounts);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getAccount(@NonNull final String accountName, @NonNull final GetAccountCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Account account = mAccountDao.getAccountByName(accountName);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (account == null) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onAccountLoaded(account);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveAccount(@NonNull final Account account) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mAccountDao.insert(account);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void deleteAccount(@NonNull final String accountName) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mAccountDao.deleteByName(accountName);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }
}
