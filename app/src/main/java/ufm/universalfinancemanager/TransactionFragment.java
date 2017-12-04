/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/17/17
* Date Complete: 12/3/17
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class TransactionFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public TransactionFragment() {
        //Required to be empty since extends Fragment
    }
    /*
    public static Transaction_Activity newInstance(ArrayList<Transaction> t) {
        Transaction_Activity fragment = new Transaction_Activity();

        Bundle args = new Bundle();
        args.putParcelableArrayList("TRANSACTIONS", t);
        return fragment;
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Transaction> transactions = getArguments().getParcelableArrayList("TRANSACTIONS");

        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction lhs, Transaction rhs) {
                return lhs.getDate().getTime() < rhs.getDate().getTime() ? -1 : (lhs.getDate().getTime() > rhs.getDate().getTime()) ? 1 : 0;
            }
        });

        ArrayList<ListItem> items = new ArrayList<>();
        Date cur_date = transactions.get(transactions.size() - 1).getDate();

        //Add the header for the latest transaction
        items.add(new TransactionDateHeader(cur_date));

        for(int i=transactions.size()-1; i>=0; i--) {
            Date t_date = transactions.get(i).getDate();
            //If the current date is different the next transaction's date, create a new header
            if(!cur_date.equals(t_date)) {
                items.add(new TransactionDateHeader(t_date));
                cur_date = t_date;
            }
            items.add(transactions.get(i));
        }

        setListAdapter(new TransactionAdapter(getActivity(), items));
        getListView().setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragment with the corresponding layout
        View rootView = inflater.inflate(R.layout.transaction_list_layout, container, false);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
