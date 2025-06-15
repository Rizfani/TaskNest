package com.dicoding.latihan_praktikum_10.data.network;



import com.dicoding.latihan_praktikum_10.data.entity.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("students")
    Call<List<Student>> getStudents();
}
