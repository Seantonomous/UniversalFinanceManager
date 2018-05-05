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

        void showEarningsHistory(List<EarningsHistoryListItem> list);

        void showNoEarningsHistory();

        void showEditCategory(String categoryName);
    }

    interface Presenter extends BasePresenter<View> {

        void loadTransactionsInDateRange();

        void takeView(EarningsHistoryContract.View view);

        void dropView();
    }
}
