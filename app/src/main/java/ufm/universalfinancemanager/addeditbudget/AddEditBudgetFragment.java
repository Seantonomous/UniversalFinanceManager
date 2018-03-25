package ufm.universalfinancemanager.addeditbudget;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryPresenter;
import ufm.universalfinancemanager.support.AccountType;
import ufm.universalfinancemanager.support.TextValidator;

/**
 * Created by Areeba on 2/23/2018.
 */

public class AddEditBudgetFragment extends DaggerFragment implements AddEditBudgetContract.View{
    Button cancel_button;
    Button submit_button;
    private Spinner category;
    private EditText edit_amount;
    private EditText edit_name;
    private Spinner duration;
    boolean valid_amount = false;
    boolean valid_name = false;

    @Inject
    AddEditBudgetPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_budget_fragment, container, false);
        edit_amount = root.findViewById(R.id.add_amount);
        cancel_button = root.findViewById(R.id.cancel);
        /*edit_name = root.findViewById(R.id.name);
        category = root.findViewById(R.id.category);
        duration = root.findViewById(R.id.duration);
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
                String stringSelectedType;
                AccountType selectedType;
                Calendar today = Calendar.getInstance();

                if(!valid_amount || !valid_name)
                    return;


                stringSelectedType = category.getSelectedItem().toString();
                switch(stringSelectedType) {
                    case ("Travel"):
                        selectedType = AccountType.CHECKING;
                        break;
                    case ("Entertainment"):
                        selectedType = AccountType.SAVINGS;
                        break;
                    case ("Food"):
                        selectedType = AccountType.CASH;
                        break;
                    default:
                        selectedType = AccountType.CHECKING;
                }

                //save the budget to the user's account and display it on the overview
            }
        });
        */
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
}
