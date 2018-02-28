/* Author: Aaron O'Connor
* Date Started: 11/19/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import ufm.universalfinancemanager.support.atomic.Account;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.Networth;
import ufm.universalfinancemanager.networth.NetworthAdapter;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.networth.NetworthHeader;

public class NetWorthFragment extends ListFragment {

    public NetWorthFragment() {
        //Required to be empty since extends Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the fragment with the corresponding layout
        View rootView = inflater.inflate(R.layout.transaction_history_fragment, container, false);
        ArrayList<Account> accounts = getArguments().getParcelableArrayList("ACCOUNT");

        Networth networth = new Networth(0,0,0,0, new Date());

        ListView accounts_list = rootView.findViewById(android.R.id.list);

        ArrayList<ListItem> items = new ArrayList<>();

        AccountType tempAccountType;


        items.add(new NetworthHeader("Networth: ", 0));

        for (int i = 0; i < accounts.size(); i++) {
            tempAccountType = accounts.get(i).getType();
            if (tempAccountType != AccountType.CREDIT_CARD) {
                items.add(accounts.get(i));
                networth.totalAssets = networth.totalAssets + accounts.get(i).getBalance();
            }
        }
        items.add(new NetworthHeader("Assets: ", networth.totalAssets));

        for (int i = 0; i < accounts.size(); i++) {
            tempAccountType = accounts.get(i).getType();
            if (tempAccountType == AccountType.CREDIT_CARD) {
                items.add(accounts.get(i));
                networth.totalLiabilities = networth.totalLiabilities + accounts.get(i).getBalance();
            }
        }
        items.add(new NetworthHeader("Liabilities: ", networth.totalLiabilities*(-1)));

        networth.totalNetWorth = networth.totalAssets - networth.totalLiabilities;
        items.set(0, new NetworthHeader("Networth: ", networth.totalNetWorth));

        accounts_list.setAdapter(new NetworthAdapter(getActivity(), items));
        return rootView;
    }
}
