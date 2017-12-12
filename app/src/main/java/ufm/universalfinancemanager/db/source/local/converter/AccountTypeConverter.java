/* Author: Sean Hansen
* ID: 108841276
* Date Started: 11/22/17
* Date Complete: 11/23/17
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager.db.source.local.converter;

import android.arch.persistence.room.TypeConverter;

import ufm.universalfinancemanager.support.AccountType;

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
