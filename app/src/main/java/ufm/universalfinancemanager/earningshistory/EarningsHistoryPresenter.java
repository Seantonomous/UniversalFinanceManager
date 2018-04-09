package ufm.universalfinancemanager.earningshistory;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.util.EspressoIdlingResource;

/**
 * Created by Faiz on 2/26/2018.
 */

@ActivityScoped
public class EarningsHistoryPresenter implements EarningsHistoryContract.Presenter {
    private final TransactionRepository mTransactionRepository;

    @Nullable
    EarningsHistoryContract.View mEarningsHistoryView;

    private boolean firstLoad = true;

    @Inject
    EarningsHistoryPresenter(TransactionRepository transactionRepository) {
        mTransactionRepository = transactionRepository;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadTransactionsInDateRange() {
        //Performing database/network call, forbid ui test activity until it's done
        EspressoIdlingResource.increment();

        mTransactionRepository.getTransactions(new TransactionDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                //processTransactions(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });
    }

    @Override
    public void takeView(EarningsHistoryContract.View view) {
        this.mEarningsHistoryView = view;
        loadTransactionsInDateRange();
    }

    @Override
    public void dropView() {
        this.mEarningsHistoryView = null;
    }

}
