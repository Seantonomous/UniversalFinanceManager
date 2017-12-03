package ufm.universalfinancemanager;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by smh7 on 11/22/17.
 */

public class AccountTypeConverter {
    @TypeConverter
    public static AccountType toAccountType(int type) {
        if(type == AccountType.CASH.ordinal())
            return AccountType.CASH;
        else if(type == AccountType.CHECKING.ordinal())
            return AccountType.CHECKING;
        else if(type == AccountType.CREDIT_CARD.ordinal())
            return AccountType.CREDIT_CARD;
        else
            throw new IllegalArgumentException("Could not read AccountType field");
    }

    @TypeConverter
    public static int toInteger(AccountType type) {
        return type.ordinal();
    }
}
