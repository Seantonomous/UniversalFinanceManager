package ufm.universalfinancemanager.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.addeditaccount.AddEditAccountPresenter;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.earningshistory.EarningsHistoryActivity;
import ufm.universalfinancemanager.networth.NetworthActivity;
import ufm.universalfinancemanager.transactionhistory.TransactionHistoryActivity;
import ufm.universalfinancemanager.util.ActivityUtils;


@ActivityScoped
public class HomeActivity extends DaggerAppCompatActivity {
    @Inject
    HomePresenter mPresenter;
    @Inject
    HomeFragmentChart1 mFragment1;
    @Inject
    HomeFragmentChart2 mFragment2;
    @Inject
    HomeFragmentChart3 mFragment3;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

//    private ListView list_view;

    Button bnNext;
    Button bnPrevious;
    TextView tvChartName;
    public int chartNum = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        bnNext = findViewById(R.id.bnNext);
        bnPrevious = findViewById(R.id.bnPrevious);
        tvChartName = findViewById(R.id.tvHomeChartName);

        //if(navigationView == null) {
        setupDrawerContent(navigationView);


        //Set up the navigation drawer toggle
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
        getSupportActionBar().setTitle(R.string.home_title);

        // Loads the first chart
        nextChart();

        bnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextChart();
            }
        });

        bnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Logic for switching charts is by setting to next chart,
                   using forward twice to get back to the previous (only 3 charts to navigate)
                 */
                for (int i = 0; i < 2; i++) {
                    nextChart();
                }
            }
        });

    }

    private void setToolbarTitle(@Nullable String Id) {
        if(Id == null) {
            setTitle(R.string.home_title);
        }
    }

    private void nextChart() {

        if(getSupportFragmentManager().findFragmentById(R.id.frag_container) != null) {
            getSupportFragmentManager()
                    .beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.frag_container)).commit();
        }

        switch(chartNum) {
            case 0:
                HomeFragmentChart1 homeFragment1 =  (HomeFragmentChart1)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                homeFragment1 = mFragment1;


                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_container, homeFragment1)
                        .commit();
                tvChartName.setText("Budget Spend");
                chartNum = 1;

                break;
            case 1:
                HomeFragmentChart2 homeFragment2 =  (HomeFragmentChart2)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                homeFragment2 = mFragment2;

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_container, homeFragment2)
                        .commit();

                tvChartName.setText("Net Worth");
                chartNum = 2;

                break;
            case 2:
                HomeFragmentChart3 homeFragment3 =  (HomeFragmentChart3)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                homeFragment3 = mFragment3;

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_container, homeFragment3)
                        .commit();

                tvChartName.setText("Category Spend");
                chartNum = 0;

                break;
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
                                // switch to home activity
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                break;
                            case R.id.budget_menu_item:
                                //switch to budget overview activity
                                break;
                            case R.id.trans_history_menu_item:
                                //switch to transaction history activity
                                startActivity(new Intent(getApplicationContext(), TransactionHistoryActivity.class));
                                break;
                            case R.id.earnings_menu_item:
                                //switch to earnings menu
                                startActivity(new Intent(getApplicationContext(), EarningsHistoryActivity.class));
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
