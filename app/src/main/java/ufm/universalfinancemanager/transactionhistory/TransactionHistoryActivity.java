package ufm.universalfinancemanager.transactionhistory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;
import ufm.universalfinancemanager.R;
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
}
