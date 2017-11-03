package ufm.universalfinancemanager;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Areeba on 11/2/2017.
 */

public class Transaction_Add extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transactions);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }
    //public Transaction_Add(){
    //}
    //@Nullable
   // @Override
   // public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
     //   View view = inflater.inflate(R.layout.add_transactions, container, false);
       //return view;
    //}
}
