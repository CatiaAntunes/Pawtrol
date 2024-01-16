package com.example.pawtrolapp.auth.data
interface TokenStorage {
    fun saveToken(token: String)
    fun getToken(): String?
}
