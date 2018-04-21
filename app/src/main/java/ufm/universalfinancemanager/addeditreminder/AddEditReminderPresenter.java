package ufm.universalfinancemanager.addeditreminder;

import android.support.annotation.Nullable;
import android.widget.TimePicker;

import java.util.Date;
import java.sql.Time;

import javax.inject.Inject;

import ufm.universalfinancemanager.addedittransaction.AddEditTransactionContract;
import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.atomic.Reminder;
import ufm.universalfinancemanager.support.atomic.User;


/**
 * Created by Areeba on 2/17/2018.
 */

public class AddEditReminderPresenter implements AddEditReminderContract.Presenter{

    private User mUser;
    @Nullable
    private AddEditReminderContract.View mAddEditReminderview = null;
    @Inject
    AddEditReminderPresenter(User user) {
        mUser = user;
    }

    @Override
    public void saveReminders(String name, Time time, Date date, String notes) {

        try {
            Reminder reminder = new Reminder(name, time, date, notes) ;

            mUser.addReminder(reminder);

            if (mAddEditReminderview != null) {
                mAddEditReminderview.showMessage("Reminder successfully saved.");
                mAddEditReminderview.showLastActivity(true);
            }

        } catch (RuntimeException e) {
            if (mAddEditReminderview != null)
                mAddEditReminderview.showMessage("Error saving Reminder");
                mAddEditReminderview.showLastActivity(true);
        }

    }

    @Override
    public void takeView(AddEditReminderContract.View view) {
        mAddEditReminderview = view;
        if(view == null)
            return;

    }

    @Override
    public void dropView() {
        mAddEditReminderview = null;
    }


}

