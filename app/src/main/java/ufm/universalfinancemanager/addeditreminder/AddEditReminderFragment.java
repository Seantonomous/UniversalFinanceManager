package ufm.universalfinancemanager.addeditreminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.TextValidator;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.SYSTEM_HEALTH_SERVICE;

/**
 * Created by Areeba on 2/17/2018.
 */

public class AddEditReminderFragment extends DaggerFragment implements AddEditReminderContract.View {
    @Inject
    AddEditReminderPresenter mPresenter;

    private EditText edit_name;
    private TimePicker timepicker;
    private TextView date_textView;
    private EditText edit_date;
    private EditText edit_notes;
    Button submit_button;
    Button cancel_button;
    private Date datePicker;

    private Calendar calendar;
    private int hour, minute;
    private boolean isEditing = false;
    boolean valid_name = false;
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
        edit_name = root.findViewById(R.id.label);
        timepicker = root.findViewById(R.id.timePicker);
        date_textView = root.findViewById(R.id.dateTextView);
        edit_date = root.findViewById(R.id.date);
        edit_notes = root.findViewById(R.id.notes);
        submit_button = root.findViewById(R.id.done);
        cancel_button = root.findViewById(R.id.cancel);

        calendar = Calendar.getInstance();
        updateDate();

        Calendar currentCal = Calendar.getInstance();
        Date currentDate = currentCal.getTime();
        final int currentHour = currentDate.getHours();
        int currentMinute = currentDate.getMinutes();
        hour = currentHour;
        minute = currentMinute;

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

        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
            }
        });

        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                Date dateSelected = calendar.getTime();
                int a = dateSelected.getDay();

                //if(valid_name) {
                    //  if(a < Calendar.DATE) {
                    //    edit_date.setError("Date cannot be before current Date");
                    //  }
                    // else{
                    mPresenter.saveReminders(edit_name.getText().toString(), new Time(hour, minute, 0), calendar.getTime(), edit_notes.getText().toString());
                    //}
                //}

                int hourOfDay = timepicker.getHour();
                int minute = timepicker.getMinute();

                long currentTime = System.currentTimeMillis();
                long diffTime =0;

                Calendar cal = Calendar.getInstance();

                cal.setTimeInMillis(currentTime);
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, 0);

                diffTime = cal.getTimeInMillis() - currentTime;

                Intent i = new Intent(getActivity(), NotifyReminder.class);
                PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, i, 0);
                AlarmManager am = (AlarmManager)getContext().getSystemService(getContext().ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +diffTime, pi);

            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditing) {
                    mPresenter.deleteReminders();
                }
                else {
                    showLastActivity(false);
                }

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
    public void setUpFragmentContent(boolean editing) {
        this.isEditing = editing;
        if(isEditing) {
            cancel_button.setText("Delete");
        }
    }

    public void updateDate() {
        String myFormat = "MM / dd / yy";
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


    @Override
    public void populateExistingFields(String name, Time time, Date date, String notes) {
        isEditing = true;
        edit_name.setText(name);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        calendar.setTime(date);
        edit_notes.setText(notes);
        //Calendar call = Calendar.getInstance();
        /*int hourOfDay = time.getHours();
        int minute = time.getMinutes();
        call.set(Calendar.HOUR_OF_DAY, hourOfDay);
        call.set(Calendar.MINUTE, minute);
        call.set(Calendar.SECOND, 0);*/
        updateDate();

    }
}