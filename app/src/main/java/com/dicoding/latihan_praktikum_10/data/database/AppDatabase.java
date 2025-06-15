package com.dicoding.latihan_praktikum_10.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dicoding.latihan_praktikum_10.data.entity.NoteEntity;
import com.dicoding.latihan_praktikum_10.data.entity.Student;


@Database(entities = {NoteEntity.class, Student.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract StudentDao studentDao();
}
