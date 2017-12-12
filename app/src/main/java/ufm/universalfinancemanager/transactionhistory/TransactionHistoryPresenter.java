package ufm.universalfinancemanager.transactionhistory;

import javax.annotation.Nullable;
import javax.inject.Inject;

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
