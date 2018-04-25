package ufm.universalfinancemanager.support.atomic;

import android.annotation.SuppressLint;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;


import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.sql.Time;

import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.support.ListItem;
import ufm.universalfinancemanager.support.RowType;

/**
 * Created by simranjeetkaur on 19/04/18.
 */

public class Reminder implements Parcelable, Serializable, ListItem {

    private String name;
    private Time time;
    private Date date;
    private String notes;

    public Reminder(String nam, Time t, Date d, String n) {
        this.name = nam;
        this.time = t;
        this.date = d;
        this.notes = n;

    }

    /******************Getters**********************/
    public String getName() {return name;}
    public Time getTime() {return time;}
    public Date getDate() {return date;}
    public String getNotes() {return notes;}

    /******************Setters**********************/
    public void setName(String name) {this.name = name;}
    public void setTime(Time t) {this.time = t;}
    public void setDate(Date date) {this.date = date;}
    public void setNotes(String notes) {this.notes= notes;}

    protected Reminder(Parcel in) {
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if(convertView == null) {
            view = (View)inflater.inflate(R.layout.reminder_list,null);
        }
        else{
            view = convertView;
        }

        TextView reminderName = view.findViewById(R.id.reminder_name);
        TextView showDate = view.findViewById(R.id.reminder_date);
        TextView showTime = view.findViewById(R.id.reminder_time);
        TextView notes = view.findViewById(R.id.reminder_notes);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        reminderName.setText(this.name);
        showDate.setText(dateFormatter.format(this.date).toString());
        showTime.setText(this.time.toString());
        notes.setText(this.notes);

        return view;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
