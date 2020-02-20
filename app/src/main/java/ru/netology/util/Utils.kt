package ru.netology.util

import android.content.Context
import androidx.core.content.edit
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.netology.activity.MainActivity
import ru.netology.activity.R
import ru.netology.api.retrofit.RetrofitClient
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

fun Context.setAuthToken(authToken: String) {
    RetrofitClient.addAuthToken(authToken)
    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
        .edit {
            putString(AUTHENTICATED_SHARED_KEY, authToken)
        }
}

fun Context.unauthorized() {
    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
        .edit {
            remove(AUTHENTICATED_SHARED_KEY)
        }
    startActivity<MainActivity>()
    toast(R.string.authorization_failed)
}
