package ufm.universalfinancemanager.addeditreminder;

import android.support.annotation.Nullable;

import java.util.Date;
import java.sql.Time;

import javax.inject.Inject;

import ufm.universalfinancemanager.db.entity.Reminder;
import ufm.universalfinancemanager.support.atomic.User;


/**
 * Created by Areeba on 2/17/2018.
 */

public class AddEditReminderPresenter implements AddEditReminderContract.Presenter{

    private User mUser;
    private final String id;
    private String remName;
    @Nullable
    private AddEditReminderContract.View mAddEditReminderview = null;
    @Inject
    AddEditReminderPresenter(User user, @Nullable String id) {
        mUser = user;
        this.id = id;
    }

    @Override
    public void saveReminders(String name, Time time, Date date, String notes) {

        try {
            if(isNewReminder()) {
                createReminder(name, time, date, notes);
            }
            else {
                updateReminder(name, time, date, notes);
            }



        } catch (RuntimeException e) {
            if (mAddEditReminderview != null)
                mAddEditReminderview.showMessage("Error saving Reminder");
            mAddEditReminderview.showLastActivity(true);
        }

    }

    private void updateReminder(String name, Time time, Date date, String notes) {
        remName = name;
        Reminder r = mUser.getReminder(name);
        r.setName(name);
        r.setDate(date);
        r.setNotes(notes);
        r.setTime(time);
        if (mAddEditReminderview != null) {
            mAddEditReminderview.showMessage("Reminder successfully saved.");
            mAddEditReminderview.showLastActivity(true);
        }
    }

    private void createReminder(String name, Time time, Date date, String notes) {
        remName = name;
        Reminder reminder = new Reminder(name, time, date, notes) ;
        mUser.addReminder(reminder);
        if (mAddEditReminderview != null) {
            mAddEditReminderview.showMessage("Reminder successfully saved.");
            mAddEditReminderview.showLastActivity(true);
        }
    }

    @Override
    public void deleteReminders() {
        mUser.deleteReminder(this.id);
        mAddEditReminderview.showLastActivity(true);

    }

    @Override
    public void takeView(AddEditReminderContract.View view) {
        mAddEditReminderview = view;
        if(view == null)
            return;
        if(isNewReminder()) {
            mAddEditReminderview.setUpFragmentContent(false);
        }
        else {
            mAddEditReminderview.setUpFragmentContent(true);
            populateReminder();
        }
    }

    private void populateReminder() {
        Reminder r = mUser.getReminder(this.id);
        mAddEditReminderview.populateExistingFields(r.getName(), r.getTime(), r.getDate(), r.getNotes());
    }

    @Override
    public void dropView() {
        mAddEditReminderview = null;
    }


    public boolean isNewReminder() {
        return this.id == null;
    }
}