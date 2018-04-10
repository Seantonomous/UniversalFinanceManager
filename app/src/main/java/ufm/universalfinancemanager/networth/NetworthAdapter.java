/* Author: Aaron O'Connor
* Date Started: 11/19/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager.networth;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;
import ufm.universalfinancemanager.db.entity.Account;

public class NetworthAdapter extends BaseAdapter {
    private static final int TYPE_ACCOUNT = 0;
    private static final int TYPE_SEPARATOR = 1;

    private List<ListItem> mItems;

    private NetworthFragment.NetworthClickListener mListener;

    public NetworthAdapter(List<Account> accounts, NetworthFragment.NetworthClickListener listener) {
        mItems = new ArrayList<>();
        mListener = listener;
        setList(accounts);
    }

    public void setList(List<Account> accounts) {
        double totalAssets = 0;
        double totalLiabilities = 0;
        double totalNetworth = 0;

        mItems.add(new NetworthHeader("Networth: ", 0));

        for(Account account : accounts) {
            if(account.getType() != AccountType.CREDIT_CARD) {
                mItems.add(account);
                totalAssets += account.getBalance();
            }
        }
        mItems.add(new NetworthHeader("Assets: ", totalAssets));

        for(Account account : accounts) {
            if(account.getType() == AccountType.CREDIT_CARD) {
                mItems.add(account);
                totalLiabilities += account.getBalance();
            }
        }
        mItems.add(new NetworthHeader("Liabilities: ", totalLiabilities*(-1)));

        totalNetworth = totalAssets - totalLiabilities;

        mItems.set(0, new NetworthHeader("Networth: ", totalNetworth));

    }

    public void replaceItems(List<Account> items) {
        mItems.clear();
        setList(items);
        notifyDataSetChanged();
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
        View rowview = getItem(position).getView(LayoutInflater.from(parent.getContext()), convertView);

        rowview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItem(position).getViewType() == TYPE_ACCOUNT)
                    mListener.onAccountClicked((Account)getItem(position));
            }
        });

        return rowview;
    }
}
