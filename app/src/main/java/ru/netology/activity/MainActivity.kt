package ru.netology.activity

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.netology.api.retrofit.RetrofitClient
import ru.netology.repository.AuthRepository
import ru.netology.util.isAuthenticated
import ru.netology.util.isValidLogin
import ru.netology.util.isValidPassword
import ru.netology.util.setAuthToken
import java.io.IOException

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isAuthenticated()) {
            startActivity<FeedActivity>()
            finish()
        } else {
            val authRepository = AuthRepository()
            btn_login.setOnClickListener {
                til_login.error = null
                til_password.error = null

                if (!isValidLogin(edt_login.text.toString())) {
                    til_login.error = resources.getString(R.string.invalid_login)
                } else if (edt_password.text.toString().isEmpty()) {
                    til_password.error = resources.getString(R.string.empty_password)
                } else if (!isValidPassword(edt_password.text.toString())) {
                    til_password.error = resources.getString(R.string.invalid_password)
                } else {
                    launch {
                        dialog =
                            indeterminateProgressDialog(
                                message = R.string.please_wait,
                                title = R.string.authentication
                            ) {
                                setCancelable(false)
                            }
                        try {
                            val response =
                                authRepository.authenticate(
                                    edt_login.text.toString(),
                                    edt_password.text.toString()
                                )
                            if (response.isSuccessful) {
                                toast(R.string.success).show()
                                setAuthToken(response.body()!!.token)
                                startActivity<FeedActivity>()
                                finish()
                            } else {
                                toast(R.string.authentication_failed).show()
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

        btn_registration.setOnClickListener {
            startActivity<RegistrationActivity>()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isAuthenticated()) {
            startActivity<FeedActivity>()
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        cancel()
        dialog?.dismiss()
    }
}