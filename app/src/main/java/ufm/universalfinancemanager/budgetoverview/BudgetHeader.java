package ufm.universalfinancemanager.budgetoverview;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.ListItem;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetHeader implements ListItem{
    //private String title;
   // public BudgetHeader(String title) {
    //    this.title = title;
   // }
    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if(convertView == null) {
            view = inflater.inflate(R.layout.net_worth_header_item, null);
        }else {
            view = convertView;
        }
       // TextView str = view.findViewById(R.id.budget_category_header);
       // str.setText("Category" + this.title);
        return view;
    }
}
