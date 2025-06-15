package com.dicoding.latihan_praktikum_10.presentation.ui;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.dicoding.latihan_praktikum_10.R;
import com.dicoding.latihan_praktikum_10.presentation.auth.LoginActivity;
import com.dicoding.latihan_praktikum_10.presentation.ui.fragment.HomeFragment;
import com.dicoding.latihan_praktikum_10.presentation.ui.fragment.KontenFragment;
import com.dicoding.latihan_praktikum_10.presentation.ui.fragment.MyFragment;
import com.dicoding.latihan_praktikum_10.presentation.ui.fragment.NoteFragment;
import com.dicoding.latihan_praktikum_10.presentation.ui.fragment.SettingFragment;
import com.dicoding.latihan_praktikum_10.presentation.utils.PermissionHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private FirebaseAuth mAuth;
    private BottomNavigationView bottomNavigationView;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private static final float LIGHT_THRESHOLD = 5.0f; // ambang cahaya

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        if (!PermissionHelper.allPermissionsGranted(this)) {
            PermissionHelper.requestPermissions(this);
        }

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.navigation_konten) {
                selectedFragment = new KontenFragment();
            } else if (id == R.id.navigation_my) {
                selectedFragment = new MyFragment();
            } else if (id == R.id.navigation_setting) {
                selectedFragment = new SettingFragment();
            } else if (id == R.id.navigation_note) {
                selectedFragment = new NoteFragment();
            }
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        // Inisialisasi Sensor Cahaya
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (lightSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        Log.d("LightSensor", "Lux: " + lux);

        if (lux < LIGHT_THRESHOLD) {
            Log.d("LightSensor", "Cahaya redup, tampilkan notifikasi");
            showNotification();
        }
    }

    private void showNotification() {
        String CHANNEL_ID = "light_sensor_channel";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Sensor Cahaya", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Sensor Cahaya")
                .setContentText("Cahaya di sekitarmu sangat redup")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}