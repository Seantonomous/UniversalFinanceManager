package ufm.universalfinancemanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Areeba on 11/2/2017.
 */

public class Account_Add extends Activity {
    private boolean valid_name = false;
    private boolean valid_amount = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);

        final EditText edit_name = (EditText)findViewById(R.id.name);
        final EditText edit_amount = (EditText)findViewById(R.id.balance);
        final Spinner type_spinner = (Spinner)findViewById(R.id.accounttype);
        Button submit_button = (Button)findViewById(R.id.submit_account);
        Button cancel_button = (Button)findViewById(R.id.cancel_account);
        final User sessionUser;

        Bundle args = getIntent().getExtras();

        sessionUser = args.getParcelable("ufm.universalfinancemanager.USER");


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
                    textView.setError("Transaction must have a name");
                    valid_amount = false;
                }else {
                    valid_amount = true;
                }
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringSelectedType;
                AccountType selectedType;
                Calendar today = Calendar.getInstance();

                if(valid_amount && valid_name) {
                    if(sessionUser.hasAccount(edit_name.getText().toString())) {
                        //Can't add, user already has account
                    } else {
                        stringSelectedType = type_spinner.getSelectedItem().toString();
                        switch(stringSelectedType) {
                            case("Checking"):
                                selectedType = AccountType.CHECKING;
                                break;
                            case("Savings"):
                                selectedType = AccountType.SAVINGS;
                                break;
                            case("Cash"):
                                selectedType = AccountType.CASH;
                                break;
                            case("Credit Card"):
                                selectedType = AccountType.CREDIT_CARD;
                                break;
                            default:
                                selectedType = AccountType.CHECKING;
                        }

                        sessionUser.addAccount(new Account(edit_name.getText().toString(),
                                selectedType,
                                Double.parseDouble(edit_amount.getText().toString()),
                                today.getTime())
                        );

                        finish();
                    }

                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    //public Account_Add() {

    //}

    //@Nullable
    //@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      //  View view = inflater.inflate(R.layout.add_account, container, false);
        //return view;
    //}
}
