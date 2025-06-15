package com.dicoding.latihan_praktikum_10.presentation.utils;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.endsWith(".com");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
