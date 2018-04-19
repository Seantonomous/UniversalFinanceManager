package ufm.universalfinancemanager.transactionhistory;

import android.widget.SearchView;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.util.EspressoIdlingResource;

/**
 * Created by smh7 on 12/11/17.
 */

@ActivityScoped
public class TransactionHistoryPresenter implements TransactionHistoryContract.Presenter {
    private final UserRepository mTransactionRepository;

    @Nullable
    TransactionHistoryContract.View mTransactionHistoryView;

    private boolean firstLoad = true;

    @Inject
    TransactionHistoryPresenter(UserRepository transactionRepository) {
        mTransactionRepository = transactionRepository;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadTransactions() {
        //Performing database/network call, forbid ui test activity until it's done
        EspressoIdlingResource.increment();

        mTransactionRepository.getTransactions(new UserDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                processTransactions(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });
    }



    @Override
    public void loadTransactionsByName(String name) {
        //Performing database/network call, forbid ui test activity until it's done
        EspressoIdlingResource.increment();


        mTransactionRepository.getTransactionsSearchByName(new UserDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                processTransactions(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        },"%"+name+"%");
    }

    public void processTransactions(List<Transaction> transactions) {
        if(transactions.isEmpty()) {
            if(mTransactionHistoryView != null) {
                mTransactionHistoryView.showNoTransactions();
            }
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
