package ufm.universalfinancemanager.addeditbudget;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryPresenter;
import ufm.universalfinancemanager.db.UserRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.TextValidator;
import ufm.universalfinancemanager.db.entity.Category;
import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by Areeba on 2/23/2018.
 */

public class AddEditBudgetFragment extends DaggerFragment implements AddEditBudgetContract.View{
    private User mUser;
    Button cancel_button;
    Button submit_button;
    private Spinner category_spinner;
    private EditText edit_amount;
    private EditText edit_name;
    private EditText edit_startdate;
    //private Date enddate;
    private EditText edit_enddate;
   // private Spinner duration;
    boolean valid_amount = false;
    boolean valid_name = false;
    private boolean isEditing = false;
   // private double currentValue;
    Transaction t;

    @Inject
    AddEditBudgetPresenter mPresenter;

    private ArrayAdapter<Category> categorySpinnerAdapter;

    private Calendar calendar;
    private Calendar calendar2;
    private DatePickerDialog.OnDateSetListener startdate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };
    private DatePickerDialog.OnDateSetListener enddate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar2.set(Calendar.YEAR, year);
            calendar2.set(Calendar.MONTH, monthOfYear);
            calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_budget_fragment, container, false);
        edit_amount = root.findViewById(R.id.add_amount);
        edit_name = root.findViewById(R.id.name);
        edit_startdate = root.findViewById(R.id.date);
        edit_enddate = root.findViewById(R.id.date2);
        category_spinner = root.findViewById(R.id.category);
        cancel_button = root.findViewById(R.id.cancel);
        submit_button = root.findViewById(R.id.submit);
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        updateDate();
        //mPresenter.getUpdatedCategories(Flow.INCOME);

        edit_name.addTextChangedListener(new TextValidator(edit_name) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() == 0) {
                    textView.setError("Budget must have a name");
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
                    textView.setError("Budget must have an amount");
                    valid_amount = false;
                }else {
                    valid_amount = true;
                }
            }
        });
        edit_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), startdate, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edit_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), enddate, calendar2.get(Calendar.YEAR),
                        calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!valid_amount || !valid_name ) {
                    if(!valid_name)
                        edit_name.setError("Budget must have a name!");
                    if(!valid_amount)
                        edit_amount.setError("Budget must have an amount!");
                    return;
                }

            mPresenter.saveBudget(edit_name.getText().toString(),
                    category_spinner.getSelectedItem().toString(),
                    Double.parseDouble(edit_amount.getText().toString()),
                    calendar.getTime(),
                    calendar2.getTime()
            );
                showLastActivity(false);
            }

        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditing) {
                    mPresenter.deleteBudget();
                }else
                    showLastActivity(false);
            }
        });
        return root;
    }

    @Inject
    public AddEditBudgetFragment() {}

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
    public void showLastActivity(boolean success) {
        if(success) {
            getActivity().setResult(Activity.RESULT_OK);
        }else {
            getActivity().setResult(Activity.RESULT_CANCELED);
        }

        getActivity().finish();
    }

    @Override
    public void setupFragmentContent(boolean editing) {
        this.isEditing = editing;
        if(isEditing)
            cancel_button.setText("Delete");
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateCategories(@Nullable List<Category> categories) {
        categorySpinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, categories);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(categorySpinnerAdapter);
    }

    @Override
    public void populateExistingFields(String name, Category category, Double amount, Date startDate, Date endDate) {
        isEditing = true;
        edit_name.setText(name);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        category_spinner.setSelection(categorySpinnerAdapter.getPosition(category));
        edit_amount.setText(Double.toString(amount));
        calendar.setTime(startDate);
        calendar2.setTime(endDate);
        mPresenter.getUpdatedCategories(Flow.INCOME);getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        updateDate();
    }


    public void updateDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edit_startdate.setText(sdf.format(calendar.getTime()));
        edit_enddate.setText(sdf.format(calendar2.getTime()));
    }

}
