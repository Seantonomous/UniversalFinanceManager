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

import android.app.AlertDialog;
import android.arch.persistence.room.Dao;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountActivity;
import ufm.universalfinancemanager.addeditbudget.AddEditBudgetActivity;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryActivity;
import ufm.universalfinancemanager.addeditreminder.AddEditReminderActivity;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.db.TransactionDataSource;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.db.source.local.TransactionDao;
import ufm.universalfinancemanager.db.source.local.TransactionDao_Impl;
import ufm.universalfinancemanager.db.source.local.TransactionDatabase;


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
    private SearchView mSearchView;
    public int filter;

    public Button mSortButton;

    @Inject
    public TransactionHistoryFragment() {
        //Required to be empty since extends Fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TransactionAdapter(new ArrayList<Transaction>(0), mClickListener,filter);
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
        mAdapter.replaceItems(items,filter);
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
        intent.putExtra("EDIT_TRANSACTION_ID", transactionId);
        //Todo: make request code in TransactionAddEditAcitivity for '2'
        startActivityForResult(intent, 2);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        //Inflate the fragment with the corresponding layout
        View root = inflater.inflate(R.layout.transaction_history_fragment, container, false);

        final ListView listview = root.findViewById(R.id.transactionlist);
        listview.setAdapter(mAdapter);
        mTransactionsView = root.findViewById(R.id.transactionsLayout);
        mNoTransactionsView = root.findViewById(R.id.noTransactionsLayout);
        mNoTransactionsTextView = root.findViewById(R.id.noTransactionsText);

        mSortButton = root.findViewById(R.id.buttonSort);

        mSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  AlertDialog.Builder options = new AlertDialog.Builder(getContext());
                  options.setTitle("Sort By: ");

                final CharSequence[] sortOptions = new String[]{"Default","Category(A-Z)","Category(Z-A)","Amount($$$-$)","Amount($-$$$)"};


                options.setTitle("Select Your Choice");

                options.setSingleChoiceItems(sortOptions, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:
                                Toast.makeText(getContext(), "Sort By: Default", Toast.LENGTH_SHORT).show();
                                filter = 0;
                                break;
                            case 1:
                                Toast.makeText(getContext(), "Sort By: Category(A-Z)", Toast.LENGTH_SHORT).show();
                                filter = 1;
                                break;
                            case 2:
                                Toast.makeText(getContext(), "Sort By: Category(Z-A)", Toast.LENGTH_SHORT).show();
                                filter = 2;
                                break;
                            case 3:
                                Toast.makeText(getContext(), "Sort By: Amount($$$-$)", Toast.LENGTH_SHORT).show();
                                filter = 3;
                                break;
                            case 4:
                                Toast.makeText(getContext(), "Sort By: Amount($-$$$)", Toast.LENGTH_SHORT).show();
                                filter = 4;
                                break;
                        }

                        if(mSearchView.getQuery().toString()==null){
                            mPresenter.loadTransactions();
                        }else{
                            mPresenter.loadTransactionsByName(mSearchView.getQuery().toString());
                        }

                    }
                });

                options.create();

                AlertDialog alert = options.create();
                alert.getWindow().setLayout(100,100);
                alert.show();

            }
        });

        //
        mSearchView =root.findViewById(R.id.searchView);

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Opened","Click");
            }


        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("Showing on close:","All Transactions");
                mPresenter.loadTransactions();
                return false;
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                Log.d("Searching for: ",query);
                mPresenter.loadTransactionsByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(mSearchView.getQuery().toString()!=null && TextUtils.getTrimmedLength(mSearchView.getQuery()) > 0){
                   // mPresenter.loadTransactionsByName(newText);
                    Log.d("Changed: -->",newText);
                }else{
                    mPresenter.loadTransactions();
                    //mPresenter.mTransactionHistoryView.showNoTransactions();
                    Log.d("Showing on change:", "Transactions");
                }
                return false;
            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_transaction:
                mPresenter.addTransaction();
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

    public interface TransactionClickListener {
        void onTransactionClicked(Transaction t);
    }


}
