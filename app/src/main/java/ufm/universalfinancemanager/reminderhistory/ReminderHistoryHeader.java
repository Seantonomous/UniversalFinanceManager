package ufm.universalfinancemanager.reminderhistory;

import android.view.LayoutInflater;
import android.view.View;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.ListItem;

/**
 * Created by simranjeetkaur on 19/04/18.
 */

public class ReminderHistoryHeader implements ListItem {
    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if(convertView == null) {
            view = inflater.inflate(R.layout.reminder_list, null);
        }else {
            view = convertView;
        }
        // TextView str = view.findViewById(R.id.budget_category_header);
        // str.setText("Category" + this.title);
        return view;
    }
}
