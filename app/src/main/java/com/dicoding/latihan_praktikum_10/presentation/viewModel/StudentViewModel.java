package com.dicoding.latihan_praktikum_10.presentation.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.dicoding.latihan_praktikum_10.data.entity.Student;
import com.dicoding.latihan_praktikum_10.data.repository.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    private final StudentRepository repository;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentRepository(application);
    }

    public LiveData<List<Student>> getAllStudents() {
        return repository.getAllStudents();
    }

    public LiveData<List<Student>> searchStudents(String keyword) {
        return repository.searchStudents(keyword);
    }
}
