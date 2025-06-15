package com.dicoding.latihan_praktikum_10;

import com.dicoding.latihan_praktikum_10.presentation.utils.LoginValidator;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void testLoginSuccess() {
        assertTrue(LoginValidator.isValidLogin("tesku@gmail.com", "123456"));
    }

    @Test
    public void testLoginFailed_WrongPassword() {
        assertFalse(LoginValidator.isValidLogin("tesku@gmail.com", "wrongpassword"));
    }

    @Test
    public void testLoginFailed_WrongEmail() {
        assertFalse(LoginValidator.isValidLogin("wrongemail@gmail.com", "123456"));
    }

    @Test
    public void testLoginFailed_BothWrong() {
        assertFalse(LoginValidator.isValidLogin("wrong@gmail.com", "wrongpassword"));
    }
}
