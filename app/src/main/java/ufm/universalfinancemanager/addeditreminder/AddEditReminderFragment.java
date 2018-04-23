package ufm.universalfinancemanager.addeditreminder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.Flow;
import ufm.universalfinancemanager.support.TextValidator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by Areeba on 2/17/2018.
 */

public class AddEditReminderFragment extends DaggerFragment implements AddEditReminderContract.View {
    @Inject
    AddEditReminderPresenter mPresenter;

    private EditText edit_name;
    private EditText edit_date;
    private TextView date_textView;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    private EditText edit_notes;
    private RadioGroup flow_radioGroup;
    private Spinner duration_spinner;
    private boolean isEditing = false;
    Button submit_button;
    Button cancel_button;
    boolean valid_name = false;
    boolean valid_amount = false;
    private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
    }
    @Inject
    public AddEditReminderFragment() {}

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_reminder_fragment, container, false);
        edit_date = root.findViewById(R.id.date);
        edit_name = root.findViewById(R.id.label);
        date_textView = root.findViewById(R.id.dateTextView);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        cancel_button = root.findViewById(R.id.cancel);

        showDate(year,month+1,day);

        edit_name.addTextChangedListener(new TextValidator(edit_name) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() == 0) {
                    textView.setError("Reminder must have a Label");
                    valid_name = false;
                }else {
                    valid_name = true;
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


    private void showDate(int year, int month, int day) {
        edit_date.setText(new StringBuilder().append(day).append(" / ")
                .append(month).append(" / ").append(year));
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
    public void updateDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edit_date.setText(sdf.format(calendar.getTime()));
    }
    public boolean isActive() {
        return isAdded();
    }
    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
