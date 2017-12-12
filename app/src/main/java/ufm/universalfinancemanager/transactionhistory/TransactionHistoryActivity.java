package ufm.universalfinancemanager.transactionhistory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.ui.Account_Add;
import ufm.universalfinancemanager.ui.Budget_Add;
import ufm.universalfinancemanager.ui.Category_Add;
import ufm.universalfinancemanager.ui.Reminder_Add;
import ufm.universalfinancemanager.ui.Transaction_Add;
import ufm.universalfinancemanager.util.ActivityUtils;

/**
 * Created by smh7 on 12/11/17.
 */

public class TransactionHistoryActivity extends DaggerAppCompatActivity {
    @Inject
    TransactionHistoryPresenter mPresenter;
    @Inject
    Lazy<TransactionHistoryFragment> transactionHistoryFragmentProvider;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_history_activity);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if(navigationView == null) {
            setupDrawerContent(navigationView);
        }

        TransactionHistoryFragment transactionHistoryFragment =
                (TransactionHistoryFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(transactionHistoryFragment == null) {
            transactionHistoryFragment = transactionHistoryFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    transactionHistoryFragment, R.id.contentFrame);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
            }

        };
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.home_menu_item:
                                break;
                            case R.id.budget_menu_item:
                                break;
                            case R.id.trans_history_menu_item:
                                break;
                            case R.id.inout_menu_item:
                                break;
                            case R.id.networth_menu_item:
                                break;
                            case R.id.reminder_menu_item:
                                break;
                            case R.id.settings_menu_item:
                                break;
                        }

                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        switch(id) {
            case R.id.action_add_transaction:
                mPresenter.addTransaction();
                return true;
            case R.id.action_add_account:
                //Intent intent_account = new Intent(this, Account_Add.class);
                //intent_account.putExtra(EXTRA_USER, (Parcelable)sessionUser);
                //startActivityForResult(intent_account, 2);
                return true;
            case R.id.action_add_category:
                //Intent intent_category = new Intent(this, Category_Add.class);
                //intent_category.putExtra(EXTRA_USER, (Parcelable)sessionUser);
                //startActivityForResult(intent_category, 3);
                return true;
            case R.id.action_add_reminder:
                //startActivity(new Intent(this, Reminder_Add.class));
                return true;
            case R.id.action_add_budget:
                //startActivity(new Intent(this, Budget_Add.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
