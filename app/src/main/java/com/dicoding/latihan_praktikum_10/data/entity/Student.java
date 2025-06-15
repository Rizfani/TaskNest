package com.dicoding.latihan_praktikum_10.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String email;
    private String gender;
    private String phone;

    // Constructor
    public Student(String name, String email, String gender, String phone) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
}