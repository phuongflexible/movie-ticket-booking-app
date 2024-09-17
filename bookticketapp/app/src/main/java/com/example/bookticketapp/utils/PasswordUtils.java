package com.example.bookticketapp.utils;

import android.util.Base64;

public class PasswordUtils {
    // Mã hóa mật khẩu
    public static String encodePassword(String password) {
        return Base64.encodeToString(password.getBytes(), Base64.NO_WRAP);
    }

    // Giải mã mật khẩu
    public static String decodePassword(String encodedPassword) {
        byte[] decodedBytes = Base64.decode(encodedPassword, Base64.NO_WRAP);
        return new String(decodedBytes);
    }
}
