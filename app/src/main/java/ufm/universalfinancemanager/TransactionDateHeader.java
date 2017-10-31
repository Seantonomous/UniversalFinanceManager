package ufm.universalfinancemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by smh7 on 10/31/17.
 */

public class TransactionDateHeader implements ListItem {
    private Date date;

    public TransactionDateHeader(Date d) {
        this.date = d;
    }

    @Override
    public int getViewType() {
        return RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;

        if(convertView == null) {
            view = (View)inflater.inflate(R.layout.transaction_header_item, null);
        }else {
            view = convertView;
        }

        TextView textDate = (TextView)view.findViewById(R.id.trans_date_header);
        textDate.setText(this.date.toString());

        return view;
    }
}
