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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.db.source.local.TransactionDao;
import ufm.universalfinancemanager.support.Flow;
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

    public TransactionAdapter(List<Transaction> items, TransactionHistoryFragment.TransactionClickListener clickListener,int filter) {
        mItems = new ArrayList<>();
        setList(items,filter);
        mListener = clickListener;
    }


    public void replaceItems(List<Transaction> items,int filter) {
        mItems.clear();
        setList(items,filter);
        notifyDataSetChanged();
    }

    private void setList(List<Transaction> items, final int filter) {
        if(items.isEmpty()) {
            return;
        }

        /*

        //Sort the transactions based on their date
        Collections.sort(items, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction lhs, Transaction rhs) {
                return lhs.getDate().getTime() > rhs.getDate().getTime() ? -1 : (lhs.getDate().getTime() < rhs.getDate().getTime()) ? 1 : 0;
            }
        });
        */
  // sortTransactionsByFilter(items,filter);

   Collections.sort(items, new Comparator<Transaction>() {
       @Override
       public int compare(Transaction o1, Transaction o2) {
           switch (filter){
               case 0:// DEFAULT: SORTED BY DATE (Recent - Old)
                   return o1.getDate().getTime() > o2.getDate().getTime() ? -1 : (o1.getDate().getTime() < o2.getDate().getTime()) ? 1 : 0;

               case 1:// SORTED BY CATEGORY (A-Z)
                    if(o1.getFlow() == Flow.TRANSFER)
                        return -1;
                    else if(o2.getFlow() == Flow.TRANSFER)
                        return 1;
                    else
                        return o1.getCategory().hashCode() < o2.getCategory().hashCode()? -1 : (o1.getCategory().hashCode() > o2.getCategory().hashCode() ? 1: 0) ;

               case 2:// SORTED BY CATEGORY (Z-A)
                   if(o1.getFlow() == Flow.TRANSFER)
                        return 1;
                    else if(o2.getFlow() == Flow.TRANSFER)
                        return -1;
                    else
                        return o1.getCategory().hashCode() > o2.getCategory().hashCode()? -1 : (o1.getCategory().hashCode() < o2.getCategory().hashCode() ? 1: 0) ;

               case 3:// SORTED BY AMOUNT ($$$-$)
                   return (o1.getAmount()) > o2.getAmount()? -1 : (o1.getAmount() < o2.getAmount() ? 1: 0) ;

               case 4: // SORTED BY AMOUNT ($-$$$)
                   return o1.getAmount() < o2.getAmount()? -1 : (o1.getAmount() > o2.getAmount() ? 1: 0) ;
           }
           return 0;
       }
   });


        Date currentDate = items.get(0).getDate();
        mItems.add(new TransactionDateHeader(currentDate));

        for(Transaction t : items) {
            //If the next transaction has a different date, update the current date
            //and insert a new transaction date header
            if(t.getDate().getTime() < currentDate.getTime() || t.getDate().getTime() > currentDate.getTime()) {
                currentDate = t.getDate();
                mItems.add(new TransactionDateHeader(currentDate));
            }
            mItems.add(t);
        }
    }

    public void sortTransactionsByFilter(List<Transaction> items, int filter){

        switch(filter){

            case 0:     // DEFAULT: SORTED BY DATE (Recent - Old)
                Log.d("filter",filter+"");
                Collections.sort(items, new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction lhs, Transaction rhs) {
                        return lhs.getDate().getTime() > rhs.getDate().getTime() ? -1 : (lhs.getDate().getTime() < rhs.getDate().getTime()) ? 1 : 0;
                    }
                });


            case 1:     // SORTED BY CATEGORY (A-Z)
                Log.d("filter",filter+"");
                Collections.sort(items, new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction o1, Transaction o2) {
                        return o1.getCategory().hashCode() < o2.getCategory().hashCode()? -1 : (o1.getCategory().hashCode() > o2.getCategory().hashCode() ? 1: 0) ;
                    }
                });

            case 2:     // SORTED BY CATEGORY (Z-A)
                Log.d("filter",filter+"");
                Collections.sort(items, new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction o1, Transaction o2) {
                        return o1.getCategory().hashCode() > o2.getCategory().hashCode()? -1 : (o1.getCategory().hashCode() < o2.getCategory().hashCode() ? 1: 0) ;
                    }
                });

            case 3:     // SORTED BY AMOUNT ($$$-$)
                Log.d("filter in sort",filter+"");
                Collections.sort(items, new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction o1, Transaction o2) {
                        return (o1.getAmount()) > o2.getAmount()? -1 : (o1.getAmount() < o2.getAmount() ? 1: 0) ;
                    }
                });

            case 4:     // SORTED BY AMOUNT ($-$$$)
                Log.d("filter",filter+"");
                Collections.sort(items, new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction o1, Transaction o2) {
                        return o1.getAmount() < o2.getAmount()? -1 : (o1.getAmount() > o2.getAmount() ? 1: 0) ;
                    }
                });

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
