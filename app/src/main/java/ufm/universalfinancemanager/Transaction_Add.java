package ufm.universalfinancemanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.StringBufferInputStream;
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Areeba on 11/2/2017.
 */

public class Transaction_Add extends Activity {
    private boolean valid_name = false;
    private boolean valid_amount = false;
    private User sessionUser;

    private EditText edit_name;
    private EditText edit_amount;
    private EditText edit_date;
    private RadioGroup flow_radioGroup;
    private RadioButton income_radioButton;
    private RadioButton expense_radioButton;
    private RadioButton transfer_radioButton;
    private Spinner toAccount_spinner;
    private Spinner fromAccount_spinner;
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
        flow_radioGroup = (RadioGroup) findViewById(R.id.flow);
        income_radioButton = (RadioButton) findViewById(R.id.flow_income);
        expense_radioButton = (RadioButton) findViewById(R.id.flow_expense);
        transfer_radioButton = (RadioButton) findViewById(R.id.flow_transfer);
        toAccount_spinner = (Spinner)findViewById(R.id.toaccount);
        fromAccount_spinner = (Spinner)findViewById(R.id.fromaccount);
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
            toAccount_spinner.setAdapter(account_adapter);
            fromAccount_spinner.setAdapter(account_adapter);
            
        }


        edit_name.addTextChangedListener(new TextValidator(edit_name) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() == 0) {
                    textView.setError("Transaction must have a name");
                    valid_name = false;
                }else {
                    valid_name = true;
                }
            }
        });

        edit_amount.addTextChangedListener(new TextValidator(edit_amount) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() == 0) {
                    textView.setError("Transaction must have an amount");
                    valid_amount = false;
                }else {
                    valid_amount = true;
                }
            }
        });

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid_name && valid_amount) {
                    if(income_radioButton.isChecked()) {

                    }else if(expense_radioButton.isChecked()) {

                        Transaction newTransaction = new Transaction(edit_name.getText().toString(),
                                Flow.OUTCOME,
                                Double.parseDouble(edit_amount.getText().toString()),
                                sessionUser.getCategory(category_spinner.getSelectedItem().toString()),
                                sessionUser.getAccount(fromAccount_spinner.getSelectedItem().toString()),
                                Calendar.getInstance().getTime(),
                                edit_notes.getText().toString());

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", newTransaction);
                        setResult(Activity.RESULT_OK, returnIntent);
                    }else { //transfer

                    }
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

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.flow_income:
                if (checked)
                    toAccount_spinner.setEnabled(true); // Enable ToAccount Spinner
                    fromAccount_spinner.setEnabled(false); // Disable FromAccount Spinner
                    // Change Category Spinner to show Income type Categories
                    break;
            case R.id.flow_expense:
                if (checked)
                    toAccount_spinner.setEnabled(false); // Disable ToAccount Spinner
                    fromAccount_spinner.setEnabled(true); // Enable FromAccount Spinner
                    // Change Category Spinner to show Expense type Categories
                    break;
            case R.id.flow_transfer:
                if (checked)
                    toAccount_spinner.setEnabled(true); // Enable ToAccount Spinner
                    fromAccount_spinner.setEnabled(true); // Enable FromAccount Spinner
                    category_spinner.setEnabled(false); // Disable Category Spinner
                    break;
        }
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
