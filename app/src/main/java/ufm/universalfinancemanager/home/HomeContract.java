package ufm.universalfinancemanager.home;

import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.db.entity.Transaction;

public interface HomeContract {

    interface View extends BaseView<Presenter> {
        // boolean isActive();
//        void showTransactions(List<Transaction> items);

        void populateList(List<Transaction> items);
    }

    interface Presenter extends BasePresenter<View> {

        void takeView(View v);

        void dropView();

        void loadTransactions();

    }
}
