package ufm.universalfinancemanager.addedittransaction;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.UserDataSource;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.entity.Account;
import ufm.universalfinancemanager.db.entity.Category;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by smh7 on 12/14/17.
 */

public class AddEditTransactionPresenter implements AddEditTransactionContract.Presenter, UserDataSource.GetTransactionCallback {
    private final UserRepository mUserRepository;
    private final User mUser;
    private final String mTransactionId;

    private double beforeEditAmount;

    @Nullable
    AddEditTransactionContract.View mAddEditTransactionView = null;

    @Inject
    public AddEditTransactionPresenter(UserRepository repository, User user, @Nullable String id) {
        mUserRepository = repository;
        mUser = user;
        mTransactionId = id;
    }

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
        mUserRepository.getTransaction(mTransactionId, new UserDataSource.GetTransactionCallback() {
            @Override
            public void onTransactionLoaded(Transaction transaction) {
                Account toAccount;
                Account fromAccount;

                if(transaction.getFlow() == Flow.INCOME) {
                    toAccount = mUser.getAccount(transaction.getToAccount());
                    toAccount.unregisterTransaction(transaction);
                    mUserRepository.saveAccount(toAccount);

                    mUser.refreshAccounts();
                }else if(transaction.getFlow() == Flow.OUTCOME) {
                    fromAccount = mUser.getAccount(transaction.getFromAccount());
                    fromAccount.unregisterTransaction(transaction);
                    mUserRepository.saveAccount(fromAccount);

                    mUser.refreshAccounts();
                }else {
                    toAccount = mUser.getAccount(transaction.getToAccount());
                    toAccount.unregisterTransaction(transaction);
                    fromAccount = mUser.getAccount(transaction.getFromAccount());
                    fromAccount.unregisterTransaction(transaction);

                    mUserRepository.saveAccount(toAccount);
                    mUserRepository.saveAccount(fromAccount);

                    mUser.refreshAccounts();
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        mUserRepository.deleteTransaction(mTransactionId);

        if(mAddEditTransactionView != null) {
            mAddEditTransactionView.showLastActivity(true);
        }
    }

    private void createTransaction(String name, Flow flow, Double amount,
                              String categoryName, String fromAccountName,
                              String toAccountName, Date date, String notes) {

        final Transaction t = new Transaction(name, flow, amount,
                categoryName, fromAccountName, toAccountName, date, notes);

        mUserRepository.saveTransaction(t);


        //Update account balances
        if(flow == Flow.INCOME)
            mUserRepository.getAccount(toAccountName, new UserDataSource.GetAccountCallback() {
                @Override
                public void onAccountLoaded(Account account) {
                    account.registerTransaction(t);
                    mUserRepository.saveAccount(account);
                    mUser.refreshAccounts();
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        else if(flow == Flow.OUTCOME)
            mUserRepository.getAccount(fromAccountName, new UserDataSource.GetAccountCallback() {
                @Override
                public void onAccountLoaded(Account account) {
                    account.registerTransaction(t);
                    mUserRepository.saveAccount(account);
                    mUser.refreshAccounts();
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        else {
            mUserRepository.getAccount(toAccountName, new UserDataSource.GetAccountCallback() {
                @Override
                public void onAccountLoaded(Account account) {
                    account.registerTransaction(t);
                    mUserRepository.saveAccount(account);
                }

                @Override
                public void onDataNotAvailable() {

                }
            });

            mUserRepository.getAccount(fromAccountName, new UserDataSource.GetAccountCallback() {
                @Override
                public void onAccountLoaded(Account account) {
                    account.registerTransaction(t);
                    mUserRepository.saveAccount(account);
                }

                @Override
                public void onDataNotAvailable() {

                }
            });

            mUser.refreshAccounts();
        }

        if (mAddEditTransactionView != null) {
            mAddEditTransactionView.showLastActivity(true);
        }
    }

    private void updateTransaction(final String name, final Flow flow, final Double amount,
                                   final String categoryName, final String fromAccountName,
                                   final String toAccountName, final Date date, final String notes) {

        final Transaction t = new Transaction(name, mTransactionId, flow, amount,
                categoryName, fromAccountName, toAccountName, date, notes);

        mUserRepository.getTransaction(mTransactionId, new UserDataSource.GetTransactionCallback() {
            @Override
            public void onTransactionLoaded(Transaction transaction) {
                if(transaction.getFlow() == Flow.INCOME) {
                    Account account = mUser.getAccount(transaction.getToAccount());
                    account.unregisterTransaction(transaction);
                    mUser.editAccount(account);
                }else if(transaction.getFlow() == Flow.OUTCOME) {
                    Account account = mUser.getAccount(transaction.getFromAccount());
                    account.unregisterTransaction(transaction);
                    mUser.editAccount(account);
                }else {
                    Account account1 = mUser.getAccount(transaction.getFromAccount());
                    Account account2 = mUser.getAccount(transaction.getToAccount());
                    account1.unregisterTransaction(transaction);
                    account2.unregisterTransaction(transaction);
                    mUser.editAccount(account1);
                    mUser.editAccount(account2);
                }

                mUser.refreshAccounts();

                mUserRepository.deleteTransaction(mTransactionId);
                createTransaction(name, flow, amount, categoryName, fromAccountName,
                        toAccountName, date, notes);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void populateTransaction() {
        mUserRepository.getTransaction(mTransactionId, this);
    }

    @Override
    public void onTransactionLoaded(Transaction transaction) {
        if (mAddEditTransactionView != null && mAddEditTransactionView.isActive()) {
            mAddEditTransactionView.populateExistingFields(transaction.getName(),
                    transaction.getAmount(), transaction.getFlow(),
                    mUser.getCategory(transaction.getCategory()),
                    transaction.getFromAccount() == null ? null : mUser.getAccount(transaction.getFromAccount()),
                    transaction.getToAccount() == null ? null : mUser.getAccount(transaction.getToAccount()),
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

        if(mUser.getIncomeCategories().isEmpty()) {
            mAddEditTransactionView.disableIncome();
        }
        if(mUser.getExpenseCategories().isEmpty()) {
            mAddEditTransactionView.disableExpense();
        }
        if(mUser.getAccounts().isEmpty()) {
            mAddEditTransactionView.showError("You must add an account before you start " +
                    "recording transactions!");
        }

        if(isNewTransaction())
            mAddEditTransactionView.setupFragmentContent(mUser.getAccounts(), false);
        else {
            mAddEditTransactionView.setupFragmentContent(mUser.getAccounts(), true);
            populateTransaction();
        }
/*
        mUserRepository.getAccounts(new UserDataSource.LoadAccountsCallback() {
            @Override
            public void onAccountsLoaded(List<Account> accounts) {
                if(isNewTransaction())
                    mAddEditTransactionView.setupFragmentContent(accounts, false);
                else
                    mAddEditTransactionView.setupFragmentContent(accounts, true);
                    populateTransaction();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });*/
    }

    @Override
    public void dropView() {
        mAddEditTransactionView =null;
    }
}
