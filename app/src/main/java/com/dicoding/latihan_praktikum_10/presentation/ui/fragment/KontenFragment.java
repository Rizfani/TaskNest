package com.dicoding.latihan_praktikum_10.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.latihan_praktikum_10.R;
import com.dicoding.latihan_praktikum_10.presentation.adapter.StudentAdapter;
import com.dicoding.latihan_praktikum_10.presentation.viewModel.StudentViewModel;

public class KontenFragment extends Fragment {

    private StudentViewModel studentViewModel;
    private StudentAdapter adapter;

    public KontenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_konten, container, false);

        SearchView searchView = view.findViewById(R.id.search_view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StudentAdapter();
        recyclerView.setAdapter(adapter);

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentViewModel.getAllStudents().observe(getViewLifecycleOwner(), students -> {
            adapter.setStudentList(students);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchStudent(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchStudent(newText);
                return true;
            }

            private void searchStudent(String keyword) {
                studentViewModel.searchStudents(keyword).observe(getViewLifecycleOwner(), students -> {
                    adapter.setStudentList(students);
                });
            }
        });

        return view;
    }
}
