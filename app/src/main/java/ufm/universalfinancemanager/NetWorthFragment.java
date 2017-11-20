/* Author: Aaron O'Connor
* Date Started: 11/19/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class NetWorthFragment extends ListFragment {

    public NetWorthFragment() {
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
        ArrayList<Account> accounts = getArguments().getParcelableArrayList("ACCOUNT");

        ListView accounts_list = rootView.findViewById(R.id.transaction_list);

        ArrayList<ListItem> items = new ArrayList<>();

        AccountType curAcctType;

        double netWorthTotal = 0;
        double assetTotal = 0;
        double liabilityTotal = 0;

        items.add(new NetworthHeader("Networth: ", 0));

        for (int i = 0; i < accounts.size(); i++) {
            curAcctType = accounts.get(i).getType();
            if (curAcctType != AccountType.CREDIT_CARD) {
                items.add(accounts.get(i));
                assetTotal = assetTotal + accounts.get(i).getBalance();
            }
        }

        items.add(new NetworthHeader("Assets: ", assetTotal));

        for (int i = 0; i < accounts.size(); i++) {
            curAcctType = accounts.get(i).getType();
            if (curAcctType == AccountType.CREDIT_CARD) {
                items.add(accounts.get(i));
                liabilityTotal = liabilityTotal + accounts.get(i).getBalance();
            }
        }

        items.add(new NetworthHeader("Liabilities: ", liabilityTotal*(-1)));

        netWorthTotal = assetTotal - liabilityTotal;

        items.set(0, new NetworthHeader("Networth: ", netWorthTotal));

//        // set list adapter 11/19 - Aaron
//        ArrayAdapter<Account > adapter;
//        adapter = new ArrayAdapter<Account>(getActivity(), android.R.layout.simple_list_item_1, accounts);
//        setListAdapter(adapter);

        accounts_list.setAdapter(new NetworthAdapter(getActivity(), items));
        return rootView;
    }
}
