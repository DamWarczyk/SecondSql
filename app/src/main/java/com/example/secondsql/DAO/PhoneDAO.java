package com.example.secondsql.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.secondsql.entities.Phone;

import java.util.List;

@Dao
public interface PhoneDAO {
    @Query("SELECT * FROM PhonesDB")
    LiveData<List<Phone>> getAll();

    @Query("SELECT * FROM PhonesDB WHERE id IN (:phoneIds)")
    List<Phone> loadAllByIds(int[] phoneIds);

    @Query("SELECT * FROM PhonesDB WHERE Producer LIKE :first AND " +
            "Model LIKE :last LIMIT 1")
    Phone findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Phone phone);

    @Insert
    void insertAll(Phone... phones);

    @Delete
    void delete(Phone phone);

    @Update
    void update(Phone phone);

}
