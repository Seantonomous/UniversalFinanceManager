package ufm.universalfinancemanager.reminderhistory;

import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.support.atomic.Reminder;

/**
 * Created by simranjeetkaur on 06/04/18.
 */

public class ReminderHistoryContract {
    public interface Presenter extends BasePresenter<View> {
        void loadReminders();

        void takeView(ReminderHistoryContract.View view);

        void dropView();
    }
    interface View extends BaseView<Presenter> {
        void showReminders(List<Reminder> Reminder);
    }
}
