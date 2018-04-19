package ufm.universalfinancemanager.earningshistory;

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
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;
import ufm.universalfinancemanager.support.atomic.Category;
import ufm.universalfinancemanager.transactionhistory.TransactionDateHeader;

/**
 * Created by Faiz on 3/10/2018.
 */

public class EarningsAdapter extends BaseAdapter {

    private static final int TYPE_TRANSACTION = 0;
    private static final int TYPE_SEPARATOR = 1;

    private List<ListItem> mItems;

    public EarningsAdapter(List<EarningsHistoryListItem> items) {
        mItems = new ArrayList<>();
        setList(items);
    }

    public void replaceItems(List<EarningsHistoryListItem> items) {
        mItems.clear();
        setList(items);
        notifyDataSetChanged();
    }

    private void setList(List<EarningsHistoryListItem> items) {
        if(items.isEmpty()) {
            return;
        }

        int i = 0;
        int expenseHeaderIndex;
        double incomeThisMonth = 0;
        double incomeLastMonth = 0;
        double expenseThisMonth = 0;
        double expenseLastMonth = 0;
        double netEarningsThisMonth = 0;
        double netEarningsLastMonth = 0;

        mItems.add(new EarningsCategoryHeader("Income: ", 0, 0));
        i++;

        for(EarningsHistoryListItem listItem : items) {
            if(listItem.getFlow() == Flow.INCOME) {
                mItems.add(listItem);
                i++;

                incomeLastMonth += listItem.getLastMonthTotal();
                incomeThisMonth += listItem.getThisMonthTotal();
            }
        }

        mItems.set(0, new EarningsCategoryHeader("Income: ", incomeLastMonth, incomeThisMonth));

        expenseHeaderIndex = i;
        mItems.add(new EarningsCategoryHeader("Expense: ", 0, 0));
        i++;

        for(EarningsHistoryListItem listItem : items) {
            if(listItem.getFlow() == Flow.OUTCOME) {
                mItems.add(listItem);
                i++;

                expenseLastMonth += listItem.getLastMonthTotal();
                expenseThisMonth += listItem.getThisMonthTotal();
            }
        }

        mItems.set(expenseHeaderIndex, new EarningsCategoryHeader("Expense: ", expenseLastMonth, expenseThisMonth));

        netEarningsLastMonth = incomeLastMonth - expenseLastMonth;
        netEarningsThisMonth = incomeThisMonth - expenseThisMonth;

        mItems.add(new EarningsCategoryHeader("Net Earnings: ", netEarningsLastMonth, netEarningsThisMonth));

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

        return rowView;
    }
}
