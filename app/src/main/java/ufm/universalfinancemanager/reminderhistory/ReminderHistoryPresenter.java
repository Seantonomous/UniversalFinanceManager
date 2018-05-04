package ufm.universalfinancemanager.reminderhistory;

import javax.inject.Inject;

import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by simranjeetkaur on 06/04/18.
 */

public class ReminderHistoryPresenter implements ReminderHistoryContract.Presenter {
    private ReminderHistoryContract.View mView;
    private User mUser;

    private boolean DEBUG = true;

    @Inject
    ReminderHistoryPresenter(User user)
    {
        mUser = user;
    }

    @Override
    public void loadReminders() {
        mView.showReminders(mUser.getReminders());

    }

    @Override
    public void addReminder() {
        if(mView != null) {
            mView.showAddEditReminders();
        }
    }

    @Override
    public void editReminder(String id) {
        this.mView.showAddEditReminders(id);

    }

    @Override
    public void takeView(ReminderHistoryContract.View view)
        {
            this.mView = view;
            loadReminders();
    }
    @Override
    public void dropView()
    {
        mView =null;
    }
}
