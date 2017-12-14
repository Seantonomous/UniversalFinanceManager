package ufm.universalfinancemanager.transactionhistory;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.di.ActivityScoped;

/**
 * Created by smh7 on 12/11/17.
 */

@ActivityScoped
public class TransactionHistoryPresenter implements TransactionHistoryContract.Presenter {
    private final TransactionRepository mTransactionRepository;

    @Nullable
    TransactionHistoryContract.View mTransactionHistoryView;

    private boolean firstLoad = true;

    @Inject
    TransactionHistoryPresenter(TransactionRepository transactionRepository) {
        mTransactionRepository = transactionRepository;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadTransactions() {
        mTransactionRepository.getTransactions(new TransactionDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                processTransactions(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });
    }

    public void processTransactions(List<Transaction> transactions) {
        if(transactions.isEmpty()) {
            if(mTransactionHistoryView != null)
                mTransactionHistoryView.showNoTransactions();
        }else {
            if(mTransactionHistoryView != null) {
                mTransactionHistoryView.showTransactions(transactions);
            }
        }
    }

    @Override
    public void addTransaction() {
        if(mTransactionHistoryView != null) {
            mTransactionHistoryView.showAddEditTransaction();
        }
    }

    @Override
    public void editTransaction(String transactionId) {
        if(mTransactionHistoryView != null) {
            mTransactionHistoryView.showAddEditTransaction(transactionId);
        }
    }

    @Override
    public void takeView(TransactionHistoryContract.View view) {
        this.mTransactionHistoryView = view;
        loadTransactions();
    }

    @Override
    public void dropView() {
        this.mTransactionHistoryView = null;
    }
}
