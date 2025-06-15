package com.dicoding.latihan_praktikum_10.presentation.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dicoding.latihan_praktikum_10.R;
import com.dicoding.latihan_praktikum_10.databinding.FragmentLoginBinding;
import com.dicoding.latihan_praktikum_10.presentation.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private FirebaseAuth auth;
    private GoogleSignInHelper.OnGoogleSignInListener googleListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();

        googleListener = firebaseUser -> {
            if (firebaseUser != null) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "Google sign-in gagal", Toast.LENGTH_SHORT).show();
            }
        };

        var launcher = GoogleSignInHelper.getGoogleSignInLauncher(this, googleListener);

        binding.btnLogin.setOnClickListener(v -> {
            String input = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();
            if (TextUtils.isEmpty(input) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Isi semua field", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(input, password);
            }
        });

        binding.btnLoginGoogle.setOnClickListener(v -> GoogleSignInHelper.startGoogleSignIn(requireActivity(), launcher));

        binding.tvRegister.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });


        return binding.getRoot();
    }

    private void loginUser(String input, String password) {
        if (input.contains("@")) {
            auth.signInWithEmailAndPassword(input, password)
                    .addOnCompleteListener(task -> handleLoginResult(task.isSuccessful()));
        } else {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
            userRef.orderByChild("username").equalTo(input)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    String email = child.child("email").getValue(String.class);
                                    auth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(task -> handleLoginResult(task.isSuccessful()));
                                    break;
                                }
                            } else {
                                Toast.makeText(requireContext(), "Username tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(requireContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void handleLoginResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), "Login gagal", Toast.LENGTH_SHORT).show();
        }
    }
}