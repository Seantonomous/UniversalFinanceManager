package ufm.universalfinancemanager.home;

import java.util.ArrayList;
import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.atomic.User;

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void populateList(List<Transaction> items);

        void populateCategories(ArrayList<HomeDataCategory> items);

        void getList(List<Transaction> items);

    }

    interface Presenter extends BasePresenter<View> {

        void result(int requestCode, int resultCode);

        void takeView(View v, int type);

        void dropView();

        void loadTransactions();
    }
}
