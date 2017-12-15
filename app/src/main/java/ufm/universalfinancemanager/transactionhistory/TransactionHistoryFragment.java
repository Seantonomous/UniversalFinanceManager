/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/17/17
* Date Complete: 12/3/17
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager.transactionhistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.ui.Transaction_Add;


public class TransactionHistoryFragment extends DaggerFragment implements TransactionHistoryContract.View {

    @Inject
    TransactionHistoryPresenter mPresenter;

    TransactionClickListener mClickListener = new TransactionClickListener() {
        @Override
        public void onTransactionClicked(Transaction t) {
            mPresenter.editTransaction(t.getId());
        }
    };

    private TransactionAdapter mAdapter;
    private View mNoTransactionsView;
    private TextView mNoTransactionsTextView;
    private View mTransactionsView;

    @Inject
    public TransactionHistoryFragment() {
        //Required to be empty since extends Fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TransactionAdapter(new ArrayList<Transaction>(0), mClickListener);
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
    public void showTransactions(List<Transaction> items) {
        mAdapter.replaceItems(items);
        mTransactionsView.setVisibility(View.VISIBLE);
        mNoTransactionsView.setVisibility(View.GONE);
    }

    @Override
    public void showNoTransactions() {
        mTransactionsView.setVisibility(View.GONE);
        mNoTransactionsView.setVisibility(View.VISIBLE);
        mNoTransactionsTextView.setText(R.string.no_transactions);
    }

    @Override
    public void showAddEditTransaction() {
        //Todo: make request code in TransactionAddEditActivity for '1'
        startActivityForResult(new Intent(getContext(), AddEditTransactionActivity.class), 1);
    }

    @Override
    public void showAddEditTransaction(String transactionId) {
        Intent intent = new Intent(getContext(), AddEditTransactionActivity.class);
        //Todo: make EXTRA_ID in TransactionAddEditActivity
        intent.putExtra("ID", transactionId);
        //Todo: make request code in TransactionAddEditAcitivity for '2'
        startActivityForResult(intent, 2);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
    }

    public interface TransactionClickListener {
        void onTransactionClicked(Transaction t);
    }
}
