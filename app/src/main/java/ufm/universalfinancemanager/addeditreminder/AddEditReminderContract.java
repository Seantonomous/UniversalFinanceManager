package ufm.universalfinancemanager.addeditreminder;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;

/**
 * Created by Areeba on 2/17/2018.
 */

public interface AddEditReminderContract {
    interface View extends BaseView<AddEditReminderContract.Presenter> {

        void showLastActivity(boolean success);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter<AddEditReminderContract.View> {

        void takeView(AddEditReminderContract.View v);

        void dropView();
    }
}
