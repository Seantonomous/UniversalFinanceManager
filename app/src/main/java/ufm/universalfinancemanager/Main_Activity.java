/* Author: Sean Hansen
* ID: 108841276
* Date Started: 9/27/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Date;

import ufm.universalfinancemanager.User;

public class Main_Activity extends AppCompatActivity {
    private String[] drawer_items;
    private DrawerLayout drawer_layout;
    private ListView list_view;
    private ActionBarDrawerToggle drawer_toggle;
    private User sessionUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        list_view = (ListView)findViewById(R.id.drawer);

        //set the drawer
        list_view.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                selectItem(pos);
            }
        }
        );

        /**************TEST DATA*************/
        sessionUser = new User("Test");
        sessionUser.addAccount(new Account("Checking", AccountType.DEBIT, 0, new Date()));
        sessionUser.addTransaction(new Transaction("Gas", -1, 30.24, new Category("Transportation"),
                sessionUser.getAccount("Checking"),new Date()));
        /**************TEST DATA*************/

        drawer_items = getResources().getStringArray(R.array.drawer_items);

        drawer_toggle = new ActionBarDrawerToggle(this,drawer_layout,R.string.drawer_open,
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

        drawer_layout.addDrawerListener(drawer_toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Populate navigation drawer with page names
        list_view.setAdapter(new ArrayAdapter<>(this, R.layout.nav_drawer_item, drawer_items));

    }

    private void selectItem(int position) {
        switch(position) {
            case(1):    //HOME
                break;
            case(2):    //BUDGET
                break;
            case(3):    //TRANSACTIONS

                //Create a new Transaction_Activity to place in main view container
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("transactions", sessionUser.getTransactions());

                Fragment fragment = new Transaction_Activity();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();

                //Replace the current container with the fragment and commit changes
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

                //Find the transaction list view, and set its adapter to handle the user's transactions
                //ListView lv = fragment.getView().findViewById(R.id.transaction_list);
                //lv.setAdapter(new TransactionAdapter(getApplicationContext(), sessionUser.getTransactions()));

                //Set the action bar title to "Transaction History"
                try {
                    getSupportActionBar().setTitle(R.string.transaction_title);
                }catch(java.lang.NullPointerException e) {

                }

                //Highlight touched item in the nav drawer and then close the nav drawer
                list_view.setItemChecked(position, true);
                drawer_layout.closeDrawer(list_view);
                break;
            case(4):    //INCOME/OUTCOME
                break;
            case(5):    //NET WORTH
                break;
            case(6):    //Reminders
                break;
            case(7):    //Settings
                break;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawer_toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawer_toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawer_toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
