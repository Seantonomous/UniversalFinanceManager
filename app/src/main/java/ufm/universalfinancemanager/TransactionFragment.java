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

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class TransactionFragment extends ListFragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragment with the corresponding layout
        View rootView = inflater.inflate(R.layout.transaction_list_layout, container, false);
        ArrayList<Transaction> transactions = getArguments().getParcelableArrayList("TRANSACTIONS");

        ListView transaction_list = rootView.findViewById(R.id.transaction_list);

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

        transaction_list.setAdapter(new TransactionAdapter(getActivity(), items));
        return rootView;
    }
}
