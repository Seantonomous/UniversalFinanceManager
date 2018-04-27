package ufm.universalfinancemanager.budgetoverview;

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
import ufm.universalfinancemanager.db.entity.Budget;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.transactionhistory.TransactionAdapter;
import ufm.universalfinancemanager.transactionhistory.TransactionHistoryFragment;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetFragment extends DaggerFragment implements BudgetContract.View {
    private View mNoBudgetView;
    private TextView mNoBudgetsTextView;
    private View mBudgetsView;

    @Inject
    BudgetPresenter mPresenter;
    private BudgetAdapter mAdapter;
    //public int filter;
    @Inject
    public BudgetFragment() {}

    BudgetFragment.BudgetClickListener mClickListener = new BudgetFragment.BudgetClickListener() {
        @Override
        public void onBudgetClicked(Budget b) {
            mPresenter.editBudget(b.getName());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new BudgetAdapter(new ArrayList<Budget>(0), mClickListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.budget_overview_fragment, container, false);
        ListView listview = root.findViewById(R.id.budget_list);
        listview.setAdapter(mAdapter);
       // mBudgetsView = root.findViewById(R.id.budgetLayout);
        //mNoBudgetView = root.findViewById(R.id.noBudgetsLayout);
      //  mNoBudgetsTextView = root.findViewById(R.id.noBudgetsText);
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
                mPresenter.addBudget();
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
    }

    @Override
    public void showBudgets(List<Budget> budgets) {

        mAdapter.replaceItems(budgets);
       // mBudgetsView.setVisibility(View.VISIBLE);
        //mNoBudgetsTextView.setVisibility(View.GONE);
    }

    @Override
    public void showAddEditBudgets() {
        startActivityForResult(new Intent(getContext(), AddEditBudgetActivity.class), 1);
    }

    @Override
    public void showAddEditBudget(String id) {
        Intent intent = new Intent(getContext(), AddEditBudgetActivity.class);
        //Todo: make EXTRA_ID in TransactionAddEditActivity
        intent.putExtra("EDIT_BUDGET_ID", id);
        //Todo: make request code in TransactionAddEditAcitivity for '2'
        startActivityForResult(intent, 2);
    }
    public interface BudgetClickListener {
        void onBudgetClicked(Budget b);
    }
}
