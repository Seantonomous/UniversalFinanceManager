package ufm.universalfinancemanager.addeditcategory;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.TextValidator;

public class AddEditCategoryFragment extends DaggerFragment implements AddEditCategoryContract.View {

    @Inject
    AddEditCategoryPresenter mPresenter;

    private boolean valid_name = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addedit_account_fragment, container, false);

        final EditText edit_name = root.findViewById(R.id.category_name);
        final RadioButton income_radioButton = root.findViewById(R.id.category_flow_income);
        final RadioButton expense_radioButton = root.findViewById(R.id.category_flow_expense);
        Button submit_button = root.findViewById(R.id.submit);
        Button cancel_button = root.findViewById(R.id.cancel);

        income_radioButton.setChecked(true);

        edit_name.addTextChangedListener(new TextValidator(edit_name) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() == 0) {
                    textView.setError("Category must have a name");
                    valid_name = false;
                }else {
                    valid_name = true;
                }
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid_name) {
                    if(income_radioButton.isChecked())
                        mPresenter.saveCategory(edit_name.getText(),Flow.INCOME);
                    else(expense_radioButton.isChecked())
                        mPresenter.saveCategory(edit_name.getText(),Flow.OUTCOME);
                }
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
