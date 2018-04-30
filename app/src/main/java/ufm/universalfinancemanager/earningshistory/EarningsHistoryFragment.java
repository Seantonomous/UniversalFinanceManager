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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountActivity;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryActivity;
import ufm.universalfinancemanager.addeditreminder.AddEditReminderActivity;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionActivity;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.db.entity.Category;

/**
 * Created by Faiz on 2/26/2018.
 */

public class EarningsHistoryFragment extends DaggerFragment implements EarningsHistoryContract.View  {

    @Inject
    EarningsHistoryPresenter mPresenter;

    private EarningsAdapter mAdapter;

    private EarningsClickListener mListener = new EarningsClickListener() {
        @Override
        public void onCategoryClicked(EarningsHistoryListItem category) {
            mPresenter.editCategory(category);
        }
    };

    @Inject
    public EarningsHistoryFragment() {
        //Required to be empty since extends Fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAdapter = new EarningsAdapter(new ArrayList<EarningsHistoryListItem>(0), mListener);
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
    public void showEarningsHistory(List<EarningsHistoryListItem> list){
        mAdapter.replaceItems(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragment with the corresponding layout
        View root = inflater.inflate(R.layout.earnings_history_fragment, container, false);

        TextView thisMonthHeader = root.findViewById(R.id.thisMonthTextView);
        TextView lastMonthHeader = root.findViewById(R.id.lastMonthTextView);

        Calendar cal = Calendar.getInstance();

        thisMonthHeader.setText(new SimpleDateFormat("MMM").format(cal.getTime()) + ": ");

        cal.add(Calendar.MONTH, -1);

        lastMonthHeader.setText(new SimpleDateFormat("MMM").format(cal.getTime()) + ": ");

        ListView listview = root.findViewById(R.id.earningsList);
        listview.setAdapter(mAdapter);

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
                break;
            case R.id.action_add_reminder:
                startActivity(new Intent(getContext(), AddEditReminderActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
    }

    @Override
    public void showEditCategory(String categoryName) {
        Intent intent = new Intent(getContext(), AddEditCategoryActivity.class);
        intent.putExtra("CATEGORY_NAME", categoryName);
        startActivity(intent);
    }

    public interface EarningsClickListener {
        void onCategoryClicked(EarningsHistoryListItem category);
    }

}
