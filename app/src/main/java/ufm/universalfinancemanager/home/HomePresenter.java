package ufm.universalfinancemanager.home;

import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.util.EspressoIdlingResource;

public class HomePresenter implements HomeContract.Presenter {
    private final TransactionRepository mTransactionRepository;

    // private final User mUser;

    @Nullable
    private HomeContract.View mHomeView = null;

    @Inject
    public HomePresenter(TransactionRepository transactionRepository) {
        mTransactionRepository = transactionRepository;
        // mUser = user;
    }


    @Override
    public void takeView(HomeContract.View v) {
        if(v == null)
            return;

        mHomeView = v;
    }

    @Override
    public void dropView() {
        mHomeView = null;
    }

    @Override
    public void loadTransactions() {
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

                processTransactions(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                //display loading transactions error message
            }
        });
    }

    public void processTransactions(List<Transaction> transactions) {
        mHomeView.populateList(transactions);

    }
}