package ufm.universalfinancemanager;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by smh7 on 11/22/17.
 */

public class FlowConverter {

    @TypeConverter
    public static ufm.universalfinancemanager.Flow toFlow(int flow) {
        if(flow == Flow.OUTCOME.ordinal())
            return Flow.OUTCOME;
        else if(flow == Flow.INCOME.ordinal())
            return Flow.INCOME;
        else
            return Flow.TRANSFER;
    }

    @TypeConverter
    public static int toInteger(ufm.universalfinancemanager.Flow flow) {
        return flow.ordinal();
    }
}
