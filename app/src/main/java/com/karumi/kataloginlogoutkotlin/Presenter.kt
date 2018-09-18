package com.karumi.kataloginlogoutkotlin

import co.metalab.asyncawait.async

class Presenter(private val logInLogOutKata: LogInLogOutKata, private val view: View) {

  fun onLogInButtonTap(username: String, password: String) = async {
    await { logInLogOutKata.logIn(username, password) }.fold(
        {
          when (it) {
            is InvalidCredentials -> view.showError(R.string.log_in_error_message)
            is InvalidUsername -> view.showError(R.string.invalid_username_error_message)
          }
        }
        ,
        {
          view.hideLogInForm()
          view.showLogOutForm()
        }
    )
  }

  fun onLogOutButtonTap() = async {
    await { logInLogOutKata.logOut() }.fold(
        {
          view.showError(R.string.log_out_error_message)
        }
        ,
        {
          view.hideLogOutForm()
          view.showLogInForm()
        }
    )
  }

  interface View {
    fun showLogInForm()
    fun hideLogInForm()
    fun showLogOutForm()
    fun hideLogOutForm()
    fun showError(resource: Int)
  }
}