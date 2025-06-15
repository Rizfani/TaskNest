package com.dicoding.latihan_praktikum_10.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dicoding.latihan_praktikum_10.data.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> students = new ArrayList<>();
    private List<Student> filteredStudents = new ArrayList<>();

    public StudentAdapter() {
        // Constructor kosong
    }

    public void setStudentList(List<Student> students) {
        this.students = students;
        this.filteredStudents = new ArrayList<>(students);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        filteredStudents.clear();
        if (text.isEmpty()) {
            filteredStudents.addAll(students);
        } else {
            text = text.toLowerCase();
            for (Student student : students) {
                if (student.getName().toLowerCase().contains(text)) {
                    filteredStudents.add(student);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = filteredStudents.get(position);
        String displayText = "Name: " + student.getName() + "\n"
                + "Email: " + student.getEmail() + "\n"
                + "Phone: " + student.getPhone() + "\n"
                + "Gender: " + student.getGender();
        holder.textView.setText(displayText);
    }

    @Override
    public int getItemCount() {

        return filteredStudents.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
