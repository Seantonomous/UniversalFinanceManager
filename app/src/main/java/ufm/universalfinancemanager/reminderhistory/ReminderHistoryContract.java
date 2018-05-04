package ufm.universalfinancemanager.reminderhistory;

import java.util.List;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;
import ufm.universalfinancemanager.db.entity.Reminder;

/**
 * Created by simranjeetkaur on 06/04/18.
 */

public class ReminderHistoryContract {
    public interface Presenter extends BasePresenter<View> {
        void loadReminders();
        void addReminder();
        void editReminder(String id);
        void takeView(ReminderHistoryContract.View view);
        void dropView();
    }
    interface View extends BaseView<Presenter> {
        void showReminders(List<Reminder> Reminder);
        void showAddEditReminders();
        void showAddEditReminders(String id);
    }
}
