package com.karumi.kataloginlogoutkotlin

import kotlin.coroutines.experimental.CoroutineContext

class Presenter(
    private val logInLogOutKata: LogInLogOutKata,
    private val view: View,
    override val asyncContext: CoroutineContext,
    override val uiContext: CoroutineContext
) : AsyncExecutor {

    fun onLogInButtonTap(username: String, password: String) = async {
        val lotInResult = await { logInLogOutKata.logIn(username, password) }
        lotInResult.fold(
            {
                when (it) {
                    is InvalidCredentials -> {
                        view.showError(R.string.log_in_error_message)
                    }
                    is InvalidUsername -> view.showError(R.string.invalid_username_error_message)
                }
            },
            {
                view.hideLogInForm()
                view.showLogOutForm()
            }
        )
    }

    fun onLogOutButtonTap() = async {
        val logOutResult = await { logInLogOutKata.logOut() }
        if (logOutResult) {
            view.hideLogOutForm()
            view.showLogInForm()
        } else {
            view.showError(R.string.log_out_error_message)
        }
    }

    interface View {
        fun showLogInForm()
        fun hideLogInForm()
        fun showLogOutForm()
        fun hideLogOutForm()
        fun showError(resource: Int)
    }
}
