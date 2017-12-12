/* Author: Sean Hansen
* ID: 108841276
* Date Started: 11/22/17
* Date Complete: 11/28/17
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members: Daniel Karapetian
*/
package ufm.universalfinancemanager.db.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ufm.universalfinancemanager.db.entity.Transaction;

/**
 * Created by smh7 on 11/22/17.
 */

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `Transaction`")
    List<Transaction> getAll();

    @Query("SELECT * FROM `Transaction` WHERE name LIKE :name")
    List<Transaction> getTransactionsByName(String name);

    @Query("SELECT * FROM `Transaction` WHERE name LIKE :name LIMIT 1")
    Transaction getTransactionByName(String name);

    @Query("SELECT * FROM `Transaction` WHERE id = :id")
    Transaction getTransactionById(String id);

    @Query("SELECT * FROM `Transaction` WHERE date BETWEEN :date1 AND :date2")
    List<Transaction> getTransactionsInDateRange(long date1, long date2);

    @Query("SELECT * FROM `Transaction` WHERE date = :date")
    List<Transaction> getTransactionsOnDate(long date);

    @Query("SELECT * FROM `Transaction` WHERE category_name = :categoryName")
    List<Transaction> getTransactionsByCategory(String categoryName);

    @Query("SELECT * FROM `Transaction` WHERE category_name = :categoryName AND date BETWEEN " +
            ":date1 AND :date2")
    List<Transaction> getTransactionsByCategoryDateRange(String categoryName, long date1, long date2);

    @Insert
    void insert(Transaction transaction);

    @Insert
    void InsertAll(Transaction... transactions);

    @Update
    void updateTransaction(Transaction... transactions);

    @Delete
    void delete(Transaction transaction);

    @Query("DELETE FROM 'Transaction'")
    void deleteAllTransactions();

    @Query("DELETE FROM 'Transaction' WHERE id = :id")
    void deleteById(String id);
}
