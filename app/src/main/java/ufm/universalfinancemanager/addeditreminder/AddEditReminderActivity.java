
package ufm.universalfinancemanager.addeditreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountFragment;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountPresenter;
import ufm.universalfinancemanager.budgetoverview.BudgetActivity;
import ufm.universalfinancemanager.earningshistory.EarningsHistoryActivity;
import ufm.universalfinancemanager.home.HomeActivity;
import ufm.universalfinancemanager.networth.NetworthActivity;
import ufm.universalfinancemanager.transactionhistory.TransactionHistoryActivity;
import ufm.universalfinancemanager.util.ActivityUtils;

/**
 * Created by Areeba on 2/17/2018.
 */

public class AddEditReminderActivity extends DaggerAppCompatActivity {
    @Inject
    AddEditReminderPresenter mPresenter;
    @Inject
    AddEditReminderFragment mFragment;

    @Inject
    @Nullable
    String reminderName;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedit_reminder_activity);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //if(navigationView == null) {
            setupDrawerContent(navigationView);
        //}

        AddEditReminderFragment addEditReminderFragment =
                (AddEditReminderFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(addEditReminderFragment == null) {
            addEditReminderFragment = mFragment;

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditReminderFragment , R.id.contentFrame);
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

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        //setTitle("Add Reminder");
        setToolbarTitle(reminderName);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setToolbarTitle(@Nullable String name) {
        if(name == null) {
            setTitle(R.string.reminder_add_title);
        }
        else{
            setTitle(R.string.reminder_edit_title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if(mDrawerToggle.onOptionsItemSelected(item))
                    return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.home_menu_item:
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                //switch to home activity
                                break;
                            case R.id.budget_menu_item:
                                startActivity(new Intent(getApplicationContext(), BudgetActivity.class));
                                //switch to budget overview activity
                                break;
                            case R.id.trans_history_menu_item:
                                startActivity(new Intent(getApplicationContext(), TransactionHistoryActivity.class));
                                //switch to transaction history activity
                                break;
                            case R.id.earnings_menu_item:
                                startActivity(new Intent(getApplicationContext(), EarningsHistoryActivity.class));
                                //switch to input output activity
                                break;
                            case R.id.networth_menu_item:
                                startActivity(new Intent(getApplicationContext(), NetworthActivity.class));
                                //switch to networth activity
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
}