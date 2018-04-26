package ufm.universalfinancemanager.budgetoverview;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.db.entity.Budget;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetAdapter extends BaseAdapter {
    private List<ListItem> mItems;

    public BudgetAdapter(List<Budget> budgets) {
        mItems = new ArrayList<>();
        setList(budgets);
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
         return getItem(position).getView(LayoutInflater.from(parent.getContext()),convertView);
    }
}
