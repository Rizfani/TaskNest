package com.dicoding.latihan_praktikum_10.data.repository;

import com.dicoding.latihan_praktikum_10.data.model.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private final CollectionReference notesRef;

    public NoteRepository() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        notesRef = FirebaseFirestore.getInstance().collection("notes").document(userId).collection("userNotes");
    }

    public interface OnNotesFetchedListener {
        void onSuccess(List<Note> notes);
        void onFailure(Exception e);
    }

    public void getNotes(OnNotesFetchedListener listener) {
        notesRef.orderBy("timestamp", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Note> notes = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Note note = doc.toObject(Note.class);
                        note.setId(doc.getId());
                        notes.add(note);
                    }
                    listener.onSuccess(notes);
                })
                .addOnFailureListener(listener::onFailure);
    }

    public interface OnNoteAddedListener {
        void onSuccess();
        void onFailure(Exception e);
    }

    public void addNote(Note note, OnNoteAddedListener listener) {
        String id = notesRef.document().getId();
        note.setId(id);
        note.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        notesRef.document(id).set(note)
                .addOnSuccessListener(unused -> listener.onSuccess())
                .addOnFailureListener(listener::onFailure);
    }

    public void updateNote(Note note, OnNoteAddedListener listener) {
        notesRef.document(note.getId()).set(note)
                .addOnSuccessListener(unused -> listener.onSuccess())
                .addOnFailureListener(listener::onFailure);
    }

    public void deleteNote(String noteId) {
        notesRef.document(noteId).delete();
    }
}
