package ufm.universalfinancemanager.budgetoverview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.networth.NetworthActivity;
import ufm.universalfinancemanager.transactionhistory.TransactionHistoryActivity;
import ufm.universalfinancemanager.util.ActivityUtils;

/**
 * Created by Areeba on 3/24/2018.
 */

public class BudgetActivity extends DaggerAppCompatActivity {

    @Inject
    BudgetPresenter mPresenter;
    @Inject
    Lazy<BudgetFragment> mFragmentProvider;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_overview_activity);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if(navigationView != null) {
            setupDrawerContent(navigationView);
        }

        BudgetFragment budgetFragment =
                (BudgetFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(budgetFragment == null) {
            budgetFragment = mFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    budgetFragment, R.id.contentFrame);
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

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.budget_title);
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
                                //switch to home activity
                                break;
                            case R.id.budget_menu_item:
                                //switch to budget overview activity
                                break;
                            case R.id.trans_history_menu_item:
                                //Do nothing we're already here
                                startActivity(new Intent(getApplicationContext(), TransactionHistoryActivity.class));
                                break;
                            case R.id.earnings_menu_item:
                                //switch to input output activity
                                break;
                            case R.id.networth_menu_item:
                                //switch to networth activity
                                startActivity(new Intent(getApplicationContext(), NetworthActivity.class));
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
