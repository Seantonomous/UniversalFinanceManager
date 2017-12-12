/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/29/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/

package ufm.universalfinancemanager.transactionhistory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;

public class TransactionDateHeader implements ListItem {
    private Date date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

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
            view = inflater.inflate(R.layout.transaction_header_item, null);
        }else {
            view = convertView;
        }

        TextView textDate = view.findViewById(R.id.trans_date_header);
        textDate.setText(dateFormat.format(this.date));

        return view;
    }
}
