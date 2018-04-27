package ufm.universalfinancemanager.budgetoverview;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.db.entity.Budget;
import ufm.universalfinancemanager.transactionhistory.TransactionHistoryFragment;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetAdapter extends BaseAdapter {
    private List<ListItem> mItems;
    private BudgetFragment.BudgetClickListener mListener;


    public BudgetAdapter(List<Budget> budgets, BudgetFragment.BudgetClickListener clickListener) {
        mItems = new ArrayList<>();
        setList(budgets);
        this.mListener = clickListener;
    }
    //populate mItems
    public void setList(List<Budget> budgets) {
        for(Budget b: budgets) {
            mItems.add(b);
        }
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public ListItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replaceItems(List<Budget> budgets) {
        mItems.clear();
        setList(budgets);
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // return getItem(position).getView(LayoutInflater.from(parent.getContext()),convertView);
        View rowView = getItem(position).getView(LayoutInflater.from(parent.getContext()), convertView);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItem(position).getViewType() == 0)
                    mListener.onBudgetClicked((Budget)getItem(position));
            }
        });

        return rowView;
    }
}
