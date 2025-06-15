package com.dicoding.latihan_praktikum_10.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.dicoding.latihan_praktikum_10.data.database.StudentDao;
import com.dicoding.latihan_praktikum_10.data.database.StudentDatabase;
import com.dicoding.latihan_praktikum_10.data.entity.Student;
import com.dicoding.latihan_praktikum_10.data.network.ApiService;
import com.dicoding.latihan_praktikum_10.data.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRepository {

    private StudentDao studentDao;
    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application) {
        StudentDatabase db = StudentDatabase.getDatabase(application);
        studentDao = db.studentDao();
        allStudents = studentDao.getAllStudents();

        // Fetch dari API sekali saat repository dibuat
        fetchStudentsFromApi();
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public LiveData<List<Student>> searchStudents(String keyword) {
        return studentDao.searchStudents(keyword);
    }

    private void fetchStudentsFromApi() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<Student>> call = apiService.getStudents();

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Student> students = response.body();
                    new Thread(() -> {
                        for (Student student : students) {
                            studentDao.insert(student); // Simpan ke Room
                        }
                    }).start();
                } else {
                    Log.e("API_ERROR", "Response failed or empty");
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch students: " + t.getMessage());
            }
        });
    }
}
