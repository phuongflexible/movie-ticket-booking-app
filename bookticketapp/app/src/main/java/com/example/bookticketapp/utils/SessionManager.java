package com.example.bookticketapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("UserLogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu trạng thái đăng nhập
    public void saveLoginStatus(boolean isLoggedIn, int userId) {
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.putInt("userId", userId);
        editor.commit();
    }

    // Kiểm tra trạng thái đăng nhập
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    // Lấy ID người dùng đã đăng nhập
    public int getUserId() {
        return sharedPreferences.getInt("userId", -1);
    }

    // Đăng xuất
    public void logout() {
        editor.clear();
        editor.commit();
    }
}
