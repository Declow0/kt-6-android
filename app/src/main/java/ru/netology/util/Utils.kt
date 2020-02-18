package ru.netology.util

import android.content.Context
import androidx.core.content.edit
import java.util.regex.Pattern

private val loginPattern by lazy(LazyThreadSafetyMode.NONE) {
    Pattern.compile("^(?=.{5,10}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$")
}

fun isValidLogin(login: String) =
    loginPattern.matcher(login).matches()

private val passwordPattern by lazy(LazyThreadSafetyMode.NONE) {
    Pattern.compile("(?=.*[A-Z])(?!.*[^a-zA-Z0-9])(.{6,})\$")
}

fun isValidPassword(password: String) =
    passwordPattern.matcher(password).matches()

fun Context.getAuthToken() =
    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
        AUTHENTICATED_SHARED_KEY, ""
    ).orEmpty()

fun Context.isAuthenticated() =
    getAuthToken().isNotEmpty()

fun Context.setAuthToken(token: String) =
    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
        .edit {
            putString(AUTHENTICATED_SHARED_KEY, token)
        }