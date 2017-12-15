package ufm.universalfinancemanager.addedittransaction;

import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.Account;
import ufm.universalfinancemanager.support.atomic.Category;
import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by smh7 on 12/14/17.
 */

public class AddEditTransactionPresenter implements AddEditTransactionContract.Presenter, TransactionDataSource.GetTransactionCallback {
    private final TransactionRepository mTransactionRepository;
    private final User mUser;
    private final String mTransactionId;

    @Nullable
    AddEditTransactionContract.View mAddEditTransactionView = null;

    @Inject
    AddEditTransactionPresenter(TransactionRepository repository, User user, @Nullable String id) {mTransactionRepository = repository;
    mUser = user; mTransactionId = id;}

    @Override
    public void saveTransaction(String name, Flow flow, Double amount,
                               String categoryName, String fromAccountName,
                               String toAccountName, Date date, String notes) {

        if(processInput(name, amount, flow)) {
            if(isNewTransaction())
                createTransaction(name, flow, amount,
                        categoryName, String fromAccountName,
                        toAccountName, date, notes);
            else
                updateTransaction(name, flow, amount,
                        categoryName, String fromAccountName,
                        toAccountName, date, notes);
        }
    }

    private void createTransaction(String name, Flow flow, Double amount,
                              String categoryName, String fromAccountName,
                              String toAccountName, Date date, String notes) {

        Transaction t = new Transaction(name, flow, amount,
                categoryName == null ? null : mUser.getCategory(categoryName),
                fromAccountName == null ? null : mUser.getAccount(fromAccountName),
                toAccountName == null ? null : mUser.getAccount(toAccountName), date, notes);

        mTransactionRepository.saveTransaction(t);
        if (mAddEditTransactionView != null) {
            mAddEditTransactionView.showLastActivity(true);
        }
    }

    private void updateTransaction(String name, Flow flow, Double amount,
                                   String categoryName, String fromAccountName,
                                   String toAccountName, Date date, String notes) {

        Transaction t = new Transaction(name, mTransactionId, flow, amount,
                categoryName == null ? null : mUser.getCategory(categoryName),
                fromAccountName == null ? null : mUser.getAccount(fromAccountName),
                toAccountName == null ? null : mUser.getAccount(toAccountName), date, notes);

        mTransactionRepository.saveTransaction(t);
        if (mAddEditTransactionView != null) {
            mAddEditTransactionView.showLastActivity(true);
        }
    }

    public boolean processInput(String name, Double amount, Flow flow) {
        if(mAddEditTransactionView != null)
            mAddEditTransactionView.displayInputError(name.isEmpty() && !(flow == Flow.TRANSFER), amount == 0);

        return ((!name.isEmpty() && !(flow == Flow.TRANSFER)) && !(amount == 0));
    }

    @Override
    public void onTransactionLoaded(Transaction transaction) {
        if (mAddEditTransactionView != null && mAddEditTransactionView.isActive()) {
            mAddEditTransactionView.populateExistingFields(transaction.getName(),
                    transaction.getAmount(), transaction.getFlow(), );
        }
    }

    @Override
    public void onDataNotAvailable() {

    }

    public boolean isNewTransaction() {
        return mTransactionId == null;
    }

    @Override
    public void takeView(AddEditTransactionContract.View v) {
        mAddEditTransactionView =v;
    }

    @Override
    public void dropView() {
        mAddEditTransactionView =null;
    }
}
