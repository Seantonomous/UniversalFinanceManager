package ufm.universalfinancemanager.networth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountActivity;
import ufm.universalfinancemanager.addeditbudget.AddEditBudgetActivity;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryActivity;
import ufm.universalfinancemanager.addeditreminder.AddEditReminderActivity;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.db.entity.Account;

/**
 * Created by smh7 on 2/28/18.
 */

public class NetworthFragment extends DaggerFragment implements NetworthContract.View {
    @Inject
    NetworthPresenter mPresenter;

    private NetworthAdapter mAdapter;
    private LinearLayout mNetworthLayout;
    private LinearLayout mNoNetworthLayout;

    private NetworthClickListener mListener = new NetworthClickListener() {
        @Override
        public void onAccountClicked(Account account) {
            mPresenter.editAccount(account);
        }
    };

    @Inject
    public NetworthFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new NetworthAdapter(new ArrayList<Account>(0), mListener);
    }

    @Override
    public void showNetworth(List<Account> accounts) {
        mNetworthLayout.setVisibility(View.VISIBLE);
        mNoNetworthLayout.setVisibility(View.GONE);
        mAdapter.replaceItems(accounts);
    }

    @Override
    public void showNoNetworth() {
        mNetworthLayout.setVisibility(View.GONE);
        mNoNetworthLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.networth_fragment, container, false);

        mNetworthLayout = root.findViewById(R.id.networthLayout);
        mNoNetworthLayout = root.findViewById(R.id.noNetworthLayout);

        mNetworthLayout.setVisibility(View.VISIBLE);
        mNoNetworthLayout.setVisibility(View.GONE);

        ListView listview = root.findViewById(R.id.networth_list);
        listview.setAdapter(mAdapter);

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_transaction:
                startActivity(new Intent(getContext(), AddEditTransactionActivity.class));
                break;
            case R.id.action_add_account:
                startActivity(new Intent(getContext(), AddEditAccountActivity.class));
                break;
            case R.id.action_add_category:
                startActivity(new Intent(getContext(), AddEditCategoryActivity.class));
                break;
            case R.id.action_add_reminder:
                startActivity(new Intent(getContext(), AddEditReminderActivity.class));
                break;
            case R.id.action_add_budget:
                startActivity(new Intent(getContext(), AddEditBudgetActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
    }

    @Override
    public void showEditAccount(String accountName) {
        Intent intent = new Intent(getContext(), AddEditAccountActivity.class);
        intent.putExtra("ACCOUNT_NAME", accountName);
        startActivity(intent);
    }

    public interface NetworthClickListener {
        void onAccountClicked(Account account);
    }
}
