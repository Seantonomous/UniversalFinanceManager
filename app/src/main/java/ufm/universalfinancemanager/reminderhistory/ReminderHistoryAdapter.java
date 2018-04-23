package ufm.universalfinancemanager.reminderhistory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

//import ufm.universalfinancemanager.db.entity.ReminderHistory;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;
import ufm.universalfinancemanager.support.atomic.Reminder;


/**
 * Created by simranjeetkaur on 06/04/18.
 */

public class ReminderHistoryAdapter extends BaseAdapter {
    private static final int TYPE_TRANSACTION = 0;
    private static final int TYPE_SEPARATOR = 1;

    private List<ListItem> mItems;
    public ReminderHistoryAdapter(List<Reminder> reminders) {
        mItems = new ArrayList<>();
        setList(reminders);
    }

    private void setList(List<Reminder> reminders) {
        for(Reminder r: reminders) {
            mItems.add(r);
        }
    }

    //  @Override
    public ListItem getItem(int position) {
        return mItems.get(position);
    }

  //  @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        return getItem(position).getView(LayoutInflater.from(parent.getContext()), convertView);
    }

    //@Override
    public int getCount() {
        return mItems.size();
    }

    public int getViewTypeCount() {
        return RowType.values().length;
    }

    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    public void replaceItem(List<Reminder> reminders) {
        mItems.clear();
        setList(reminders);
        notifyDataSetChanged();
    }

}
