package ufm.universalfinancemanager.addedittransaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.Account;
import ufm.universalfinancemanager.support.atomic.Category;

/**
 * Created by smh7 on 12/14/17.
 */

public interface AddEditTransactionContract {

    interface View extends BaseView<Presenter> {
        boolean isActive();

        void showLastActivity(boolean success);

        void setupFragmentContent(@Nullable List<Category> categories,
                                  @Nullable List<Account> accounts,
                                  boolean editing);

        void populateExistingFields(String name, Double amount, Flow flow,
                                  Category categoryName, @Nullable Account fromAccountName,
                                  @Nullable Account toAccountName, Date date, @Nullable String notes);
    }

    interface Presenter extends BasePresenter<View> {

        void saveTransaction(String name, Flow flow, Double amount,
                            String categoryName, String fromAccountName,
                            String toAccountName, Date date, String notes);

        void deleteTransaction();

        void takeView(View v);

        void dropView();
    }
}
