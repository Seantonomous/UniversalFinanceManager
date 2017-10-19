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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Transaction_Activity extends Fragment {

    public Transaction_Activity() {
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
        View rootView = inflater.inflate(R.layout.transaction_list_layout, container);

        ArrayList<Transaction> transactions = savedInstanceState.getParcelableArrayList("transactions");

        ListView transaction_list = rootView.findViewById(R.id.transaction_list);
        transaction_list.setAdapter(new TransactionAdapter(getActivity(), transactions));
        return rootView;
    }
}
