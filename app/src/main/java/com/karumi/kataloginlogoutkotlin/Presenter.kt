package com.karumi.kataloginlogoutkotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class Presenter(
    private val logInLogOutKata: LogInLogOutKata,
    private val view: View,
    private val context: CoroutineContext = Dispatchers.Default
) : CoroutineScope by MainScope() {

    fun onLogInButtonTap(username: String, password: String) = launch {
        val lotInResult = withContext(context) { logInLogOutKata.logIn(username, password) }
        lotInResult.fold(
            {
                when (it) {
                    is InvalidCredentials -> view.showError(R.string.log_in_error_message)
                    is InvalidUsername -> view.showError(R.string.invalid_username_error_message)
                }
            },
            {
                view.hideLogInForm()
                view.showLogOutForm()
            }
        )
    }

    fun onLogOutButtonTap() = launch {
        val logOutResult = withContext(context) { logInLogOutKata.logOut() }
        if (logOutResult) {
            view.hideLogOutForm()
            view.showLogInForm()
        } else {
            view.showError(R.string.log_out_error_message)
        }
    }

    fun onDestroy() {
        cancel()
    }

    interface View {
        fun showLogInForm()
        fun hideLogInForm()
        fun showLogOutForm()
        fun hideLogOutForm()
        fun showError(resource: Int)
    }
}
