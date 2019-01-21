package com.karumi.kataloginlogoutkotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class Presenter(
    private val logInLogOutKata: LogInLogOutKata,
    private val view: View
) : CoroutineScope by MainScope() {

    fun onLogInButtonTap(username: String, password: String) = launch {
        val lotInResult = coroutineScope { logInLogOutKata.logIn(username, password) }
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
        val logOutResult = coroutineScope { logInLogOutKata.logOut() }
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
