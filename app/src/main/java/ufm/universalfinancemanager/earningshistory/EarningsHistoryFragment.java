package ufm.universalfinancemanager.earningshistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountActivity;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryActivity;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.db.entity.Transaction;

/**
 * Created by Faiz on 2/26/2018.
 */

public class EarningsHistoryFragment extends DaggerFragment implements EarningsHistoryContract.View  {


    @Inject
    EarningsHistoryPresenter mPresenter;

    private EarningsAdapter mAdapter;
    private View mNoTransactionsView;
    private TextView mNoTransactionsTextView;
    private View mTransactionsView;

    @Inject
    public EarningsHistoryFragment() {
        //Required to be empty since extends Fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragment with the corresponding layout
        View root = inflater.inflate(R.layout.transaction_history_fragment, container, false);

        ListView listview = root.findViewById(R.id.transactionlist);
        listview.setAdapter(mAdapter);
        mTransactionsView = root.findViewById(R.id.transactionsLayout);
        mNoTransactionsView = root.findViewById(R.id.noTransactionsLayout);
        mNoTransactionsTextView = root.findViewById(R.id.noTransactionsText);

        setHasOptionsMenu(true);

        return root;
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
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
    }

    public interface TransactionClickListener {
        void onTransactionClicked(Transaction t);
    }

}
