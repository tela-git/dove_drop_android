package com.example.dovedrop.chat.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedPrefs(context: Context) {
    private val preferences = EncryptedSharedPreferences
        .create(
            context,
            "secured_prefs",
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    fun saveToken(token: String) {
        preferences.edit()
            .putString("auth_token", token)
            .apply()
    }
    fun getToken(): String? {
        return preferences.getString("auth_token", null)
    }
    fun clearToken() {
        preferences.edit()
            .remove("auth_token")
            .apply()
    }
    fun updateLoginStatus(isLoggedIn: Boolean) {
        preferences.edit()
            .putBoolean("isLoggedIn", isLoggedIn)
            .apply()
    }
    fun getLoginStatus() : Boolean {
        return preferences.getBoolean("isLoggedIn", false)
    }
}