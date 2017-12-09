/* Author: Sean Hansen
* ID: 108841276
* Date Started: 11/22/17
* Date Complete: 11/22/17
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager.db.converter;

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
