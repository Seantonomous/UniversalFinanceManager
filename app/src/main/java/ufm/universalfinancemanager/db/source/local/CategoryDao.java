package ufm.universalfinancemanager.db.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ufm.universalfinancemanager.db.entity.Category;

/**
 * Created by smh7 on 4/4/18.
 */

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Query("SELECT * FROM Category WHERE category_name = :name LIMIT 1")
    Category getCategoryByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);

    @Query("DELETE FROM Category")
    void deleteAll();

    @Query("DELETE FROM Category WHERE category_name = :name")
    void deleteByName(String name);
}
