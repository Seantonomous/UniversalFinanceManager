package ufm.universalfinancemanager.addeditbudget;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.addeditcategory.AddEditCategoryPresenter;

/**
 * Created by Areeba on 2/23/2018.
 */

public class AddEditBudgetFragment extends DaggerFragment implements AddEditBudgetContract.View{
    Button cancel_button;
    private Spinner category;
    private EditText edit_amount;
    private Spinner duration;

    @Inject
    AddEditBudgetPresenter mPresenter;

    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_budget_fragment, container, false);
        edit_amount = root.findViewById(R.id.add_amount);
        cancel_button = root.findViewById(R.id.cancel);

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
