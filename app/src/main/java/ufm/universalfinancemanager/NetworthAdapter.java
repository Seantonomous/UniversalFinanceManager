/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/17/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class NetworthAdapter extends ArrayAdapter<ListItem> {
    private static final int TYPE_TRANSACTION = 0;
    private static final int TYPE_SEPARATOR = 1;

    private Context context;
    private static LayoutInflater inflater = null;


    public NetworthAdapter(Context c, ArrayList<ListItem> transactions) {
        super(c, 0, transactions);
        this.context = c;
        inflater = LayoutInflater.from(c); //LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getViewTypeCount() {
        return RowType.values().length;
    }

    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    /*
    @Override
    public long getItemId(int position) {
        return position;
    }
    */

    public class Holder {
        public View view;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        int rowtype = getItemViewType(position);
        View view;

        if (convertView == null) {
            holder = new Holder();
            switch (rowtype) {
                case TYPE_TRANSACTION:
                    convertView = inflater.inflate(R.layout.net_worth_list_item, null);
                    holder.view = getItem(position).getView(inflater, convertView);
                    break;
                case TYPE_SEPARATOR:
                    convertView = inflater.inflate(R.layout.net_worth_header_item, null);
                    holder.view = getItem(position).getView(inflater, convertView);
                    break;
            }
            convertView.setTag(holder);
        }else {
            holder = (Holder)convertView.getTag();
        }
        return convertView;
    }
}
