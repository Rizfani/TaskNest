package com.dicoding.latihan_praktikum_10.presentation.ui.fragment;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.dicoding.latihan_praktikum_10.R;
import com.dicoding.latihan_praktikum_10.data.model.Note;
import com.dicoding.latihan_praktikum_10.presentation.viewModel.NoteViewModel;

public class AddNoteFragment extends DialogFragment {

    private NoteViewModel noteViewModel;

    public AddNoteFragment(NoteViewModel noteViewModel) {
        this.noteViewModel = noteViewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_note, null);

        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etContent = view.findViewById(R.id.etContent);
        Button btnSave = view.findViewById(R.id.btnSaveNote);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                etTitle.setError("Judul harus diisi");
                return;
            }

            long timestamp = System.currentTimeMillis();

            Note note = new Note(title, content, timestamp, null);
            noteViewModel.addNote(note);

            showNotification(title);

            Toast.makeText(getActivity(), "Catatan berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        return new AlertDialog.Builder(requireActivity())
                .setView(view)
                .setTitle("Tambah Catatan")
                .create();
    }

    private void showNotification(String title) {
        String CHANNEL_ID = "note_channel";

        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Note Channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notifikasi catatan baru");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Catatan Baru")
                .setContentText("Berhasil menambahkan: " + title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
