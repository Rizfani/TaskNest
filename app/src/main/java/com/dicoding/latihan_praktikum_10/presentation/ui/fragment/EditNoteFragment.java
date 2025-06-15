package com.dicoding.latihan_praktikum_10.presentation.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.dicoding.latihan_praktikum_10.R;
import com.dicoding.latihan_praktikum_10.data.model.Note;
import com.dicoding.latihan_praktikum_10.presentation.viewModel.NoteViewModel;

public class EditNoteFragment extends DialogFragment {

    private final Note note;
    private final NoteViewModel noteViewModel;

    public EditNoteFragment(Note note, NoteViewModel noteViewModel) {
        this.note = note;
        this.noteViewModel = noteViewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_note, null);

        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etContent = view.findViewById(R.id.etContent);
        Button btnSave = view.findViewById(R.id.btnSaveNote);

        // Set data awal ke EditText
        etTitle.setText(note.getTitle());
        etContent.setText(note.getContent());

        btnSave.setOnClickListener(v -> {
            String updatedTitle = etTitle.getText().toString().trim();
            String updatedContent = etContent.getText().toString().trim();

            if (updatedTitle.isEmpty()) {
                etTitle.setError("Judul tidak boleh kosong");
                return;
            }

            // Update data
            note.setTitle(updatedTitle);
            note.setContent(updatedContent);
            noteViewModel.updateNote(note);

            Toast.makeText(getActivity(), "Catatan berhasil diupdate", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        return new AlertDialog.Builder(requireActivity())
                .setView(view)
                .setTitle("Edit Catatan")
                .create();
    }
}
