package com.dicoding.latihan_praktikum_10.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dicoding.latihan_praktikum_10.data.entity.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Student student);

    @Query("SELECT * FROM students")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT * FROM students WHERE name LIKE '%' || :keyword || '%'")
    LiveData<List<Student>> searchStudents(String keyword);
}
