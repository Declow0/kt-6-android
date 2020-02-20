package ru.netology.activity

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import ru.netology.api.retrofit.RetrofitClient
import ru.netology.repository.AuthRepository
import ru.netology.util.isValidLogin
import ru.netology.util.isValidPassword
import ru.netology.util.setAuthToken
import java.io.IOException

class RegistrationActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val authRepository = AuthRepository()

        btn_register.setOnClickListener {
            val login = edt_registration_login.text.toString()
            val password = edt_registration_password.text.toString()
            val repeatedPassword = edt_registration_repeat_password.text.toString()

            til_registration_login.error = null
            til_registration_password.error = null
            til_registration_repeat_password.error = null

            if (!isValidLogin(login)) {
                til_registration_login.error = resources.getString(R.string.invalid_login)
            } else if (password.isEmpty()) {
                til_registration_password.error =
                    resources.getString(R.string.empty_password)
            } else if (!isValidPassword(password)) {
                til_registration_password.error =
                    resources.getString(R.string.invalid_password)
            } else if (password != repeatedPassword) {
                til_registration_repeat_password.error =
                    resources.getString(R.string.diff_passwords)
            } else {
                launch {
                    dialog =
                        indeterminateProgressDialog(
                            message = R.string.please_wait,
                            title = R.string.registration
                        ) {
                            setCancelable(false)
                        }
                    try {
                        val response =
                            authRepository.register(
                                login,
                                password
                            )
                        if (response.isSuccessful) {
                            toast(R.string.success)
                            setAuthToken(response.body()!!.token)
                            finish()
                        } else {
                            toast(R.string.registration_failed)
                            toast(RetrofitClient.parseError(response).error)
                        }
                    } catch (e: IOException) {
                        toast(R.string.network_error)
                    } finally {
                        dialog?.dismiss()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        dialog?.dismiss()
        cancel()
    }
}
