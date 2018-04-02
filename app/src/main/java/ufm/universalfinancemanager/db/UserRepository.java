package ufm.universalfinancemanager.db;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import ufm.universalfinancemanager.db.entity.Account;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.db.source.Local;
import ufm.universalfinancemanager.db.source.Remote;

/**
 * Created by smh7 on 12/11/17.
 */

@Singleton
public class UserRepository implements UserDataSource {

    private final UserDataSource mLocalSource;
    private final UserDataSource mRemoteSource;

    Map<String, Transaction> mCachedTransactions;

    boolean mCacheDirty = false;

    @Inject
    UserRepository(@Local UserDataSource localSource,
                   @Remote UserDataSource remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public void getTransactions(@NonNull final LoadTransactionsCallback callback) {
        if(mCachedTransactions != null && !mCacheDirty) {
            callback.onTransactionsLoaded(new ArrayList<>(mCachedTransactions.values()));
            return;
        }

        if(mCacheDirty) {
            getTransactionsFromRemoteDataSource(callback);
        }else {
            mLocalSource.getTransactions(new LoadTransactionsCallback() {
                @Override
                public void onTransactionsLoaded(List<Transaction> transactions) {
                    refreshCache(transactions);
                    callback.onTransactionsLoaded(new ArrayList<>(mCachedTransactions.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getTransactionsFromRemoteDataSource(callback);
                }
            });
        }
    }

    private void getTransactionsFromRemoteDataSource(@NonNull final LoadTransactionsCallback callback) {
        mRemoteSource.getTransactions(new LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                refreshCache(transactions);
                refreshLocalDataSource(transactions);
                callback.onTransactionsLoaded(new ArrayList<>(mCachedTransactions.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Transaction> transactions) {
        if (mCachedTransactions == null) {
            mCachedTransactions = new LinkedHashMap<>();
        }
        mCachedTransactions.clear();
        for (Transaction task : transactions) {
            mCachedTransactions.put(task.getId(), task);
        }
        mCacheDirty = false;
    }

    private void refreshLocalDataSource(List<Transaction> transactions) {
        mLocalSource.deleteAllTransactions();
        for (Transaction transaction : transactions) {
            mLocalSource.saveTransaction(transaction);
        }
    }

    @Override
    public void getTransaction(@NonNull final String transactionId, @NonNull final GetTransactionCallback callback) {
        Transaction cachedTransaction = getTransactionWithId(transactionId);

        if(cachedTransaction != null) {
            callback.onTransactionLoaded(cachedTransaction);
            return;
        }

        mLocalSource.getTransaction(transactionId, new GetTransactionCallback() {
            @Override
            public void onTransactionLoaded(Transaction transaction) {
                if(mCachedTransactions == null) {
                    mCachedTransactions = new LinkedHashMap<>();
                }
                mCachedTransactions.put(transactionId, transaction);
                callback.onTransactionLoaded(transaction);
            }

            @Override
            public void onDataNotAvailable() {
                mRemoteSource.getTransaction(transactionId, new GetTransactionCallback() {
                    @Override
                    public void onTransactionLoaded(Transaction transaction) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedTransactions == null) {
                            mCachedTransactions = new LinkedHashMap<>();
                        }
                        mCachedTransactions.put(transaction.getId(), transaction);
                        callback.onTransactionLoaded(transaction);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void getTransactionsInDateRange(@NonNull final long date1, @NonNull final long date2,  @NonNull final LoadTransactionsCallback callback) {
        if(mCachedTransactions != null && !mCacheDirty) {
            callback.onTransactionsLoaded(new ArrayList<>(mCachedTransactions.values()));
            return;
        }

        if(mCacheDirty) {
            getTransactionsFromRemoteDataSource(callback);
        }else {
            mLocalSource.getTransactionsInDateRange(date1, date2, new LoadTransactionsCallback() {
                @Override
                public void onTransactionsLoaded(List<Transaction> transactions) {
                    refreshCache(transactions);
                    callback.onTransactionsLoaded(new ArrayList<>(mCachedTransactions.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getTransactionsFromRemoteDataSource(callback);
                }
            });
        }
    }

    private Transaction getTransactionWithId(String transactionId) {
        if (mCachedTransactions == null || mCachedTransactions.isEmpty()) {
            return null;
        } else {
            return mCachedTransactions.get(transactionId);
        }
    }

    @Override
    public void saveTransaction(@NonNull Transaction transaction) {
        mLocalSource.saveTransaction(transaction);
        mRemoteSource.saveTransaction(transaction);

        if(mCachedTransactions == null) {
            mCachedTransactions = new LinkedHashMap<>();
        }
        mCachedTransactions.put(transaction.getId(), transaction);
    }

    @Override
    public void refreshTransactions() {
        mCacheDirty = true;
    }

    @Override
    public void deleteAllTransactions() {
        mRemoteSource.deleteAllTransactions();
        mLocalSource.deleteAllTransactions();

        if (mCachedTransactions == null) {
            mCachedTransactions = new LinkedHashMap<>();
        }
        mCachedTransactions.clear();
    }

    @Override
    public void deleteTransaction(String transactionId) {
        mLocalSource.deleteTransaction(transactionId);
        mRemoteSource.deleteTransaction(transactionId);

        mCachedTransactions.remove(transactionId);
    }



    @Override
    public void getAccounts(@NonNull final LoadAccountsCallback callback) {
        mLocalSource.getAccounts(new LoadAccountsCallback() {
            @Override
            public void onAccountsLoaded(List<Account> accounts) {
                callback.onAccountsLoaded(accounts);
            }

            @Override
            public void onDataNotAvailable() {
                //TODO: THROW EXCEPTION
            }
        });
    }

    @Override
    public void getAccount(@NonNull String accountName, @NonNull final GetAccountCallback callback) {
        mLocalSource.getAccount(accountName, new GetAccountCallback() {
            @Override
            public void onAccountLoaded(Account account) {
                callback.onAccountLoaded(account);
            }

            @Override
            public void onDataNotAvailable() {
                //TODO: THROW EXCEPTION
            }
        });
    }

    @Override
    public void saveAccount(@NonNull Account account) {
        mLocalSource.saveAccount(account);
    }

    @Override
    public void deleteAccount(@NonNull String accountName) {
        mLocalSource.deleteAccount(accountName);
    }
}
