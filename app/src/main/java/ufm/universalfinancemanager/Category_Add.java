/* Author: Areeba Waheed
* ID:
* Date Started: 11/2/17
* Date Complete: 11/24/17
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members: Sean Hansen, Muhammad Ansari
*/
package ufm.universalfinancemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Areeba on 11/2/2017.
 */

public class Category_Add extends Activity {
    private boolean valid_name = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category);
        final User sessionUser;

        final EditText edit_name = (EditText)findViewById(R.id.category_name);
        final RadioGroup flow_radioGroup = (RadioGroup) findViewById(R.id.category_flow);
        final RadioButton income_radioButton = (RadioButton) findViewById(R.id.category_flow_income);
        final RadioButton expense_radioButton = (RadioButton) findViewById(R.id.category_flow_expense);
        final RadioButton transfer_radioButton = (RadioButton) findViewById(R.id.category_flow_transfer);
        Button submit_button = (Button)findViewById(R.id.submit);

        income_radioButton.setChecked(true);

        Bundle args = getIntent().getExtras();
        if(args == null)
            sessionUser = null;
        else {
            sessionUser = args.getParcelable("ufm.universalfinancemanager.USER");
        }
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
                    if(sessionUser.hasCategory(edit_name.getText().toString())) {
                        //Can't add, user already has category
                    }
                    else {
                        if (income_radioButton.isChecked()) {

                            Category newCategory = new Category(edit_name.getText().toString(), Flow.INCOME);

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", (Parcelable)newCategory);
                            setResult(Activity.RESULT_OK, returnIntent);
                        } else if (expense_radioButton.isChecked()) {

                            Category newCategory = new Category(edit_name.getText().toString(), Flow.OUTCOME);

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", (Parcelable)newCategory);
                            setResult(Activity.RESULT_OK, returnIntent);
                        } else {

                            Category newCategory = new Category(edit_name.getText().toString(), Flow.TRANSFER);

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", (Parcelable)newCategory);
                            setResult(Activity.RESULT_OK, returnIntent);

                        }
                    }

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
}
