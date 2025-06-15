package com.dicoding.latihan_praktikum_10.presentation.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dicoding.latihan_praktikum_10.R;
import com.dicoding.latihan_praktikum_10.databinding.FragmentRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(username, email, password);
            }
        });

        binding.tvLogin.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return binding.getRoot();
    }

    private void registerUser(String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = auth.getCurrentUser().getUid();
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("username", username);
                        userMap.put("email", email);

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(uid)
                                .setValue(userMap)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Toast.makeText(getContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fragment_container, new LoginFragment())
                                                .addToBackStack(null)
                                                .commit();
                                    } else {
                                        Toast.makeText(getContext(), "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getContext(), "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
