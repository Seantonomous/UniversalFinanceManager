package ufm.universalfinancemanager.db.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ufm.universalfinancemanager.db.entity.Account;

/**
 * Created by smh7 on 4/2/18.
 */

@Dao
public interface AccountDao {
    @Query("SELECT * FROM Account")
    List<Account> getAll();

    @Query("SELECT * FROM Account WHERE account_name = :name LIMIT 1")
    Account getAccountByName(String name);

    @Query("UPDATE Account SET account_balance = :newBalance WHERE account_name = :name")
    void updateBalance(String name, double newBalance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Insert
    void insertAll(Account... accounts);

    @Delete
    void delete(Account account);

    @Query("DELETE FROM Account")
    void deleteAll();

    @Query("DELETE FROM Account WHERE account_name = :name")
    void deleteByName(String name);
}
