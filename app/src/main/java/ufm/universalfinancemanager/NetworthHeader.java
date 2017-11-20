/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/29/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/

package ufm.universalfinancemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NetworthHeader implements ListItem {
    private Date date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

    public NetworthHeader(Date d) {
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
            view = (View)inflater.inflate(R.layout.net_worth_header_item, null);
        }else {
            view = convertView;
        }

        TextView stringNetworth = (TextView)view.findViewById(R.id.networth_header);
        stringNetworth.setText("Assets total: $");

        return view;
    }
}
