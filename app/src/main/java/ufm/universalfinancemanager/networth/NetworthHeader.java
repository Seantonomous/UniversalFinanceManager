/* Author: Aaron O'Connor
* Date Started: 11/19/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager.networth;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;

public class NetworthHeader implements ListItem {
    private double dblAmount;
    private String title;

    private NumberFormat num_format = NumberFormat.getCurrencyInstance();

    public NetworthHeader(String title, double d) {
        this.title = title;
        this.dblAmount = d;
    }

    @Override
    public int getViewType() {
        return RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;

        if(convertView == null) {
            view = inflater.inflate(R.layout.net_worth_header_item, null);
        }else {
            view = convertView;
        }

        TextView stringNetworth = view.findViewById(R.id.networth_header);
        stringNetworth.setText("Net " + this.title + num_format.format(this.dblAmount));

        return view;
    }
}
