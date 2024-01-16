package com.example.pawtrolapp.android

import android.content.Context
import com.example.pawtrolapp.auth.data.TokenStorage

class SharedPreferencesTokenStorage(context: Context) : TokenStorage {
    private val sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }
}
