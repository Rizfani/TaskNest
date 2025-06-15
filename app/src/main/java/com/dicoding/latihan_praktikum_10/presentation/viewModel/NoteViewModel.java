package com.dicoding.latihan_praktikum_10.presentation.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.latihan_praktikum_10.data.model.Note;
import com.dicoding.latihan_praktikum_10.data.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends ViewModel {
    private final NoteRepository repository;
    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public NoteViewModel() {
        repository = new NoteRepository();
        loadNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notesLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public void loadNotes() {
        loading.setValue(true);
        repository.getNotes(new NoteRepository.OnNotesFetchedListener() {
            @Override
            public void onSuccess(List<Note> notes) {
                notesLiveData.setValue(notes);
                loading.setValue(false);
            }

            @Override
            public void onFailure(Exception e) {
                loading.setValue(false);
            }
        });
    }

    public void addNote(Note note) {
        repository.addNote(note, new NoteRepository.OnNoteAddedListener() {
            @Override
            public void onSuccess() { loadNotes(); }
            @Override
            public void onFailure(Exception e) {}
        });
    }

    public void updateNote(Note note) {
        repository.updateNote(note, new NoteRepository.OnNoteAddedListener() {
            @Override
            public void onSuccess() { loadNotes(); }
            @Override
            public void onFailure(Exception e) {}
        });
    }

    public void deleteNote(String noteId) {
        repository.deleteNote(noteId);
        loadNotes();
    }
}
