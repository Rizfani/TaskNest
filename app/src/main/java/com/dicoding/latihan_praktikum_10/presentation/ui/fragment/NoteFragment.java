package com.dicoding.latihan_praktikum_10.presentation.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dicoding.latihan_praktikum_10.R;
import com.dicoding.latihan_praktikum_10.data.model.Note;
import com.dicoding.latihan_praktikum_10.presentation.adapter.NoteAdapter;
import com.dicoding.latihan_praktikum_10.presentation.viewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteFragment extends Fragment {
    private NoteViewModel noteViewModel;
    private NoteAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNotes);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddNote);
        progressBar = view.findViewById(R.id.progressBarNotes);

        adapter = new NoteAdapter(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                EditNoteFragment dialog = new EditNoteFragment(note, noteViewModel);
                dialog.show(getChildFragmentManager(), "EditNote");
            }

            @Override
            public void onNoteLongClick(Note note) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Hapus Catatan")
                        .setMessage("Yakin ingin menghapus?")
                        .setPositiveButton("Hapus", (dialog, which) -> noteViewModel.deleteNote(note.getId()))
                        .setNegativeButton("Batal", null)
                        .show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        observeViewModel();

        fabAdd.setOnClickListener(v -> {
            AddNoteFragment dialog = new AddNoteFragment(noteViewModel);
            dialog.show(getChildFragmentManager(), "AddNote");
        });

        return view;
    }

    private void observeViewModel() {
        noteViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> adapter.setNotes(notes));
        noteViewModel.isLoading().observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));
    }
}
