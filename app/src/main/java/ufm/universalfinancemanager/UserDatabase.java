package ufm.universalfinancemanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by smh7 on 11/22/17.
 */

@Database(entities = {Transaction.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
}
