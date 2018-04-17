package ufm.universalfinancemanager.addeditbudget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryPresenter;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.TextValidator;
import ufm.universalfinancemanager.support.atomic.Category;
import ufm.universalfinancemanager.support.atomic.User;

/**
 * Created by Areeba on 2/23/2018.
 */

public class AddEditBudgetFragment extends DaggerFragment implements AddEditBudgetContract.View{
    private User mUser;
    Button cancel_button;
    Button submit_button;
    private Spinner category;
    private EditText edit_amount;
    private EditText edit_name;
   // private Spinner duration;
    boolean valid_amount = false;
    boolean valid_name = false;
   // private double currentValue;
    Transaction t;
    @Inject
    AddEditBudgetPresenter mPresenter;
    private ArrayAdapter<Category> categorySpinnerAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_budget_fragment, container, false);
        edit_amount = root.findViewById(R.id.add_amount);
        edit_name = root.findViewById(R.id.name);
        category = root.findViewById(R.id.category);
        cancel_button = root.findViewById(R.id.cancel);
        submit_button = root.findViewById(R.id.submit);

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

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!valid_amount || !valid_name) {
                    if(!valid_name)
                        edit_name.setError("Budget must have a name!");
                    if(!valid_amount)
                        edit_amount.setError("Budget must have an amount!");
                    return;
                }
           // currentValue = getExpenseTransactions(category.getSelectedItem().toString());
            mPresenter.loadTransactions(edit_name.getText().toString(),
                    category.getSelectedItem().toString(),
                    Double.parseDouble(edit_amount.getText().toString())
            );
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateCategories(@Nullable List<Category> categories) {
        categorySpinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, categories);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categorySpinnerAdapter);
    }
    private ArrayList getCategories() {

        ArrayList categories = new ArrayList();

        categories.add("Rent");
        categories.add("Gas");
        categories.add("Groceries");
        categories.add("Household");
        categories.add("Entertainment");
        categories.add("Savings");
        categories.add("401K");

        return categories;
    }

}
