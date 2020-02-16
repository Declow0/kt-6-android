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
import ru.netology.repository.AuthRepository
import ru.netology.util.isValidLogin
import ru.netology.util.isValidPassword
import ru.netology.util.setAuthToken

class RegistrationActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btn_register.setOnClickListener {
            val login = edt_registration_login.text.toString()
            val password = edt_registration_password.text.toString()
            val repeatedPassword = edt_registration_repeat_password.text.toString()

            if (!isValidLogin(login)) {
                edt_registration_login.error = resources.getString(R.string.invalid_login)
            } else if (password != repeatedPassword) {
                edt_registration_password.error =
                    resources.getString(R.string.diff_passwords)
                edt_registration_repeat_password.error =
                    resources.getString(R.string.diff_passwords)
            } else if (!isValidPassword(password)) {
                edt_registration_password.error =
                    resources.getString(R.string.invalid_password)
                edt_registration_repeat_password.error =
                    resources.getString(R.string.invalid_password)
            } else {
                launch {
                    dialog =
                        indeterminateProgressDialog(
                            message = R.string.please_wait,
                            title = R.string.authentication
                        ) {
                            setCancelable(false)
                        }
                    val response =
                        AuthRepository.register(
                            login,
                            password
                        )
                    dialog?.dismiss()
                    if (response.isSuccessful) {
                        toast(R.string.success)
                        setAuthToken(response.body()!!.token)
                        finish()
                    } else {
                        toast(R.string.registration_failed)
                        toast(AuthRepository.parseError(response).error)
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
