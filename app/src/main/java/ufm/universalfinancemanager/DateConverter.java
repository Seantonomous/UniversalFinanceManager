package ufm.universalfinancemanager;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by smh7 on 11/22/17.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(long date) {
        return new Date(date);
    }

    @TypeConverter
    public static long toLong(Date date) {
        return date.getTime();
    }
}
