package ru.netology.util

import android.content.Context
import java.util.regex.Pattern

fun isValidLogin(login: String) =
    Pattern.compile("^(?=.{5,10}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$").matcher(login).matches()

fun isValidPassword(password: String) =
    Pattern.compile("(?=.*[A-Z])(?!.*[^a-zA-Z0-9])(.{6,})\$").matcher(password).matches()

fun Context.getAuthToken() =
    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
        AUTHENTICATED_SHARED_KEY, ""
    )!!

fun Context.isAuthenticated() =
    getAuthToken().isNotEmpty()

fun Context.setAuthToken(token: String) =
    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
        .edit()
        .putString(AUTHENTICATED_SHARED_KEY, token)
        .commit()