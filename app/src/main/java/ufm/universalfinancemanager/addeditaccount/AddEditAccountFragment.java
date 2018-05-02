package ufm.universalfinancemanager.addeditaccount;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.TextValidator;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Created by smh7 on 1/23/18.
 */

public class AddEditAccountFragment extends DaggerFragment implements AddEditAccountContract.View {
    @Inject
    public AddEditAccountPresenter mPresenter;

    EditText edit_name;
    CurrencyEditText edit_amount;
    Spinner type_spinner;
    Button submit_button;
    Button cancel_button;

    boolean valid_name = false;
    boolean valid_amount = false;
    boolean isediting = false;

    @Inject
    public AddEditAccountFragment() {}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addedit_account_fragment, container, false);

        edit_name = root.findViewById(R.id.name);
        edit_amount = root.findViewById(R.id.balance);
        type_spinner = root.findViewById(R.id.accounttype);
        submit_button = root.findViewById(R.id.submit_account);
        cancel_button = root.findViewById(R.id.cancel_account);

        edit_name.addTextChangedListener(new TextValidator(edit_name) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() == 0) {
                    textView.setError("Account must have a name");
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
                    textView.setError("Account must have a starting amount");
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

                if(!valid_amount || !valid_name)
                    return;


                stringSelectedType = type_spinner.getSelectedItem().toString();
                switch(stringSelectedType) {
                    case ("Checking"):
                        selectedType = AccountType.CHECKING;
                        break;
                    case ("Savings"):
                        selectedType = AccountType.SAVINGS;
                        break;
                    case ("Cash"):
                        selectedType = AccountType.CASH;
                        break;
                    case ("Credit Card"):selectedType = AccountType.CREDIT_CARD;
                        break;
                    default:
                        selectedType = AccountType.CHECKING;
                }

                double amount = Double.parseDouble(edit_amount.getText().toString().replace("$","").replace(",",""));
                mPresenter.saveAccount(edit_name.getText().toString(),
                        amount,
                        selectedType);
            }
        });

        /*this cancel button will eventually serve as the delete
        but until we get the logic straigtened out it will just cancel
        the add/edit*/
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isediting) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle("Delete Account")
                            .setMessage("Are you sure you want to delete this Account?" +
                                    " All Transactions associated with it will be deleted")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mPresenter.deleteAccount();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else
                    showLastActivity(false);
            }
        });

        return root;
    }

    @Override
    public void showLastActivity(boolean success) {
        if(success) {
            getActivity().setResult(Activity.RESULT_OK);
        }else {
            getActivity().setResult(Activity.RESULT_CANCELED);
        }

        getActivity().finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void populateExistingAccountInfo(String name, double balance, AccountType type) {
        isediting = true;

        cancel_button.setText("Delete");

        edit_name.setText(name);

        NumberFormat formatter = new DecimalFormat("#0.00");
        edit_amount.setText(formatter.format(balance));
        edit_amount.setEnabled(false);  //Disable balance editing

        ArrayAdapter<String> adapter = (ArrayAdapter<String>)type_spinner.getAdapter();

        if(type == AccountType.CHECKING)
            type_spinner.setSelection(adapter.getPosition("Checking")); //1 -> Checking
        else if(type == AccountType.SAVINGS)
            type_spinner.setSelection(adapter.getPosition("Savings"));
        else if(type == AccountType.CASH)
            type_spinner.setSelection(adapter.getPosition("Cash"));
        else if(type == AccountType.CREDIT_CARD)
            type_spinner.setSelection(adapter.getPosition("Credit Card"));

        //Disable type spinner
        type_spinner.setEnabled(false);
    }
}
