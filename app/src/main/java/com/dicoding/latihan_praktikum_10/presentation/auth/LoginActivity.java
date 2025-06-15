package com.dicoding.latihan_praktikum_10.presentation.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.latihan_praktikum_10.R;
import com.dicoding.latihan_praktikum_10.presentation.ui.MainActivity;
import com.dicoding.latihan_praktikum_10.presentation.utils.PermissionHelper;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // Jika sudah login, langsung ke MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }

        if (!PermissionHelper.allPermissionsGranted(this)) {
            PermissionHelper.requestPermissions(this);
        }
    }

}
