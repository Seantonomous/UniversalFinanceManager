package ufm.universalfinancemanager;

import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main_Activity extends AppCompatActivity {
    private String[] drawer_items;
    private DrawerLayout drawer_layout;
    private ListView list_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        list_view = (ListView)findViewById(R.id.drawer);
        drawer_items = getResources().getStringArray(R.array.drawer_items);

        list_view.setAdapter(new ArrayAdapter<String>(this, R.layout.nav_drawer_item, drawer_items));
    }
}
