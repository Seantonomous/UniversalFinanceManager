/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/17/17
* Date Complete: 10/24/17
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager.transactionhistory;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;

/*
*   This class adapts transactions to be used in a listview, it also sorts them
*   and inserts TransactionDateHeaders to separate transactions by date
*/
public class TransactionAdapter extends BaseAdapter {
    private static final int TYPE_TRANSACTION = 0;
    private static final int TYPE_SEPARATOR = 1;

    private List<ListItem> mItems;
    private TransactionHistoryFragment.TransactionClickListener mListener;

    public TransactionAdapter(List<Transaction> items, TransactionHistoryFragment.TransactionClickListener clickListener) {
        mItems = new ArrayList<>();
        setList(items);
        mListener = clickListener;
    }

    public void replaceItems(List<Transaction> items) {
        mItems.clear();
        setList(items);
        notifyDataSetChanged();
    }

    private void setList(List<Transaction> items) {
        if(items.isEmpty()) {
            return;
        }

        //Sort the transactions based on their date
        Collections.sort(items, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction lhs, Transaction rhs) {
                return lhs.getDate().getTime() > rhs.getDate().getTime() ? -1 : (lhs.getDate().getTime() < rhs.getDate().getTime()) ? 1 : 0;
            }
        });

        Date currentDate = items.get(0).getDate();
        mItems.add(new TransactionDateHeader(currentDate));

        for(Transaction t : items) {
            //If the next transaction has a different date, update the current date
            //and insert a new transaction date header
            if(t.getDate().getTime() < currentDate.getTime()) {
                currentDate = t.getDate();
                mItems.add(new TransactionDateHeader(currentDate));
            }
            mItems.add(t);
        }
    }

    @Override
    public ListItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    public int getViewTypeCount() {
        return RowType.values().length;
    }

    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View rowView = getItem(position).getView(LayoutInflater.from(parent.getContext()), convertView);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItem(position).getViewType() == TYPE_TRANSACTION)
                mListener.onTransactionClicked((Transaction)getItem(position));
            }
        });
        return rowView;
    }
}
