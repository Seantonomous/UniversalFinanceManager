package ufm.universalfinancemanager.budgetoverview;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.List;

import ufm.universalfinancemanager.support.ListItem;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetAdapter extends BaseAdapter {
    private List<ListItem> mItems;
    private BudgetFragment.TransactionClickListener mListener;

    //populate mItems
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
       /* View rowView = getItem(position).getView(LayoutInflater.from(parent.getContext()), convertView);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(getItem(position).getViewType() == TYPE_TRANSACTION)
                    //mListener.onTransactionClicked((Transaction)getItem(position));
            }
        });
        return rowView;
*/ return null;
    }
}
