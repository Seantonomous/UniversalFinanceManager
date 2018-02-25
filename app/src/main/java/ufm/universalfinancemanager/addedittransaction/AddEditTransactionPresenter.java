package ufm.universalfinancemanager.addedittransaction;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

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

    private double beforeEditAmount;

    @Nullable
    AddEditTransactionContract.View mAddEditTransactionView = null;

    @Inject
    public AddEditTransactionPresenter(TransactionRepository repository, User user, @Nullable String id) {mTransactionRepository = repository;
        this.mUser = user; mTransactionId = id;}

    @Override
    public void saveTransaction(String name, Flow flow, Double amount,
                               String categoryName, String fromAccountName,
                               String toAccountName, Date date, String notes) {

        if(isNewTransaction())
            createTransaction(name, flow, amount,
                    categoryName, fromAccountName,
                    toAccountName, date, notes);
        else
            updateTransaction(name, flow, amount,
                    categoryName, fromAccountName,
                    toAccountName, date, notes);
    }

    @Override
    public void deleteTransaction() {
        mTransactionRepository.deleteTransaction(mTransactionId);

        if(mAddEditTransactionView != null) {
            mAddEditTransactionView.showLastActivity(true);
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

        //Update account balances
        if(flow == Flow.INCOME)
            mUser.getAccount(toAccountName).registerTransaction(t);
        else if(flow == Flow.OUTCOME)
            mUser.getAccount(fromAccountName).registerTransaction(t);
        else {
            mUser.getAccount(toAccountName).registerTransaction(t);
            mUser.getAccount(fromAccountName).registerTransaction(t);
        }

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

        if(beforeEditAmount != amount) {
            if(flow == Flow.INCOME) {
                mUser.getAccount(toAccountName).unregisterTransaction(t);
                mUser.getAccount(toAccountName).registerTransaction(t);
            } else if(flow == Flow.OUTCOME) {
                mUser.getAccount(fromAccountName).unregisterTransaction(t);
                mUser.getAccount(fromAccountName).registerTransaction(t);
            } else {
                mUser.getAccount(toAccountName).unregisterTransaction(t);
                mUser.getAccount(toAccountName).registerTransaction(t);

                mUser.getAccount(fromAccountName).unregisterTransaction(t);
                mUser.getAccount(fromAccountName).registerTransaction(t);
            }
        }

        mTransactionRepository.saveTransaction(t);
        if (mAddEditTransactionView != null) {
            mAddEditTransactionView.showLastActivity(true);
        }
    }

    public void populateTransaction() {
        mTransactionRepository.getTransaction(mTransactionId, this);
    }

    @Override
    public void onTransactionLoaded(Transaction transaction) {
        if (mAddEditTransactionView != null && mAddEditTransactionView.isActive()) {
            mAddEditTransactionView.populateExistingFields(transaction.getName(),
                    transaction.getAmount(), transaction.getFlow(), transaction.getCategory(),
                    transaction.getFromAccount(), transaction.getToAccount(),
                    transaction.getDate(), transaction.getNotes());

            beforeEditAmount = transaction.getAmount();
        }
    }

    @Override
    public void onDataNotAvailable() {

    }

    public boolean isNewTransaction() {
        return mTransactionId == null;
    }

    @Override
    public void getUpdatedCategories(Flow flow) {
        if(mAddEditTransactionView == null)
            return;

        if(flow == Flow.INCOME) {
            mAddEditTransactionView.updateCategories(mUser.getIncomeCategories());
        }else if(flow == Flow.OUTCOME) {
            mAddEditTransactionView.updateCategories(mUser.getExpenseCategories());
        }
    }

    @Override
    public void takeView(AddEditTransactionContract.View v) {
        mAddEditTransactionView =v;

        if(v == null)
            return;

        if(isNewTransaction())
            //Start w/ expense chosen
            mAddEditTransactionView.setupFragmentContent(mUser.getAccounts(), false);
        else {
            mAddEditTransactionView.setupFragmentContent(mUser.getAccounts(), true);
            populateTransaction();
        }
    }

    @Override
    public void dropView() {
        mAddEditTransactionView =null;
    }
}
