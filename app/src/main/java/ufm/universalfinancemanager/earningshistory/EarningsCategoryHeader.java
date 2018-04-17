package ufm.universalfinancemanager.earningshistory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;

/**
 * Created by Faiz on 4/7/2018.
 */

public class EarningsCategoryHeader implements ListItem {

    private String title;

    public EarningsCategoryHeader(String title) {
        this.title = title;
    }

    @Override
    public int getViewType() {
        return RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;

        if(convertView == null) {
            view = inflater.inflate(R.layout.earnings_header_item, null);
        }else {
            view = convertView;
        }

        TextView title = view.findViewById(R.id.earnings_header);
        title.setText(this.title);

        return view;
    }

}
