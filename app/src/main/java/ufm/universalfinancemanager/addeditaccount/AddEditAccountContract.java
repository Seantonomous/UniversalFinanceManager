package ufm.universalfinancemanager.addeditaccount;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.support.AccountType;

/**
 * Created by smh7 on 1/23/18.
 */

public interface AddEditAccountContract {
    interface View extends BaseView<Presenter> {
        void populateExistingAccountInfo(String name, double balance, AccountType type);

        void showLastActivity(boolean success);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void saveAccount(String name, double balance, AccountType type);

        void takeView(View v);

        void dropView();
    }
}
