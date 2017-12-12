/* Author: Sean Hansen
* ID: 108841276
* Date Started: 11/22/17
* Date Complete: 12/3/17
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members: Daniel Karapetian
*/
package ufm.universalfinancemanager.db.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ufm.universalfinancemanager.db.entity.Transaction;
import ufm.universalfinancemanager.db.source.local.TransactionDao;

/**
 * Created by smh7 on 11/22/17.
 */

@Database(entities = {Transaction.class}, version = 1)
public abstract class TransactionDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
}
