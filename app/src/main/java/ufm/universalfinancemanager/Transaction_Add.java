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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.StringBufferInputStream;
import java.sql.Date;

/**
 * Created by Areeba on 11/2/2017.
 */

public class Transaction_Add extends Activity {
    private boolean valid_input = true;
    private User sessionUser;

    private EditText edit_name;
    private EditText edit_amount;
    private EditText edit_date;
    private Spinner account_spinner;
    private Spinner category_spinner;
    private EditText edit_notes;
    private Button done_button;
    private Button cancel_button;

    private String selected_account;
    private String selected_category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transactions);

        edit_name = (EditText)findViewById(R.id.name);
        edit_amount = (EditText)findViewById(R.id.amount);
        edit_date = (EditText)findViewById(R.id.date);
        account_spinner = (Spinner)findViewById(R.id.account);
        category_spinner = (Spinner)findViewById(R.id.category);
        edit_notes = (EditText)findViewById(R.id.notes);
        done_button = (Button)findViewById(R.id.done);
        cancel_button = (Button)findViewById(R.id.cancel);

        Bundle args = getIntent().getExtras();

        if(args == null)
            sessionUser = null;
        else {
            sessionUser = args.getParcelable("ufm.universalfinancemanager.USER");

            ArrayAdapter<Account> account_adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, sessionUser.getAccounts());
            account_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            account_spinner.setAdapter(account_adapter);
        }


        edit_name.addTextChangedListener(new TextValidator(edit_name) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() == 0) {
                    textView.setError("Transaction must have a name");
                    valid_input = false;
                }else {
                    valid_input = true;
                }
            }
        });

        edit_amount.addTextChangedListener(new TextValidator(edit_amount) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() == 0) {
                    textView.setError("Transaction must have an ammount");
                    valid_input = false;
                }else {
                    valid_input = true;
                }
            }
        });

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid_input) {
                    /*sessionUser.addTransaction(
                            new Transaction(edit_name.getText().toString(),
                                    1,
                                    Double.parseDouble(edit_amount.getText().toString()),
                                    sessionUser.getCategory(category_spinner.getSelectedItem().toString()),
                                    sessionUser.getAccount(account_spinner.getSelectedItem().toString()),
                                    ,
                                    edit_notes.getText().toString())
                    );*/

                    finish();
                }
            }
        });
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
