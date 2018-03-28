package ufm.universalfinancemanager.addedittransaction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.TextValidator;
import ufm.universalfinancemanager.support.atomic.Account;
import ufm.universalfinancemanager.support.atomic.Category;

/**
 * Created by smh7 on 12/14/17.
 */

@ActivityScoped
public class AddEditTransactionFragment extends DaggerFragment implements AddEditTransactionContract.View {
    @Inject
    AddEditTransactionPresenter mPresenter;

    public static final String ARGUMENT_EDIT_TRANSACTION_ID = "EDIT_TRANSACTION_ID";

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
    private Spinner duration_spinner;
    private EditText edit_notes;
    private Button done_button;
    private Button cancel_button;
    private TextView date_textView;

    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    private boolean valid_name = false;
    private boolean valid_amount = false;
    private boolean isEditing = false;

    private ArrayAdapter<Category> categorySpinnerAdapter;
    private ArrayAdapter<Account> accountSpinnerAdapter;

    @Inject
    public AddEditTransactionFragment() {
        //Fragment constructors must be empty
    }

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

        calendar = Calendar.getInstance();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addedit_transaction_fragment, container, false);

        edit_name = root.findViewById(R.id.name);
        edit_amount = root.findViewById(R.id.amount);
        edit_date = root.findViewById(R.id.date);
        edit_notes = root.findViewById(R.id.notes);
        flow_radioGroup = root.findViewById(R.id.flow);
        income_radioButton = root.findViewById(R.id.flow_income);
        expense_radioButton = root.findViewById(R.id.flow_expense);
        transfer_radioButton = root.findViewById(R.id.flow_transfer);
        toAccount_spinner = root.findViewById(R.id.toaccount);
        fromAccount_spinner = root.findViewById(R.id.fromaccount);
        category_spinner = root.findViewById(R.id.category);
        duration_spinner = root.findViewById(R.id.duration);
        done_button = root.findViewById(R.id.done);
        cancel_button = root.findViewById(R.id.cancel);
        date_textView = root.findViewById(R.id.dateTextView);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        updateDate();

        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!valid_name || !valid_amount) {
                    if(!valid_name)
                        edit_name.setError("Transaction must have a name!");
                    if(!valid_amount)
                        edit_amount.setError("Transaction must have an amount!");

                    return;
                }

                if(income_radioButton.isChecked()) {
                    mPresenter.saveTransaction(edit_name.getText().toString(),
                            Flow.INCOME,
                            Double.parseDouble(edit_amount.getText().toString()),
                            category_spinner.getSelectedItem().toString(),
                            null,
                            toAccount_spinner.getSelectedItem().toString(),
                            calendar.getTime(),
                            edit_notes.getText().toString());
                }
                else if(expense_radioButton.isChecked()) {
                    mPresenter.saveTransaction(edit_name.getText().toString(),
                            Flow.OUTCOME,
                            Double.parseDouble(edit_amount.getText().toString()),
                            category_spinner.getSelectedItem().toString(),
                            fromAccount_spinner.getSelectedItem().toString(),
                            null,
                            calendar.getTime(),
                            edit_notes.getText().toString());
                }else { //transfer
                    mPresenter.saveTransaction(edit_name.getText().toString(),
                            Flow.TRANSFER,
                            Double.parseDouble(edit_amount.getText().toString()),
                            null,
                            fromAccount_spinner.getSelectedItem().toString(),
                            toAccount_spinner.getSelectedItem().toString(),
                            calendar.getTime(),
                            edit_notes.getText().toString());
                }
            }
        });

        edit_name.addTextChangedListener(new TextValidator(edit_name) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() == 0 && !transfer_radioButton.isChecked()) {
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

        income_radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFlowChecked(view);
            }
        });

        expense_radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFlowChecked(view);
            }
        });

        transfer_radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFlowChecked(view);
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEditing) {
                    mPresenter.deleteTransaction();
                }else
                    showLastActivity(false);
            }
        });

        return root;
    }

    @Override
    public void setupFragmentContent(@Nullable List<Account> accounts, boolean editing) {

        isEditing = editing;

        accountSpinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, accounts);

        if(isEditing)
            cancel_button.setText("Delete");
        else {
            expense_radioButton.setChecked(true);
            onFlowChecked(expense_radioButton);
        }

        accountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromAccount_spinner.setAdapter(accountSpinnerAdapter);
        toAccount_spinner.setAdapter(accountSpinnerAdapter);
    }

    @Override
    public void updateCategories(List<Category> categories) {
        categorySpinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, categories);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(categorySpinnerAdapter);

    }

    @Override
    public void populateExistingFields(String name, Double amount, Flow flow,
                                       Category categoryName, @Nullable Account fromAccountName,
                                       @Nullable Account toAccountName, Date date, @Nullable String notes) {
        edit_name.setText(name);
        edit_amount.setText(Double.toString(amount));

        if(flow == Flow.INCOME) {
            income_radioButton.setChecked(true);
            toAccount_spinner.setSelection(accountSpinnerAdapter.getPosition(toAccountName));

            onFlowChecked(income_radioButton);
            category_spinner.setSelection(categorySpinnerAdapter.getPosition(categoryName));
        }else if(flow == Flow.OUTCOME) {
            expense_radioButton.setChecked(true);
            fromAccount_spinner.setSelection(accountSpinnerAdapter.getPosition(fromAccountName));

            onFlowChecked(expense_radioButton);
            category_spinner.setSelection(categorySpinnerAdapter.getPosition(categoryName));
        }else {
            transfer_radioButton.setChecked(true);
            onFlowChecked(transfer_radioButton);
        }

        calendar.setTime(date);
        updateDate();
    }

    public void onFlowChecked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.flow_income:
                if (checked)
                    // Set focus to Name EditText.
                    edit_name.requestFocus();
                toAccount_spinner.setEnabled(true); // Enable ToAccount Spinner
                fromAccount_spinner.setEnabled(false); // Disable FromAccount Spinner
                category_spinner.setEnabled(true);  //Enable Category Spinner

                //Replace current categories w/ income categories
                mPresenter.getUpdatedCategories(Flow.INCOME);

                // Change Category Spinner to show Income type Categories
                edit_name.setEnabled(true);
                edit_name.setPaintFlags(edit_name.getPaintFlags()  & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                break;
            case R.id.flow_expense:
                if (checked)
                    // Set focus to Name EditText.
                    edit_name.requestFocus();
                toAccount_spinner.setEnabled(false); // Disable ToAccount Spinner
                fromAccount_spinner.setEnabled(true); // Enable FromAccount Spinner
                category_spinner.setEnabled(true);  //Enable Category Spinner

                //Replace current categories w/ expense categories
                mPresenter.getUpdatedCategories(Flow.OUTCOME);

                // Change Category Spinner to show Expense type Categories
                edit_name.setEnabled(true);
                edit_name.setPaintFlags(edit_name.getPaintFlags()  & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                break;
            case R.id.flow_transfer:
                if (checked)
                    //Set focus to Transfer EditText;
                    edit_amount.requestFocus();
                toAccount_spinner.setEnabled(true); // Enable ToAccount Spinner
                fromAccount_spinner.setEnabled(true); // Enable FromAccount Spinner
                category_spinner.setEnabled(false); // Disable Category Spinner

                edit_name.setEnabled(false);
                edit_name.setPaintFlags(edit_name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                //clear any text error from having no name
                edit_name.setError(null);
                break;
        }
    }

    @Override
    public void showLastActivity(boolean success) {
        if(success)
            getActivity().setResult(Activity.RESULT_OK);
        else
            getActivity().setResult(Activity.RESULT_CANCELED);

        getActivity().finish();
    }

    public void updateDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edit_date.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
