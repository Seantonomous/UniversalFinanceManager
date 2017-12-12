package ufm.universalfinancemanager.transactionhistory;

import javax.annotation.Nullable;
import javax.inject.Inject;

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
    public void addNewTransaction() {

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
