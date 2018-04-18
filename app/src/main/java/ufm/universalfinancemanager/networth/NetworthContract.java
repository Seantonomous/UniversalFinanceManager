package ufm.universalfinancemanager.networth;

import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.db.entity.Account;

/**
 * Created by smh7 on 2/28/18.
 */

public class NetworthContract {
    interface View extends BaseView<Presenter> {

        void showNetworth(List<Account> accounts);

        void showEditAccount(String accountName);
    }

    interface Presenter extends BasePresenter<View> {

        void editAccount(Account account);

        void takeView(NetworthContract.View view);

        void dropView();
    }
}
