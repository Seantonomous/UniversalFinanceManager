package ufm.universalfinancemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionAdapter extends BaseAdapter {
    private ArrayList<Transaction> transactions;
    private Context context;
    private static LayoutInflater inflater = null;

    public TransactionAdapter(Context c, ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        this.context = c;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView dateView;
        TextView nameView;
        TextView amountView;
        TextView accountView;
        TextView categoryView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        //inflate the row item layout (multiple textviews
        View rowView = inflater.inflate(R.layout.transaction_list_item, null);

        //Instantiate all the textviews from the layout
        holder.dateView = (TextView)rowView.findViewById(R.id.trans_date);
        holder.nameView = (TextView)rowView.findViewById(R.id.trans_name);
        holder.amountView = (TextView)rowView.findViewById(R.id.trans_amount);
        holder.accountView = (TextView)rowView.findViewById(R.id.trans_account);
        holder.categoryView = (TextView)rowView.findViewById(R.id.trans_category);

        //Set the text of each textview based on its corresponding transaction attribute
        holder.dateView.setText(transactions.get(position).getDate().toString());
        holder.nameView.setText(transactions.get(position).getName());
        holder.amountView.setText(Double.toString(transactions.get(position).getAmount()));
        holder.accountView.setText(transactions.get(position).getAccount().toString());
        holder.categoryView.setText(transactions.get(position).getCategory().toString());

        return rowView;
    }
}
