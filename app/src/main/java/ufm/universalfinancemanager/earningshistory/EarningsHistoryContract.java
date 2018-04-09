package ufm.universalfinancemanager.earningshistory;

import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.ListItem;

/**
 * Created by Faiz on 2/26/2018.
 */

public class EarningsHistoryContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {
        void result(int requestCode, int resultCode);

        void loadTransactionsInDateRange();

        void takeView(EarningsHistoryContract.View view);

        void dropView();
    }
}
