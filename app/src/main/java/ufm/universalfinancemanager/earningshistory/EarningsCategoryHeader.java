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
    private double lastMonthTotal;
    private double thisMonthTotal;
    private NumberFormat num_format = NumberFormat.getCurrencyInstance();

    public EarningsCategoryHeader(String title) {
        this.title = title;
        this.lastMonthTotal = 0;
        this.thisMonthTotal = 0;
    }

    public EarningsCategoryHeader(String title, double a, double b) {
        this.title = title;
        this.lastMonthTotal = a;
        this.thisMonthTotal = b;
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
        TextView lastMonthTitle = view.findViewById(R.id.last_month_total);
        TextView thisMonthTitle = view.findViewById(R.id.this_month_total);

        title.setText(this.title);
        lastMonthTitle.setText(num_format.format(this.lastMonthTotal));
        thisMonthTitle.setText(num_format.format(this.thisMonthTotal));

        return view;
    }

}
