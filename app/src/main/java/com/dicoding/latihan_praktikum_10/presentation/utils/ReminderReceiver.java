package com.dicoding.latihan_praktikum_10.presentation.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Ini pengingat harian kamu!", Toast.LENGTH_LONG).show();
    }
}
