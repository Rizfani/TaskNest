package com.dicoding.latihan_praktikum_10.presentation.utils;

public class LoginValidator {

    public static boolean isValidLogin(String email, String password) {
        // Simulasi validasi akun
        // Nanti ini bisa disesuaikan dengan database atau Firebase

        String validEmail = "tesku@gmail.com";
        String validPassword = "123456";

        return email.equals(validEmail) && password.equals(validPassword);
    }
}