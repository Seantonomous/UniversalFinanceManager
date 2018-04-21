package ufm.universalfinancemanager.reminderhistory;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ufm.universalfinancemanager.R;

import ufm.universalfinancemanager.addeditaccount.AddEditAccountActivity;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryActivity;
import ufm.universalfinancemanager.addeditreminder.AddEditReminderActivity;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.support.atomic.Reminder;

/**
 * Created by simranjeetkaur on 06/04/18.
 */

public class ReminderHistoryFragment extends dagger.android.support.DaggerFragment implements ReminderHistoryContract.View {
    @Inject
    ReminderHistoryPresenter mPresenter;

    private ReminderHistoryAdapter mAdapter;

    @Inject
    public ReminderHistoryFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ReminderHistoryAdapter(new ArrayList<Reminder>(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.reminder_history_fragment, container, false);
        ListView listview = root.findViewById(R.id.reminder_list);
        listview.setAdapter(mAdapter);
        setHasOptionsMenu(true);
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_transaction:
                startActivity(new Intent(getContext(), AddEditTransactionActivity.class));
                break;
            case R.id.action_add_account:
                startActivity(new Intent(getContext(), AddEditAccountActivity.class));
                break;
            case R.id.action_add_category:
                startActivity(new Intent(getContext(), AddEditCategoryActivity.class));
                break;
            case R.id.action_add_reminder:
                startActivity(new Intent(getContext(), AddEditReminderActivity.class));
                break;
        }
        return true;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
    }

    @Override
    public void showReminders(List<Reminder> reminders) {
        mAdapter.replaceItem(reminders);
    }
}