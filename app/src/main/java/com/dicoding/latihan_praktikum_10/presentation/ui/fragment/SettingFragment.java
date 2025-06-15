package com.dicoding.latihan_praktikum_10.presentation.ui.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.dicoding.latihan_praktikum_10.R;
import com.dicoding.latihan_praktikum_10.presentation.auth.LogoutHelper;

public class SettingFragment extends Fragment {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener sensorEventListener;

    private static final String CHANNEL_ID = "light_sensor_channel";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Logout button (yang sudah ada)
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> LogoutHelper.logout(getActivity()));

        // Tambahkan tombol sensor
        Button btnStartSensor = view.findViewById(R.id.btn_start_sensor);
        Button btnStopSensor = view.findViewById(R.id.btn_stop_sensor);

        createNotificationChannel();

        // Inisialisasi sensor
        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        if (lightSensor == null) {
            Toast.makeText(requireContext(), "Sensor cahaya tidak tersedia di perangkat ini", Toast.LENGTH_SHORT).show();
        }

        btnStartSensor.setOnClickListener(v -> {
            if (lightSensor != null) {
                startLightSensor();
                Toast.makeText(requireContext(), "Sensor Cahaya Diaktifkan", Toast.LENGTH_SHORT).show();
            }
        });

        btnStopSensor.setOnClickListener(v -> {
            stopLightSensor();
            Toast.makeText(requireContext(), "Sensor Cahaya Dinonaktifkan", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void startLightSensor() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float lux = event.values[0];
                if (lux < 10) {
                    showNotification("Cahaya Ruangan", "Cahaya ruangan terlalu redup: " + lux + " lux");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) { }
        };
        sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stopLightSensor() {
        if (sensorEventListener != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Light Sensor Channel";
            String description = "Channel untuk notifikasi sensor cahaya";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24) // pastikan drawable icon ini ada
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
